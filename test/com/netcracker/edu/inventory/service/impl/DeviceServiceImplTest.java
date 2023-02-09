package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.*;
import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.EntityFactory;
import org.junit.*;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class DeviceServiceImplTest {

    private static final int PIPED_BUFER_SIZE = 1024 * 4;
    private static final String FILE_NAME = "testOutDevice.bin";

    private static java.sql.Connection dbConnection;
    private static EntityFactory entityFactory;

    DeviceService deviceService;
    Battery b;
    Router r;
    Switch s;
    WifiRouter wr;
    Device tn;
    Device ab;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
        dbConnection = DBConnector.connectToRDB();
        entityFactory = InventoryFactoryManager.getEntityFactory();
    }

    @AfterClass
    public static void releaseEnvironment() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void before() throws Exception {
        deviceService = InventoryFactoryManager.getServiceFactory().createDeviceServiceImpl();

        //for sort and filtrate tests
        b = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        b.setType(Battery.class.getSimpleName());
        b.setManufacturer("APC");
        b.setModel("Smart-UPS 750 ВА 230 В U2");
        r = new com.netcracker.edu.inventory.model.device.entity.impl.Router();
        r.setType(Router.class.getSimpleName());
        r.setManufacturer("Cisco");
        r.setModel("NCS 5011 Router Data Sheet");
        s = new com.netcracker.edu.inventory.model.device.entity.impl.Switch();
        s.setType(Switch.class.getSimpleName());
        s.setManufacturer("D-Link");
        s.setModel("DES-1050G");
        wr = new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter();
        wr.setType(WifiRouter.class.getSimpleName());
        wr.setManufacturer("D-Link");
        wr.setModel("DWL-2600AP");
        tn = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        ab = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        ab.setType(new StringBuilder("Bat").append("tery").toString());
        ab.setManufacturer(new StringBuilder("A").append("PC").toString());
        ab.setModel(new StringBuilder("Smart-UPS 750").append(" ВА 230 В U2").toString());
    }

    @Test
    public void sortByIN() throws Exception {
        Battery b0 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        Battery b1 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        b1.setIn(1);
        Battery b2 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        b2.setIn(2);
        Battery b3 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        b3.setIn(3);
        Device[] devices = new Device[] {null, b2, b1, b0, null, b3, b2, b0, null};
        Device[] expResult = new Device[] {b1, b2, b2, b3, b0, b0, null, null, null};

        deviceService.sortByIN(devices);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void sortByProductionDate() throws Exception {
        Battery b0 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        Battery b1 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        b1.setProductionDate(new Date(1));
        Battery b2 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        b2.setProductionDate(new Date(2));
        Battery b3 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        b3.setProductionDate(new Date(3));
        Device[] devices = new Device[] {null, b2, b1, b0, null, b3, b2, b0, null};
        Device[] expResult = new Device[] {b1, b2, b2, b3, b0, b0, null, null, null};

        deviceService.sortByProductionDate(devices);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        deviceService.filtrateByType(devices, Router.class.getSimpleName());

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType_TypeNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        deviceService.filtrateByType(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType_TypeAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        deviceService.filtrateByType(devices, "Battery");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByManufacturer() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        deviceService.filtrateByManufacturer(devices, "Cisco");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByManufacturer_ManufacturerNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        deviceService.filtrateByManufacturer(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByManufacturer_ManufacturerAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        deviceService.filtrateByManufacturer(devices, "APC");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByModel() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        deviceService.filtrateByModel(devices, "NCS 5011 Router Data Sheet");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByModel_ModelNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        deviceService.filtrateByModel(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByModel_ModelAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        deviceService.filtrateByModel(devices, "Smart-UPS 750 ВА 230 В U2");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void isValidDeviceForInsertToRack() throws Exception {
        Battery battery = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery.setIn(5);

        boolean result = deviceService.isValidDeviceForInsertToRack(battery);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_DeviceNull() throws Exception {
        boolean result = deviceService.isValidDeviceForInsertToRack(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_IN0() throws Exception {
        Battery battery = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();

        boolean result = deviceService.isValidDeviceForInsertToRack(battery);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForOutputToStream() throws Exception {
        WifiRouter wifiRouter = new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceService.isValidDeviceForOutputToStream(wifiRouter);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForOutputToStream_DeviceNull() throws Exception {
        boolean result = deviceService.isValidDeviceForOutputToStream(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForOutputToStream_DeviceAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();
        wifiRouter.setModel("Super\nPuper");
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceService.isValidDeviceForOutputToStream(wifiRouter);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForOutputToStream_ChildAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();
        wifiRouter.setModel("Super&Puper");
        wifiRouter.setSecurityProtocol("no\nne");

        boolean result = deviceService.isValidDeviceForOutputToStream(wifiRouter);

        assertFalse(result);
    }

    @Test
    public void outputInputDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouterWithConnections();

        deviceService.outputDevice(battery, pipedOutputStream);
        deviceService.outputDevice(router, pipedOutputStream);
        deviceService.outputDevice(aSwitch, pipedOutputStream);
        deviceService.outputDevice(wifiRouter, pipedOutputStream);
        pipedOutputStream.close();

        for (int i = 0; i < aSwitch.getNumberOfPorts(); i++) {
            Connection connection = aSwitch.getPortConnection(i);
            if (connection != null) {
                Connection lazyConnection = entityFactory.createLazyInstance(connection.getPrimaryKey());
                aSwitch.setPortConnection(lazyConnection, i);
            }
        }
        wifiRouter.setWirelessConnection(entityFactory.createLazyInstance(wifiRouter.getWirelessConnection().getPrimaryKey()));
        wifiRouter.setWireConnection(entityFactory.createLazyInstance(wifiRouter.getWireConnection().getPrimaryKey()));

        Device result1 = deviceService.inputDevice(pipedInputStream);
        Device result2 = deviceService.inputDevice(pipedInputStream);
        Device result3 = deviceService.inputDevice(pipedInputStream);
        Device result4 = deviceService.inputDevice(pipedInputStream);
        pipedInputStream.close();

        assertEquals(com.netcracker.edu.inventory.model.device.entity.impl.Battery.class, result1.getClass());
        AssertUtilities.assertBattery(battery, (Battery) result1);
        assertEquals(com.netcracker.edu.inventory.model.device.entity.impl.Router.class, result2.getClass());
        AssertUtilities.assertRouter(router, (Router) result2);
        assertEquals(com.netcracker.edu.inventory.model.device.entity.impl.Switch.class, result3.getClass());
        AssertUtilities.assertSwitch(aSwitch, (Switch) result3);
        assertEquals(com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter.class, result4.getClass());
        AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) result4);
    }

    @Test(expected = DeviceValidationException.class)
    public void outputDeviceNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);

        deviceService.outputDevice(null, pipedOutputStream);
    }

    @Test(expected = DeviceValidationException.class)
    public void outputDevice_NotValidDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();
        wifiRouter.setModel("Super\nPuper");
        wifiRouter.setSecurityProtocol("none");

        deviceService.outputDevice(wifiRouter, pipedOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputDeviceStreamNull() throws Exception {
        deviceService.outputDevice(CreateUtilities.createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputDeviceStreamNull() throws Exception {
        deviceService.inputDevice(null);
    }

    @Test
    public void outputToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouterWithConnections();

        deviceService.outputDevice(battery, fileOutputStream);
        deviceService.outputDevice(router, fileOutputStream);
        deviceService.outputDevice(aSwitch, fileOutputStream);
        deviceService.outputDevice(wifiRouter, fileOutputStream);
        fileOutputStream.close();

        Files.delete(Paths.get(FILE_NAME));
    }

    @Test
    public void putGetDeviceFromDB() throws Exception {
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitchWithConnections();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouterWithConnections();

//        deviceService.removeDeviceFromDB(dbConnection, battery.getPrimaryKey());
//        deviceService.removeDeviceFromDB(dbConnection, router.getPrimaryKey());
//        deviceService.removeDeviceFromDB(dbConnection, aSwitch.getPrimaryKey());
//        deviceService.removeDeviceFromDB(dbConnection, wifiRouter.getPrimaryKey());

        Device result1 = deviceService.getDeviceFromDB(dbConnection, battery.getPrimaryKey());
        Device result2 = deviceService.getDeviceFromDB(dbConnection, router.getPrimaryKey());
        Device result3 = deviceService.getDeviceFromDB(dbConnection, aSwitch.getPrimaryKey());
        Device result4 = deviceService.getDeviceFromDB(dbConnection, wifiRouter.getPrimaryKey());

        assertNull(result1);
        assertNull(result2);
        assertNull(result3);
        assertNull(result4);

        assertTrue(deviceService.putDeviceToDB(dbConnection, battery));
        assertTrue(deviceService.putDeviceToDB(dbConnection, router));
        assertTrue(deviceService.putDeviceToDB(dbConnection, aSwitch));
        assertTrue(deviceService.putDeviceToDB(dbConnection, wifiRouter));

        for (int i = 0; i < aSwitch.getNumberOfPorts(); i++) {
            Connection connection = aSwitch.getPortConnection(i);
            if (connection != null) {
                Connection lazyConnection = entityFactory.createLazyInstance(connection.getPrimaryKey());
                aSwitch.setPortConnection(lazyConnection, i);
            }
        }
        wifiRouter.setWirelessConnection(entityFactory.createLazyInstance(wifiRouter.getWirelessConnection().getPrimaryKey()));
        wifiRouter.setWireConnection(entityFactory.createLazyInstance(wifiRouter.getWireConnection().getPrimaryKey()));

        result1 = deviceService.getDeviceFromDB(dbConnection, battery.getPrimaryKey());
        result2 = deviceService.getDeviceFromDB(dbConnection, router.getPrimaryKey());
        result3 = deviceService.getDeviceFromDB(dbConnection, aSwitch.getPrimaryKey());
        result4 = deviceService.getDeviceFromDB(dbConnection, wifiRouter.getPrimaryKey());

        assertTrue(result1 instanceof Battery);
        AssertUtilities.assertBattery(battery, (Battery) result1);
        assertTrue(result2 instanceof Router);
        AssertUtilities.assertRouter(router, (Router) result2);
        assertTrue(result3 instanceof Switch);
        AssertUtilities.assertSwitch(aSwitch, (Switch) result3);
        assertTrue(result4 instanceof WifiRouter);
        AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) result4);

        assertTrue(deviceService.removeDeviceFromDB(dbConnection, battery.getPrimaryKey()));
        assertTrue(deviceService.removeDeviceFromDB(dbConnection, router.getPrimaryKey()));
        assertTrue(deviceService.removeDeviceFromDB(dbConnection, aSwitch.getPrimaryKey()));
        assertTrue(deviceService.removeDeviceFromDB(dbConnection, wifiRouter.getPrimaryKey()));

        result1 = deviceService.getDeviceFromDB(dbConnection, battery.getPrimaryKey());
        result2 = deviceService.getDeviceFromDB(dbConnection, router.getPrimaryKey());
        result3 = deviceService.getDeviceFromDB(dbConnection, aSwitch.getPrimaryKey());
        result4 = deviceService.getDeviceFromDB(dbConnection, wifiRouter.getPrimaryKey());

        assertNull(result1);
        assertNull(result2);
        assertNull(result3);
        assertNull(result4);
    }

}