package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.connection.*;
import com.netcracker.edu.inventory.model.connection.entity.OpticFiber;
import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.connection.entity.Wireless;
import com.netcracker.edu.inventory.model.connection.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.connection.impl.DummyConnection;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.model.device.impl.DevicePK;
import com.netcracker.edu.inventory.model.device.impl.DummyDevice;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.rack.RackPrimaryKey;
import com.netcracker.edu.inventory.model.rack.impl.RackPK;
import com.netcracker.edu.inventory.service.DeviceService;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class AssertUtilities {

    private static final DeviceService deviceService = InventoryFactoryManager.getServiceFactory().createDeviceServiceImpl();

    public static void assertSomeNetworkElement(NetworkElement expNetworkElement, NetworkElement networkElement) throws Exception {
        if (expNetworkElement == null) {
            assertNull(networkElement);
        } else {
            if (expNetworkElement instanceof Device) {
                assertSomeDevice((Device) expNetworkElement, (Device) networkElement);
                return;
            }
            if (expNetworkElement instanceof Connection) {
                assertSomeConnection((Connection) expNetworkElement, (Connection) networkElement);
                return;
            }
        }
    }

    public static void assertNetworkElement(NetworkElement expNetworkElement, NetworkElement networkElement) throws Exception {
        if (expNetworkElement == null) {
            assertNull(networkElement);
        }
    }

    public static void assertSomeDevice(Device expDevice, Device device) throws Exception {
        if (expDevice == null) {
            assertNull(device);
            return;
        }
        if (expDevice.isLazy()) {
            assertDummyDevice((DummyDevice) expDevice, (DummyDevice) device);
            return;
        }
        if (expDevice instanceof Battery) {
            assertBattery((Battery) expDevice, (Battery) device);
            return;
        }
        if (expDevice instanceof Router) {
            if (expDevice instanceof Switch) {
                assertSwitch((Switch) expDevice, (Switch) device);
                return;
            }
            if (expDevice instanceof WifiRouter) {
                assertWifiRouter((WifiRouter) expDevice, (WifiRouter) device);
                return;
            }
            assertRouter((Router) expDevice, (Router) device);
            return;
        }
    }

    public static void assertDevice(Device expDevice, Device device) throws Exception {
        if (expDevice == null) {
            assertNull(device);
        } else {
            assertNetworkElement(expDevice, device);
            assertEquals(expDevice.getIn(), device.getIn());
            assertEquals(expDevice.getType(), device.getType());
            assertEquals(expDevice.getModel(), device.getModel());
            assertEquals(expDevice.getManufacturer(), device.getManufacturer());
            assertEquals(expDevice.getProductionDate(), device.getProductionDate());
        }
    }

    public static void assertBattery(Battery expBattery, Battery battery) throws Exception {
        assertDevice(expBattery, battery);
        assertEquals(expBattery.getChargeVolume(), battery.getChargeVolume());
    }

    public static void assertRouter(Router expRouter, Router router) throws Exception {
        assertDevice(expRouter, router);
        assertEquals(expRouter.getDataRate(), router.getDataRate());
    }

    public static void assertSwitch(Switch expSwitch, Switch aSwitch) throws Exception {
        assertRouter(expSwitch, aSwitch);
        assertEquals(expSwitch.getNumberOfPorts(), aSwitch.getNumberOfPorts());
        assertEquals(expSwitch.getPortsType(), aSwitch.getPortsType());
        assertConnectionList(expSwitch.getAllPortConnections(), aSwitch.getAllPortConnections());
    }

    public static void assertWifiRouter(WifiRouter expWifiRouter, WifiRouter wifiRouter) throws Exception {
        assertRouter(expWifiRouter, wifiRouter);
        assertEquals(expWifiRouter.getTechnologyVersion(), wifiRouter.getTechnologyVersion());
        assertEquals(expWifiRouter.getSecurityProtocol(), wifiRouter.getSecurityProtocol());
        assertSomeConnection(expWifiRouter.getWirelessConnection(), wifiRouter.getWirelessConnection());
        assertEquals(expWifiRouter.getWirePortType(), wifiRouter.getWirePortType());
        assertSomeConnection(expWifiRouter.getWireConnection(), wifiRouter.getWireConnection());
    }

    public static void assertRack(Rack expRack, Rack rack) throws Exception {
        if (expRack == null) {
            assertNull(rack);
        } else {
            assertEquals(expRack.getLocation(), rack.getLocation());
            assertEquals(expRack.getSize(), rack.getSize());
            assertEquals(expRack.getTypeOfDevices(), rack.getTypeOfDevices());
            for (int i = 0; i < expRack.getSize(); i++) {
                Device expDevice = expRack.getDevAtSlot(i);
                Device device = rack.getDevAtSlot(i);
                if (expDevice == null) {
                    assertNull(device);
                } else {
                    assertSomeDevice(expDevice, device);
                }
            }
        }
    }

    public static void assertSomeConnection(Connection expConnection, Connection connection) throws Exception {
        if (expConnection == null) {
            assertNull(connection);
            return;
        }
        if (expConnection.isLazy()) {
            assertDummyConnection((DummyConnection) expConnection, (DummyConnection) connection);
            return;
        }
        if (expConnection instanceof TwistedPair) {
            assertTwistedPair((TwistedPair) expConnection, (TwistedPair) connection);
            return;
        }
        if (expConnection instanceof OpticFiber) {
            assertOpticFiber((OpticFiber) expConnection, (OpticFiber) connection);
            return;
        }
        if (expConnection instanceof Wireless) {
            assertWireless((Wireless) expConnection, (Wireless) connection);
            return;
        }
        if (expConnection instanceof ThinCoaxial) {
            assertThinCoaxial((ThinCoaxial) expConnection, (ThinCoaxial) connection);
            return;
        }
    }

    public static void assertConnection(Connection expConnection, Connection connection) throws Exception {
        if (expConnection == null) {
            assertNull(connection);
        } else {
            assertNetworkElement(expConnection, connection);
            assertEquals(expConnection.getTrunk(), connection.getTrunk());
            assertEquals(expConnection.getSerialNumber(), connection.getSerialNumber());
            assertEquals(expConnection.getStatus(), connection.getStatus());
        }
    }

    public static void assertOneToOneConnection(OneToOneConnection expOneToOneConnection, OneToOneConnection oneToOneConnection) throws Exception {
        assertConnection(expOneToOneConnection, oneToOneConnection);
        assertEquals(expOneToOneConnection.getAPointConnectorType(), oneToOneConnection.getAPointConnectorType());
        assertEquals(expOneToOneConnection.getBPointConnectorType(), oneToOneConnection.getBPointConnectorType());
        assertSomeDevice(expOneToOneConnection.getAPoint(), oneToOneConnection.getAPoint());
        assertSomeDevice(expOneToOneConnection.getBPoint(), oneToOneConnection.getBPoint());
    }

    public static void assertOneToManyConnection(OneToManyConnection expOneToManyConnection, OneToManyConnection oneToManyConnection) throws Exception {
        assertConnection(expOneToManyConnection, oneToManyConnection);
        assertEquals(expOneToManyConnection.getAPointConnectorType(), oneToManyConnection.getAPointConnectorType());
        assertEquals(expOneToManyConnection.getBPointConnectorType(), oneToManyConnection.getBPointConnectorType());
        assertSomeDevice(expOneToManyConnection.getAPoint(), oneToManyConnection.getAPoint());
        assertEquals(expOneToManyConnection.getBCapacity(), oneToManyConnection.getBCapacity());
        assertDeviceList(expOneToManyConnection.getBPoints(), oneToManyConnection.getBPoints());
    }

    public static void assertAllToAllConnection(AllToAllConnection expAllToAllConnection, AllToAllConnection allToAllConnection) throws Exception {
        assertConnection(expAllToAllConnection, allToAllConnection);
        assertEquals(expAllToAllConnection.getConnectorType(), allToAllConnection.getConnectorType());
        assertEquals(expAllToAllConnection.getCurSize(), allToAllConnection.getCurSize());
        assertEquals(expAllToAllConnection.getMaxSize(), allToAllConnection.getMaxSize());
        assertDeviceSet(expAllToAllConnection.getAllDevices(), (allToAllConnection.getAllDevices()));
    }

    public static void assertTwistedPair(TwistedPair expTwistedPair, TwistedPair twistedPair) throws Exception {
        assertOneToOneConnection(expTwistedPair, twistedPair);
        assertEquals(expTwistedPair.getType(), twistedPair.getType());
        assertEquals(expTwistedPair.getLength(), twistedPair.getLength());
    }

    public static void assertOpticFiber(OpticFiber expOpticFiber, OpticFiber opticFiber) throws Exception {
        assertOneToOneConnection(expOpticFiber, opticFiber);
        assertEquals(expOpticFiber.getMode(), opticFiber.getMode());
        assertEquals(expOpticFiber.getLength(), opticFiber.getLength());
    }

    public static void assertWireless(Wireless expWireless, Wireless wireless) throws Exception {
        assertOneToManyConnection(expWireless, wireless);
        assertEquals(expWireless.getTechnology(), wireless.getTechnology());
        assertEquals(expWireless.getProtocol(), wireless.getProtocol());
        assertEquals(expWireless.getVersion(), wireless.getVersion());
    }

    public static void assertThinCoaxial(ThinCoaxial expThinCoaxial, ThinCoaxial thinCoaxial) throws Exception {
        assertAllToAllConnection(expThinCoaxial, thinCoaxial);
    }

    public static void assertDeviceArray(Device[] expArray, Device[] array) throws Exception {
        assertEquals(expArray.length, array.length);
        for (int i = 0; i < expArray.length; i++) {
            if (expArray[i] == null) {
                assertNull(array[i]);
            } else if (expArray[i].isLazy()) {
                assertDummyDevice((DummyDevice) expArray[i], (DummyDevice) array[i]);
            } else {
                assertSomeDevice(expArray[i], array[i]);
            }
        }
    }

    public static void assertDeviceSet(Set<Device> expSet, Set<Device> set) throws Exception {
        Device[] expDevices = new Device[0];
        expDevices = expSet.toArray(expDevices);
        Device[] devices = new Device[0];
        devices = set.toArray(devices);
        Arrays.sort(expDevices, Comparator.naturalOrder());
        Arrays.sort(devices, Comparator.naturalOrder());
        assertDeviceArray(expDevices, devices);
    }

    public static void assertDeviceList(List<Device> expList, List<Device> list) throws Exception {
        Device[] expDevices = new Device[0];
        expDevices = expList.toArray(expDevices);
        Device[] devices = new Device[0];
        devices = list.toArray(devices);
        assertDeviceArray(expDevices, devices);
    }

    public static void assertConnectionArray(Connection[] expArray, Connection[] array) throws Exception {
        assertEquals(expArray.length, array.length);
        for (int i = 0; i < expArray.length; i++) {
            if (expArray[i] == null) {
                assertNull(array[i]);
            } else if (expArray[i].isLazy()) {
                assertDummyConnection((DummyConnection) expArray[i], (DummyConnection) array[i]);
            } else {
                assertSomeConnection(expArray[i], array[i]);
            }
        }
    }

    public static void assertConnectionList(List<Connection> expList, List<Connection> list) throws Exception {
        Connection[] expConnections = new Connection[0];
        expConnections = expList.toArray(expConnections);
        Connection[] connections = new Connection[0];
        connections = list.toArray(connections);
        assertConnectionArray(expConnections, connections);
    }

    public static void assertSomePK(Unique.PrimaryKey expPK, Unique.PrimaryKey pk) throws Exception {
        assertEquals(expPK.getClass(), pk.getClass());
        if (expPK.getClass() == DevicePK.class) {
            assertDevicePK((DevicePrimaryKey) expPK, (DevicePrimaryKey) pk);
            return;
        }
        if (expPK.getClass() == ConnectionPK.class) {
            assertConnectionPK((ConnectionPrimaryKey) expPK, (ConnectionPrimaryKey) pk);
            return;
        }
        if (expPK.getClass() == RackPK.class) {
            assertRackPK((RackPrimaryKey) expPK, (RackPrimaryKey) pk);
            return;
        }
    }

    public static void assertDevicePK(DevicePrimaryKey expDevicePK, DevicePrimaryKey devicePK) throws Exception {
        assertEquals(expDevicePK.getIn(), devicePK.getIn());
        assertTrue(expDevicePK.equals(devicePK));
    }

    public static void assertConnectionPK(ConnectionPrimaryKey expConnectionPK, ConnectionPrimaryKey connectionPK) throws Exception {
        assertEquals(expConnectionPK.getSerialNumber(), connectionPK.getSerialNumber());
        assertTrue(expConnectionPK.getTrunk().equals(connectionPK.getTrunk()));
        assertTrue(expConnectionPK.equals(connectionPK));
    }

    public static void assertRackPK(RackPrimaryKey expRackPK, RackPrimaryKey rackPK) throws Exception {
        assertTrue(expRackPK.getLocation().equals(rackPK.getLocation()));
        assertTrue(expRackPK.equals(rackPK));
    }

    public static void assertDummyDevice(DummyDevice expDummyDevice, DummyDevice dummyDevice) throws Exception {
        assertTrue(expDummyDevice.equals(dummyDevice));
    }

    public static void assertDummyConnection(DummyConnection expDummyConnection, DummyConnection dummyConnection) throws Exception {
        assertTrue(expDummyConnection.equals(dummyConnection));
    }

    public static void assertPropertyChangeEvent(PropertyChangeEvent expPropertyChangeEvent, PropertyChangeEvent propertyChangeEvent) throws Exception {
        assertEquals(expPropertyChangeEvent.getPropertyName(), propertyChangeEvent.getPropertyName());
        if (expPropertyChangeEvent.getOldValue() instanceof NetworkElement) {
            AssertUtilities.assertSomeNetworkElement((NetworkElement) expPropertyChangeEvent.getOldValue(), (NetworkElement) propertyChangeEvent.getOldValue());
        } else {
            assertEquals(expPropertyChangeEvent.getOldValue(), propertyChangeEvent.getOldValue());
        }
        if (expPropertyChangeEvent.getNewValue() instanceof NetworkElement) {
            AssertUtilities.assertSomeNetworkElement((NetworkElement) expPropertyChangeEvent.getNewValue(), (NetworkElement) propertyChangeEvent.getNewValue());
        } else {
            assertEquals(expPropertyChangeEvent.getNewValue(), propertyChangeEvent.getNewValue());
        }
    }

}