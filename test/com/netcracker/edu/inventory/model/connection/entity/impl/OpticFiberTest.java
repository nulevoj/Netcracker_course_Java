package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.entity.OpticFiber;
import com.netcracker.edu.inventory.model.connection.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.location.impl.TrunkImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 13.11.16.
 */
public class OpticFiberTest {

    ConnectionMethodsTestSupplier connectionMethodsTestSupplier;

    OpticFiber<Switch, WifiRouter> defaultOpticFiber;
    OpticFiber<Switch, WifiRouter> opticFiber;
    OpticFiber<Switch, WifiRouter> opticFiberWithDevices;
    int defaultLength = 0;
    int length = 50;
    OpticFiber.Mode defaultMode = OpticFiber.Mode.need_init;
    OpticFiber.Mode mode = OpticFiber.Mode.single;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        connectionMethodsTestSupplier = new ConnectionMethodsTestSupplier();
        defaultOpticFiber = new com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber();
        opticFiber = CreateUtilities.createOpticFiber();
        opticFiberWithDevices = CreateUtilities.createOpticFiberWithDevices();
    }

    @Test
    public void setGetTrunk() throws Exception {
        connectionMethodsTestSupplier.setGetTrunk(defaultOpticFiber);
    }

    @Test
    public void setGetSerialNumber() throws Exception {
        connectionMethodsTestSupplier.setGetSerialNumber(defaultOpticFiber);
    }

    @Test
    public void setSerialNumber_PrevNot0_newNot0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_PrevNot0_newNot0(defaultOpticFiber);
    }

    @Test
    public void setSerialNumber_PrevNot0_new0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_PrevNot0_new0(defaultOpticFiber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSerialNumber_newLess0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_newLess0(defaultOpticFiber);
    }

    @Test
    public void defaultStatus() throws Exception {
        connectionMethodsTestSupplier.defaultStatus(defaultOpticFiber);
    }

    @Test
    public void setGetStatus() throws Exception {
        connectionMethodsTestSupplier.setGetStatus(defaultOpticFiber);
    }

    @Test
    public void getMode() throws Exception {
        assertEquals(defaultMode, defaultOpticFiber.getMode());
        assertEquals(mode, opticFiber.getMode());
    }

    @Test
    public void defaultLength() throws Exception {
        int result = defaultOpticFiber.getLength();

        assertEquals(defaultLength, result);
    }

    @Test
    public void setGetLength() throws Exception {
        defaultOpticFiber.setLength(length);
        int result = defaultOpticFiber.getLength();

        assertEquals(length, result);
    }

    @Test
    public void getAPointConnectorType() throws Exception {
        assertEquals(ConnectorType.FiberConnector_FC, defaultOpticFiber.getAPointConnectorType());
        assertEquals(ConnectorType.FiberConnector_FC, opticFiber.getAPointConnectorType());
    }

    @Test
    public void getBPointConnectorType() throws Exception {
        assertEquals(ConnectorType.FiberConnector_FC, defaultOpticFiber.getBPointConnectorType());
        assertEquals(ConnectorType.FiberConnector_FC, opticFiber.getBPointConnectorType());
    }

    @Test
    public void setGetAPoint() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();

        defaultOpticFiber.setAPoint(aSwitch);
        Switch result = defaultOpticFiber.getAPoint();

        AssertUtilities.assertSwitch(aSwitch, result);
    }

    @Test
    public void setGetBPoint() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        defaultOpticFiber.setBPoint(wifiRouter);
        WifiRouter result = defaultOpticFiber.getBPoint();

        AssertUtilities.assertWifiRouter(wifiRouter, result);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        OpticFiber result1 = new com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber();
        result1.fillAllFields(opticFiberWithDevices.getAllFields());

        AssertUtilities.assertOpticFiber(opticFiberWithDevices, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyConnection() throws Exception {
        OpticFiber result1 = new com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber();
        result1.fillAllFields(defaultOpticFiber.getAllFields());

        AssertUtilities.assertOpticFiber(defaultOpticFiber, result1);
    }

    @Test
    public void isLazy() throws Exception {
        assertFalse(defaultOpticFiber.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        ConnectionPrimaryKey expConnectionPK = new ConnectionPK(opticFiber.getTrunk(), opticFiber.getSerialNumber());

        Unique.PrimaryKey primaryKey = opticFiber.getPrimaryKey();

        AssertUtilities.assertSomePK(expConnectionPK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultOpticFiber.getPrimaryKey();

        assertNull(primaryKey);
    }

    @Test
    public void compareTo() throws Exception {
        Connection connection1 = new com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber();
        connection1.setTrunk(new TrunkImpl("", ""));
        connection1.setSerialNumber(1);
        Connection connection2 = new com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber();
        connection2.setTrunk(new TrunkImpl("", ""));
        connection2.setSerialNumber(2);
        Connection connection3 = new com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber();
        connection3.setTrunk(new TrunkImpl("", ""));
        connection3.setSerialNumber(3);
        Connection connection4 = new com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber();
        connection4.setTrunk(new TrunkImpl("", ""));
        connection4.setSerialNumber(2);

        int result1 = connection2.compareTo(connection1);
        int result2 = connection2.compareTo(connection3);
        int result3 = connection2.compareTo(connection4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);
    }

}