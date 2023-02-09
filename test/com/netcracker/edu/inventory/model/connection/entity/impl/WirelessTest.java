package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.entity.Wireless;
import com.netcracker.edu.inventory.model.connection.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.location.impl.TrunkImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 13.11.16.
 */
public class WirelessTest {

    ConnectionMethodsTestSupplier connectionMethodsTestSupplier;

    Wireless<WifiRouter, WifiRouter> defaultWireless;
    Wireless<WifiRouter, WifiRouter> wireless;
    Wireless<WifiRouter, WifiRouter> wirelessWithDevices;
    String technology = "802.11g";
    String securityProtocol = "WPA";
    int defaultVersion = 0;
    int version = 2;
    int defaultBCapacity = 0;
    int bCapacity = 3;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        connectionMethodsTestSupplier = new ConnectionMethodsTestSupplier();
        defaultWireless = new com.netcracker.edu.inventory.model.connection.entity.impl.Wireless();
        wireless = CreateUtilities.createWireless();
        wirelessWithDevices = CreateUtilities.createWirelessWithDevices();
    }

    @Test
    public void setGetTrunk() throws Exception {
        connectionMethodsTestSupplier.setGetTrunk(defaultWireless);
    }

    @Test
    public void setGetSerialNumber() throws Exception {
        connectionMethodsTestSupplier.setGetSerialNumber(defaultWireless);
    }

    @Test
    public void setSerialNumber_PrevNot0_newNot0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_PrevNot0_newNot0(defaultWireless);
    }

    @Test
    public void setSerialNumber_PrevNot0_new0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_PrevNot0_new0(defaultWireless);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSerialNumber_newLess0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_newLess0(defaultWireless);
    }

    @Test
    public void defaultStatus() throws Exception {
        connectionMethodsTestSupplier.defaultStatus(defaultWireless);
    }

    @Test
    public void setGetStatus() throws Exception {
        connectionMethodsTestSupplier.setGetStatus(defaultWireless);
    }

    @Test
    public void getTechnology() throws Exception {
        assertNull(defaultWireless.getTechnology());
        assertEquals(technology, wireless.getTechnology());
    }

    @Test
    public void defaultProtocol() throws Exception {
        assertNull(defaultWireless.getProtocol());
    }

    @Test
    public void setGetProtocol() throws Exception {
        defaultWireless.setProtocol(securityProtocol);
        String result = defaultWireless.getProtocol();

        assertEquals(securityProtocol, result);
    }

    @Test
    public void defaultVersion() throws Exception {
        assertEquals(defaultVersion, defaultWireless.getVersion());
    }

    @Test
    public void setGetVersion() throws Exception {
        defaultWireless.setVersion(version);
        int result = defaultWireless.getVersion();

        assertEquals(version, result);
    }

    @Test
    public void getAPointConnectorType() throws Exception {
        assertEquals(ConnectorType.Wireless, defaultWireless.getAPointConnectorType());
        assertEquals(ConnectorType.Wireless, wireless.getAPointConnectorType());
    }

    @Test
    public void getBPointConnectorType() throws Exception {
        assertEquals(ConnectorType.Wireless, defaultWireless.getBPointConnectorType());
        assertEquals(ConnectorType.Wireless, wireless.getBPointConnectorType());
    }

    @Test
    public void setGetAPoint() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        defaultWireless.setAPoint(wifiRouter);
        WifiRouter result = defaultWireless.getAPoint();

        AssertUtilities.assertWifiRouter(wifiRouter, result);
    }

    @Test
    public void defaultBPoints() throws Exception {
        List<WifiRouter> result = defaultWireless.getBPoints();

        assertEquals(new ArrayList<WifiRouter>(), result);
    }

    @Test
    public void setGetBPoints() throws Exception {
        List<WifiRouter> devices = wireless.getBPoints();

        defaultWireless.setBPoints(devices);
        List<WifiRouter> result = defaultWireless.getBPoints();

        assertEquals(devices, result);
    }

    @Test
    public void getBCapacity() throws Exception {
        assertEquals(defaultBCapacity, defaultWireless.getBCapacity());
        assertEquals(bCapacity, wireless.getBCapacity());
    }

    @Test
    public void setGetBPoint() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();
        wifiRouter.setSecurityProtocol("WEP");

        wireless.setBPoint(wifiRouter, 2);
        WifiRouter result = wireless.getBPoint(2);

        AssertUtilities.assertWifiRouter(wifiRouter, result);
    }

    @Test
    public void setGetBPoint_IndexOutOfBounds() throws Exception {
        int[] indexesFor1 = new int[] {-20, -1, 0, 20};
        int[] indexesFor3 = new int[] {-20, -1, 3, 20};
        int[][] allIndexes = new int[][] {indexesFor1, indexesFor3};
        Wireless[] switches = new Wireless[] {defaultWireless, wireless};
        Router router = CreateUtilities.createRouter();
        int setCount = 0;
        int getCount = 0;
        int expCount = indexesFor1.length + indexesFor3.length;

        for (int i = 0; i < switches.length; i++) {
            for (int j = 0; j < allIndexes[i].length; j++) {
                try {
                    switches[i].setBPoint(router, allIndexes[i][j]);
                } catch (IndexOutOfBoundsException e) {
                    if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                        setCount++;
                    }
                }
                try {
                    switches[i].getBPoint(allIndexes[i][j]);
                } catch (IndexOutOfBoundsException e) {
                    if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                        getCount++;
                    }
                }
            }
        }

        assertEquals(expCount, setCount);
        assertEquals(expCount, getCount);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        Wireless result1 = new com.netcracker.edu.inventory.model.connection.entity.impl.Wireless();
        result1.fillAllFields(wirelessWithDevices.getAllFields());

        AssertUtilities.assertWireless(wirelessWithDevices, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyConnection() throws Exception {
        Wireless result1 = new com.netcracker.edu.inventory.model.connection.entity.impl.Wireless();
        result1.fillAllFields(defaultWireless.getAllFields());

        AssertUtilities.assertWireless(defaultWireless, result1);
    }

    @Test
    public void isLazy() throws Exception {
        assertFalse(wireless.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        ConnectionPrimaryKey expConnectionPK = new ConnectionPK(wireless.getTrunk(), wireless.getSerialNumber());

        Unique.PrimaryKey primaryKey = wireless.getPrimaryKey();

        AssertUtilities.assertSomePK(expConnectionPK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultWireless.getPrimaryKey();

        assertNull(primaryKey);
    }

    @Test
    public void compareTo() throws Exception {
        Connection connection1 = new com.netcracker.edu.inventory.model.connection.entity.impl.Wireless();
        connection1.setTrunk(new TrunkImpl("", ""));
        connection1.setSerialNumber(1);
        Connection connection2 = new com.netcracker.edu.inventory.model.connection.entity.impl.Wireless();
        connection2.setTrunk(new TrunkImpl("", ""));
        connection2.setSerialNumber(2);
        Connection connection3 = new com.netcracker.edu.inventory.model.connection.entity.impl.Wireless();
        connection3.setTrunk(new TrunkImpl("", ""));
        connection3.setSerialNumber(3);
        Connection connection4 = new com.netcracker.edu.inventory.model.connection.entity.impl.Wireless();
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