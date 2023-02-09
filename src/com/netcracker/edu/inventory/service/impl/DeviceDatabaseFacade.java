package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.InventoryFactoryManager;
import com.netcracker.edu.inventory.model.FillableEntity;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.model.device.impl.DummyDevice;
import com.netcracker.edu.inventory.service.EntityFactory;
import com.netcracker.edu.location.Service;
import com.netcracker.edu.location.impl.ServiceImpl;

import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceDatabaseFacade {

    protected static final EntityFactory entityFactory = InventoryFactoryManager.getEntityFactory();
    protected static final Service locationService = new ServiceImpl();

    private static final Logger log = Logger.getLogger(DeviceDatabaseFacade.class.getName());

    public static Device getDeviceFromDB(Connection dbConnection, DevicePrimaryKey dpk) throws SQLException {
        checkConnectionIsNull(dbConnection);
        if (checkDPKIsNull(dpk)) {
            return null;
        }

        Device result = null;

        Statement statement = dbConnection.createStatement();
        ResultSet rsDevice = statement.executeQuery("SELECT * FROM Device WHERE InventoryNumber = " + dpk.getIn());
        if (!rsDevice.next()) {
            log.warning("device not found");
            return null;
        }
        int deviceId = rsDevice.getInt("id");

        Queue<FillableEntity.Field> fields = new ArrayDeque<>();
        fields.add(new FillableEntity.Field(Integer.class, rsDevice.getInt("InventoryNumber")));
        fields.add(new FillableEntity.Field(String.class, rsDevice.getString("Type")));
        fields.add(new FillableEntity.Field(String.class, rsDevice.getString("Manufacturer")));
        fields.add(new FillableEntity.Field(String.class, rsDevice.getString("Model")));
        fields.add(new FillableEntity.Field(Date.class, getDateFromString(rsDevice.getString("ProductionDate"))));

        String clazz = rsDevice.getString("Class");
        if (clazz.equals("Battery")) {
            result = entityFactory.createEmptyNetworkElementImpl(Battery.class);
            ResultSet rsBattery = statement.executeQuery("SELECT * FROM Battery WHERE Device_id = " + deviceId);
            if (rsBattery.next()) {
                fields.add(new FillableEntity.Field(Integer.class, rsBattery.getInt("ChargeVolume")));
            }
        }
        if (clazz.equals("Router") || clazz.equals("Switch") || clazz.equals("WifiRouter")) {
            ResultSet rsRouter = statement.executeQuery("SELECT * FROM Router WHERE Device_id = " + deviceId);
            if (!rsRouter.next()) {
                log.warning("router not found");
                return null;
            }
            fields.add(new FillableEntity.Field(String.class, rsRouter.getInt("DataRate")));

            if (clazz.equals("Router")) {
                result = entityFactory.createEmptyNetworkElementImpl(Router.class);
            }
            if (clazz.equals("Switch")) {
                result = entityFactory.createEmptyNetworkElementImpl(Switch.class);
                ResultSet rsSwitch = statement.executeQuery("SELECT * FROM Switch WHERE Device_id = " + deviceId);
                if (rsSwitch.next()) {
                    fields.add(new FillableEntity.Field(String.class, rsSwitch.getInt("NumberOfPorts")));
                    fields.add(new FillableEntity.Field(String.class, rsSwitch.getString("PortsType")));
                    com.netcracker.edu.inventory.model.connection.Connection[] portsConnections
                            = new com.netcracker.edu.inventory.model.connection.Connection[rsRouter.getInt("NumberOfPorts")];
                    ResultSet rsPortsConnections = statement.executeQuery("SELECT * FROM ConnectionPK, MultDevToConReferences" +
                            " WHERE PK_id = Connection_PK_id" +
                            " AND Device_id = " + deviceId +
                            " ORDER BY 'Index' ASC");
                    while (rsPortsConnections.next()) {
                        portsConnections[rsPortsConnections.getInt("Index")] =
                                entityFactory.createLazyInstance(
                                        entityFactory.createConnectionPrimaryKey(
                                                locationService.getTrunkFromDB(dbConnection, rsPortsConnections.getString("Trunk_seq")),
                                                rsPortsConnections.getInt("SerialNumber")));
                    }
                    fields.add(new FillableEntity.Field(com.netcracker.edu.inventory.model.connection.Connection[].class, portsConnections));
                }
            }
            if (clazz.equals("WifiRouter")) {
                result = entityFactory.createEmptyNetworkElementImpl(WifiRouter.class);
                ResultSet rsWifiRouter = statement.executeQuery("SELECT * FROM WifiRouter WHERE Device_id = " + deviceId);
                if (rsWifiRouter.next()) {
                    fields.add(new FillableEntity.Field(String.class, rsWifiRouter.getString("SecurityProtocol")));
                    fields.add(new FillableEntity.Field(String.class, rsWifiRouter.getString("TechnologyVersion")));
                    fields.add(new FillableEntity.Field(com.netcracker.edu.inventory.model.connection.Connection.class,
                            getDummyConnection(dbConnection, rsWifiRouter.getInt("WirelessConnection"))));
                    fields.add(new FillableEntity.Field(String.class, rsWifiRouter.getString("WirePortType")));
                    fields.add(new FillableEntity.Field(com.netcracker.edu.inventory.model.connection.Connection.class,
                            getDummyConnection(dbConnection, rsWifiRouter.getInt("WireConnection"))));
                }
            }
        }
        if (result == null) {
            return null;
        }
        result.fillAllFields(fields);
        return result;
    }

    public static boolean putDeviceToDB(Connection dbConnection, Device device) throws SQLException {
        checkConnectionIsNull(dbConnection);
        if (device == null) {
            log.warning("device can not be null");
            return false;
        }
        if (device instanceof DummyDevice) {
            log.warning("device can not be Dummy");
            return false;
        }

        dbConnection.setAutoCommit(false);
        Savepoint start = dbConnection.setSavepoint("start");
        Statement statement = dbConnection.createStatement();
        boolean result;

        ResultSet rsDevice = statement.executeQuery("SELECT * FROM Device WHERE InventoryNumber = " + device.getIn());
        if (rsDevice.next()) {
            if (!device.getClass().getSimpleName().equals(rsDevice.getString("Class"))) {
                log.warning(String.format("Fail: new device (%s) is not the same type as old device (%s)",
                        device.getClass().getSimpleName(), rsDevice.getString("Class")));
                return false;
            }
            result = updateDevice(dbConnection, device, rsDevice.getInt("id"));
        } else {
            result = insertDevice(dbConnection, device);
        }

        if (result) {
            dbConnection.commit();
        }
        if (!result) {
            log.warning("Failed to put device to DB");
            dbConnection.rollback(start);
        }
        dbConnection.setAutoCommit(true);
        return result;
    }

    protected static boolean insertDevice(Connection dbConnection, Device device) throws SQLException {
        boolean result;
        result = executeUpdate(dbConnection,
                String.format("INSERT INTO Device ('Class', 'InventoryNumber', 'Type', 'Model', 'Manufacturer', 'ProductionDate')" +
                                " VALUES (%s, %d, %s, %s, %s, %d)",
                        getStringForInsertion(device.getClass().getSimpleName()),
                        device.getIn(),
                        getStringForInsertion(device.getType()),
                        getStringForInsertion(device.getModel()),
                        getStringForInsertion(device.getManufacturer()),
                        getDateForInsertion(device.getProductionDate())));
        dbConnection.commit();

        Statement statement = dbConnection.createStatement();
        ResultSet rsDevice = statement.executeQuery("SELECT * FROM Device WHERE InventoryNumber = " + device.getIn());
        if (!rsDevice.next()) {
            log.warning("could not insert device to DB");
            return false;
        }
        int deviceId = rsDevice.getInt("id");

        if (device.getClass().getSimpleName().equals("Battery")) {
            result = result && executeUpdate(dbConnection,
                    String.format("INSERT INTO Battery ('Device_id', 'ChargeVolume') VALUES (%d, %d)",
                            deviceId, ((Battery) device).getChargeVolume()));
        }
        if (device.getClass().getSimpleName().equals("Router")
                || device.getClass().getSimpleName().equals("Switch")
                || device.getClass().getSimpleName().equals("WifiRouter")) {

            result = result && executeUpdate(dbConnection,
                    String.format("INSERT INTO Router ('Device_id', 'DataRate') VALUES (%d, %d)",
                            deviceId, ((Router) device).getDataRate()));

            if (device.getClass().getSimpleName().equals("Switch")) {
                result = result && executeUpdate(dbConnection,
                        String.format("INSERT INTO Switch ('Device_id', 'NumberOfPorts', 'PortsType') VALUES (%d, %d, %s)",
                                deviceId,
                                ((Switch) device).getNumberOfPorts(),
                                getStringForInsertion(((Switch) device).getPortsType().toString())));
                ResultSet rsConnection;
                for (int i = 0; i < ((Switch) device).getNumberOfPorts(); i++) {
                    if (((Switch) device).getPortConnection(i) == null) {
                        continue;
                    }
                    result = result && putConnectionToDB(dbConnection, ((Switch) device).getPortConnection(i));
                    rsConnection = statement.executeQuery("SELECT * FROM ConnectionPK WHERE SerialNumber = "
                            + ((Switch) device).getPortConnection(i).getSerialNumber());
                    rsConnection.next();
                    result = result && executeUpdate(dbConnection,
                            String.format("INSERT INTO MultDevToConReferences ('Device_id', 'Index', 'Connection_PK_id', 'FieldSeq')" +
                                            " VALUES (%d, %d, %d, '%d_%d_%d')",
                                    deviceId, i, rsConnection.getInt("PK_id"),
                                    deviceId, i, rsConnection.getInt("PK_id")));
                }
            }
            if (device.getClass().getSimpleName().equals("WifiRouter")) {
                result = result && executeUpdate(dbConnection,
                        String.format("INSERT INTO WifiRouter ('Device_id', 'TechnologyVersion', 'SecurityProtocol'," +
                                        " 'WirelessConnection', 'WirePortType', 'WireConnection')" +
                                        " VALUES (%d, %s, %s, %d, %s, %d)",
                                deviceId,
                                getStringForInsertion(((WifiRouter) device).getTechnologyVersion()),
                                getStringForInsertion(((WifiRouter) device).getSecurityProtocol()),
                                ((WifiRouter) device).getWirelessConnection().getSerialNumber(),
                                getStringForInsertion(((WifiRouter) device).getWirePortType().toString()),
                                ((WifiRouter) device).getWireConnection().getSerialNumber()));
                result = result && putConnectionToDB(dbConnection, ((WifiRouter) device).getWirelessConnection());
                result = result && putConnectionToDB(dbConnection, ((WifiRouter) device).getWireConnection());
            }
        }
        return result;
    }

    protected static boolean updateDevice(Connection dbConnection, Device device, int deviceId) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet rsDevice = statement.executeQuery("SELECT * FROM Device WHERE id = " + deviceId);
        if (!rsDevice.next()) {
            log.warning("device not found");
            return false;
        }

        boolean result = executeUpdate(dbConnection, String.format(
                "UPDATE Device SET" +
                        " 'InventoryNumber' = %d, 'Type' = %s, 'Model' = %s, 'Manufacturer' = %s, 'ProductionDate' = %d" +
                        " WHERE id = %d",
                device.getIn(),
                getStringForInsertion(device.getType()),
                getStringForInsertion(device.getModel()),
                getStringForInsertion(device.getManufacturer()),
                getDateForInsertion(device.getProductionDate()),
                deviceId));

        String clazz = rsDevice.getString("Class");
        if (clazz.equals("Battery")) {
            result = result && executeUpdate(dbConnection, String.format(
                    "UPDATE Battery SET 'ChargeVolume' = %d WHERE Device_id = %d",
                    ((Battery) device).getChargeVolume(),
                    deviceId));
        }
        if (clazz.equals("Router") || clazz.equals("Switch") || clazz.equals("WifiRouter")) {
            result = result && executeUpdate(dbConnection, String.format(
                    "UPDATE Router SET 'DataRate' = %d WHERE Device_id = %d",
                    ((Router) device).getDataRate(), deviceId));
            if (clazz.equals("Switch")) {
                ResultSet rsConnection;
                for (int i = 0; i < ((Switch) device).getNumberOfPorts(); i++) {
                    if (((Switch) device).getPortConnection(i) == null) {
                        continue;
                    }
                    String s = String.format(
                            "SELECT * FROM MultDevToConReferences" +
                                    " WHERE 'Device_id' = %d" +
                                    " AND 'Index' = %d",
                            deviceId, i);
                    System.out.println(s);
                    ResultSet rsReference = statement.executeQuery(s);
                    if (rsReference.next()) {
                        rsConnection = statement.executeQuery(String.format(
                                "SELECT * FROM ConnectionPK WHERE PK_id = "
                                        + rsReference.getInt("Connection_PK_id")));
                        if (rsConnection.next()) {
                            if (rsConnection.getInt("SerialNumber")
                                    == ((Switch) device).getPortConnection(i).getSerialNumber()) {
                                result = result && updateConnection(dbConnection,
                                        ((Switch) device).getPortConnection(i),
                                        rsConnection.getInt("PK_id"));
                                continue;
                            } else {
                                executeUpdate(dbConnection, "DELETE FROM ConnectionPK WHERE PK_id = "
                                        + rsReference.getInt("Connection_PK_id"));
                            }
                        }
                        executeUpdate(dbConnection, String.format(
                                "DELETE FROM MultDevToConReferences" +
                                        " WHERE Device_id = %d" +
                                        " AND Index = %d",
                                deviceId, i));
                    }
                    result = result && putConnectionToDB(dbConnection, ((Switch) device).getPortConnection(i));
                    rsConnection = statement.executeQuery("SELECT * FROM ConnectionPK WHERE SerialNumber = "
                            + ((Switch) device).getPortConnection(i).getSerialNumber());
                    rsConnection.next();
                    result = result && executeUpdate(dbConnection,
                            String.format("INSERT INTO MultDevToConReferences ('Device_id', 'Index', 'Connection_PK_id', 'FieldSeq')" +
                                            " VALUES (%d, %d, %d, '%d_%d_%d')",
                                    deviceId, i, rsConnection.getInt("PK_id"),
                                    deviceId, i, rsConnection.getInt("PK_id")));
                }
            }
            if (clazz.equals("WifiRouter")) {
                ResultSet rsConnection;

                rsConnection = statement.executeQuery(
                        "SELECT * FROM ConnectionPK WHERE SerialNumber = "
                                + ((WifiRouter) device).getWireConnection().getSerialNumber());
                if (rsConnection.next()) {
                    result = result && updateConnection(dbConnection,
                            ((WifiRouter) device).getWireConnection(),
                            rsConnection.getInt("PK_id"));
                } else {
                    ResultSet oldWifiRouter = statement.executeQuery(
                            "SELECT * FROM WifiRouter WHERE Device_id = " + deviceId);
                    if (oldWifiRouter.next()) {
                        executeUpdate(dbConnection,
                                "DELETE FROM ConnectionPK WHERE SerialNumber = "
                                        + oldWifiRouter.getInt("WireConnection"));
                    }
                    result = result && putConnectionToDB(dbConnection, ((WifiRouter) device).getWireConnection());
                }

                rsConnection = statement.executeQuery(
                        "SELECT * FROM ConnectionPK WHERE SerialNumber = "
                                + ((WifiRouter) device).getWirelessConnection().getSerialNumber());
                if (rsConnection.next()) {
                    result = result && updateConnection(dbConnection,
                            ((WifiRouter) device).getWirelessConnection(),
                            rsConnection.getInt("PK_id"));
                } else {
                    ResultSet oldWifiRouter = statement.executeQuery(
                            "SELECT * FROM WifiRouter WHERE Device_id = " + deviceId);
                    if (oldWifiRouter.next()) {
                        executeUpdate(dbConnection,
                                "DELETE FROM ConnectionPK WHERE SerialNumber = "
                                        + oldWifiRouter.getInt("WirelessConnection"));
                    }
                    result = result && putConnectionToDB(dbConnection, ((WifiRouter) device).getWirelessConnection());
                }
            }
        }
        return result;
    }

    public static boolean removeDeviceFromDB(Connection dbConnection, DevicePrimaryKey dpk) throws SQLException {
        checkConnectionIsNull(dbConnection);
        if (checkDPKIsNull(dpk)) {
            return false;
        }

        Statement statement = dbConnection.createStatement();
        ResultSet rsDevice = statement.executeQuery("SELECT * FROM Device WHERE InventoryNumber = " + dpk.getIn());
        if (!rsDevice.next()) {
            log.warning(String.format("device not found (in = %d)", dpk.getIn()));
            return false;
        }
        int deviceId = rsDevice.getInt("id");
        dbConnection.setAutoCommit(false);

        String clazz = rsDevice.getString("Class");
        if (clazz.equals("Battery")) {
            executeUpdate(dbConnection, "DELETE FROM Battery WHERE Device_id = " + deviceId);
        }
        if (clazz.equals("Router") || clazz.equals("Switch") || clazz.equals("WifiRouter")) {
            executeUpdate(dbConnection, "DELETE FROM Router WHERE Device_id = " + deviceId);

            if (clazz.equals("Switch")) {
                executeUpdate(dbConnection, "DELETE FROM Switch WHERE Device_id = " + deviceId);

                ResultSet rsMultDevToConReferences = statement.executeQuery(
                        "SELECT * FROM MultDevToConReferences WHERE Device_id = " + deviceId);
                while (rsMultDevToConReferences.next()) {
                    int connection_PK_id = rsMultDevToConReferences.getInt("Connection_PK_id");
                    ResultSet rsConnection = statement.executeQuery(
                            "SELECT * FROM ConnectionPK WHERE PK_id = " + connection_PK_id);
                    if (rsConnection.next()) {
                        executeUpdate(dbConnection, "DELETE FROM ConnectionPK WHERE PK_id = " + connection_PK_id);
                    }
                }
                executeUpdate(dbConnection, "DELETE FROM MultDevToConReferences WHERE Device_id = " + deviceId);
            }
            if (clazz.equals("WifiRouter")) {
                ResultSet rsWifiRouter = statement.executeQuery("SELECT * FROM WifiRouter WHERE Device_id = " + deviceId);
                if (rsWifiRouter.next()) {
                    executeUpdate(dbConnection,
                            "DELETE FROM ConnectionPK WHERE SerialNumber = " +
                                    rsWifiRouter.getString("WirelessConnection"));
                    executeUpdate(dbConnection,
                            "DELETE FROM ConnectionPK WHERE SerialNumber = " +
                                    rsWifiRouter.getString("WireConnection"));
                }
            }
        }

        boolean result = executeUpdate(dbConnection, "DELETE FROM Device WHERE id = " + deviceId);
        if (result) {
            dbConnection.commit();
        }
        if (!result) {
            log.info(String.format("could not delete device (in = %d)", dpk.getIn()));
            dbConnection.rollback();
        }
        return result;
    }

    protected static com.netcracker.edu.inventory.model.connection.Connection
    getDummyConnection(Connection dbConnection, int serialNumber) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet rsConnection = statement.executeQuery("SELECT * FROM ConnectionPK" +
                " WHERE SerialNumber = " + serialNumber);
        if (!rsConnection.next()) {
            log.warning("Connection not found");
            return null;
        }
        return InventoryFactoryManager.getEntityFactory().createLazyInstance(
                InventoryFactoryManager.getEntityFactory().createConnectionPrimaryKey(
                        locationService.getTrunkFromDB(dbConnection, rsConnection.getString("Trunk_seq")),
                        serialNumber));

    }

    protected static boolean putConnectionToDB(
            Connection dbConnection, com.netcracker.edu.inventory.model.connection.Connection connection) throws SQLException {
        boolean result;
        result = executeUpdate(dbConnection, String.format(
                "INSERT INTO ConnectionPK ('SerialNumber', 'Trunk_seq') VALUES (%d, %s)",
                connection.getSerialNumber(),
                getStringForInsertion(connection.getTrunk().getAlias())));
        result = result && locationService.putTrunkToDB(dbConnection, connection.getTrunk());
        return result;
    }

    protected static boolean updateConnection(
            Connection dbConnection, com.netcracker.edu.inventory.model.connection.Connection connection,
            int PK_id) throws SQLException {
        boolean result;
        ResultSet rsConnection = dbConnection.createStatement().executeQuery(
                "SELECT * FROM ConnectionPK WHERE PK_id = " + PK_id);
        locationService.removeTrunkFromDB(dbConnection, rsConnection.getString("Trunk_seq"));
        result = executeUpdate(dbConnection, String.format(
                "UPDATE ConnectionPK SET 'SerialNumber' = %d, 'Trunk_seq' = %s WHERE PK_id = " + PK_id,
                connection.getSerialNumber(),
                getStringForInsertion(connection.getTrunk().getAlias())));
        result = result && locationService.putTrunkToDB(dbConnection, connection.getTrunk());
        return result;
    }

    protected static Long getDateForInsertion(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime();
    }

    protected static Date getDateFromString(String milliseconds) {
        if (milliseconds == null) {
            return null;
        }
        long date = Integer.parseInt(milliseconds);
        return new Date(date);
    }

    protected static String getStringForInsertion(String s) {
        if (s == null) {
            return null;
        }
        return String.format("'%s'", s);
    }

    protected static boolean executeUpdate(Connection dbConnection, String query) throws SQLException {
        try {
            Statement statement = dbConnection.createStatement();
            return statement.executeUpdate(query) == 1;
        } catch (SQLException e) {
            log.log(Level.SEVERE, query, e);
            throw e;
        }
    }

    protected static boolean checkDPKIsNull(DevicePrimaryKey dpk) {
        if (dpk == null) {
            log.warning("DevicePrimaryKey is null");
            return true;
        }
        return false;
    }

    protected static void checkConnectionIsNull(Connection connection) throws IllegalArgumentException {
        CommonUtility.checkObjectIsNull(connection, "DB connection can not be null");
    }

}
