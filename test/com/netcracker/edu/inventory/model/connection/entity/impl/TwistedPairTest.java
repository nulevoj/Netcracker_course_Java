package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
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
public class TwistedPairTest {

    ConnectionMethodsTestSupplier connectionMethodsTestSupplier;

    TwistedPair<Switch, WifiRouter> defaultTwistedPair;
    TwistedPair<Switch, WifiRouter> twistedPair;
    TwistedPair<Switch, WifiRouter> twistedPairWithDevices;
    int defaultLength = 0;
    int length = 50;
    TwistedPair.Type defaultType = TwistedPair.Type.need_init;
    TwistedPair.Type type = TwistedPair.Type.UTP;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        connectionMethodsTestSupplier = new ConnectionMethodsTestSupplier();
        defaultTwistedPair = new com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair();
        twistedPair = CreateUtilities.createTwistedPair();
        twistedPairWithDevices = CreateUtilities.createTwistedPairWithDevices();
    }

    @Test
    public void setGetTrunk() throws Exception {
        connectionMethodsTestSupplier.setGetTrunk(defaultTwistedPair);
    }

    @Test
    public void setGetSerialNumber() throws Exception {
        connectionMethodsTestSupplier.setGetSerialNumber(defaultTwistedPair);
    }

    @Test
    public void setSerialNumber_PrevNot0_newNot0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_PrevNot0_newNot0(defaultTwistedPair);
    }

    @Test
    public void setSerialNumber_PrevNot0_new0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_PrevNot0_new0(defaultTwistedPair);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSerialNumber_newLess0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_newLess0(defaultTwistedPair);
    }

    @Test
    public void defaultStatus() throws Exception {
        connectionMethodsTestSupplier.defaultStatus(defaultTwistedPair);
    }

    @Test
    public void setGetStatus() throws Exception {
        connectionMethodsTestSupplier.setGetStatus(defaultTwistedPair);
    }

    @Test
    public void getType() throws Exception {
        assertEquals(defaultType, defaultTwistedPair.getType());
        assertEquals(type, twistedPair.getType());
    }

    @Test
    public void defaultLength() throws Exception {
        int result = defaultTwistedPair.getLength();

        assertEquals(defaultLength, result);
    }

    @Test
    public void setGetLength() throws Exception {
        defaultTwistedPair.setLength(length);
        int result = defaultTwistedPair.getLength();

        assertEquals(length, result);
    }

    @Test
    public void getAPointConnectorType() throws Exception {
        assertEquals(ConnectorType.RJ45, defaultTwistedPair.getAPointConnectorType());
        assertEquals(ConnectorType.RJ45, twistedPair.getAPointConnectorType());
    }

    @Test
    public void getBPointConnectorType() throws Exception {
        assertEquals(ConnectorType.RJ45, defaultTwistedPair.getBPointConnectorType());
        assertEquals(ConnectorType.RJ45, twistedPair.getBPointConnectorType());
    }

    @Test
    public void setGetAPoint() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();

        defaultTwistedPair.setAPoint(aSwitch);
        Switch result = defaultTwistedPair.getAPoint();

        AssertUtilities.assertSwitch(aSwitch, result);
    }

    @Test
    public void setGetBPoint() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        defaultTwistedPair.setBPoint(wifiRouter);
        WifiRouter result = defaultTwistedPair.getBPoint();

        AssertUtilities.assertWifiRouter(wifiRouter, result);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        TwistedPair result1 = new com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair();
        result1.fillAllFields(twistedPairWithDevices.getAllFields());

        AssertUtilities.assertTwistedPair(twistedPairWithDevices, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyConnection() throws Exception {
        TwistedPair result1 = new com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair();
        result1.fillAllFields(defaultTwistedPair.getAllFields());

        AssertUtilities.assertTwistedPair(defaultTwistedPair, result1);
    }

    @Test
    public void isLazy() throws Exception {
        assertFalse(twistedPair.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        ConnectionPrimaryKey expConnectionPK = new ConnectionPK(twistedPair.getTrunk(), twistedPair.getSerialNumber());

        Unique.PrimaryKey primaryKey = twistedPair.getPrimaryKey();

        AssertUtilities.assertSomePK(expConnectionPK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultTwistedPair.getPrimaryKey();

        assertNull(primaryKey);
    }

    @Test
    public void compareTo() throws Exception {
        Connection connection1 = new com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair();
        connection1.setTrunk(new TrunkImpl("", ""));
        connection1.setSerialNumber(1);
        Connection connection2 = new com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair();
        connection2.setTrunk(new TrunkImpl("", ""));
        connection2.setSerialNumber(2);
        Connection connection3 = new com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair();
        connection3.setTrunk(new TrunkImpl("", ""));
        connection3.setSerialNumber(3);
        Connection connection4 = new com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair();
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