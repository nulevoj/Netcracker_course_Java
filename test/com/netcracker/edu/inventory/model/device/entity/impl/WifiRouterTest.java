package com.netcracker.edu.inventory.model.device.entity.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.connection.entity.Wireless;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.model.device.impl.DevicePK;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class WifiRouterTest {

    DeviceMethodsTestSupplier deviceMethodsTestSupplier;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    WifiRouter defaultWifiRouter;
    WifiRouter wifiRouter;
    WifiRouter wifiRouterWithConnections;
    String technologyVersion = "802.11g";

    String securityProtocol = "";

    @Before
    public void before() throws Exception {
        deviceMethodsTestSupplier = new DeviceMethodsTestSupplier();
        defaultWifiRouter = new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter();
        wifiRouter = CreateUtilities.createWifiRouter();
        wifiRouterWithConnections = CreateUtilities.createWifiRouterWithConnections();
    }

    @Test
    public void setGetIn() throws Exception {
        deviceMethodsTestSupplier.setGetIn(defaultWifiRouter);
    }

    @Test
    public void setIn_PrevNot0_newNot0() throws Exception {
        deviceMethodsTestSupplier.setIn_PrevNot0_newNot0(defaultWifiRouter);
    }

    @Test
    public void setIn_PrevNot0_new0() throws Exception {
        deviceMethodsTestSupplier.setIn_PrevNot0_new0(defaultWifiRouter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIn_newLess0() throws Exception {
        deviceMethodsTestSupplier.setIn_newLess0(defaultWifiRouter);
    }

    @Test
    public void setGetType() throws Exception {
        deviceMethodsTestSupplier.setGetType(defaultWifiRouter);
    }

    @Test
    public void setGetManufacturer() throws Exception {
        deviceMethodsTestSupplier.setGetManufacturer(defaultWifiRouter);
    }

    @Test
    public void setGetModel() throws Exception {
        deviceMethodsTestSupplier.setGetModel(defaultWifiRouter);
    }

    @Test
    public void setGetProductionDate() throws Exception {
        deviceMethodsTestSupplier.setGetProductionDate(defaultWifiRouter);
    }

    @Test
    public void compareTo() throws Exception {
        deviceMethodsTestSupplier.compareTo(
                new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter(),
                new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter(),
                new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter(),
                new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter());
    }

    @Test
    public void getTechnologyVersion() throws Exception {
        assertNull(defaultWifiRouter.getTechnologyVersion());
        assertEquals(technologyVersion, wifiRouter.getTechnologyVersion());
    }

    @Test
    public void setGetSecurityProtocol() throws Exception {
        defaultWifiRouter.setSecurityProtocol(securityProtocol);
        String result = defaultWifiRouter.getSecurityProtocol();

        assertEquals(securityProtocol, result);
    }

    @Test
    public void setGetWirelessConnection() throws Exception {
        Wireless wireless = CreateUtilities.createWireless();
        wireless.setVersion(1);

        defaultWifiRouter.setWireConnection(wireless);
        Wireless result = (Wireless) defaultWifiRouter.getWireConnection();

        AssertUtilities.assertWireless(wireless, result);
    }

    @Test
    public void getWirePortType() throws Exception {
        assertEquals(ConnectorType.need_init, defaultWifiRouter.getWirePortType());
        assertEquals(ConnectorType.RJ45, wifiRouter.getWirePortType());
    }

    @Test
    public void setGetWireConnection() throws Exception {
        TwistedPair twistedPair = CreateUtilities.createTwistedPair();
        twistedPair.setLength(10);

        defaultWifiRouter.setWireConnection(twistedPair);
        TwistedPair result = (TwistedPair) defaultWifiRouter.getWireConnection();

        AssertUtilities.assertTwistedPair(twistedPair, result);
    }

    @Test
    public void testGetAndFillAllFields() throws Exception {
        WifiRouter result = defaultWifiRouter;
        result.fillAllFields(wifiRouterWithConnections.getAllFields());

        AssertUtilities.assertWifiRouter(wifiRouterWithConnections, result);
    }

    @Test
    public void testGetAndFillAllFields_EmptyDevice() throws Exception {
        WifiRouter result = new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter();
        result.fillAllFields(defaultWifiRouter.getAllFields());

        AssertUtilities.assertWifiRouter(defaultWifiRouter, result);
    }

    @Test
    public void isLazy() throws Exception {
        assertFalse(defaultWifiRouter.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        DevicePrimaryKey expDevicePK = new DevicePK(wifiRouter.getIn());

        Unique.PrimaryKey primaryKey = wifiRouter.getPrimaryKey();

        AssertUtilities.assertSomePK(expDevicePK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultWifiRouter.getPrimaryKey();

        assertNull(primaryKey);
    }

}