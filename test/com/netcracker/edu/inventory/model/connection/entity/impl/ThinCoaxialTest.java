package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.location.impl.TrunkImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 13.11.16.
 */
public class ThinCoaxialTest {

    ConnectionMethodsTestSupplier connectionMethodsTestSupplier;

    ThinCoaxial<Router> defaultThinCoaxial;
    ThinCoaxial<Router> thinCoaxial;
    ThinCoaxial<Router> thinCoaxialWithDevices;
    int defaultCurSize = 0;
    int curSize = 0;
    int defaultMaxSize = 0;
    int maxSize = 5;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        connectionMethodsTestSupplier = new ConnectionMethodsTestSupplier();
        defaultThinCoaxial = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
        thinCoaxial = CreateUtilities.createThinCoaxial();
        thinCoaxialWithDevices = CreateUtilities.createThinCoaxialWithDevices();
    }

    @Test
    public void setGetTrunk() throws Exception {
        connectionMethodsTestSupplier.setGetTrunk(defaultThinCoaxial);
    }

    @Test
    public void setGetSerialNumber() throws Exception {
        connectionMethodsTestSupplier.setGetSerialNumber(defaultThinCoaxial);
    }

    @Test
    public void setSerialNumber_PrevNot0_newNot0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_PrevNot0_newNot0(defaultThinCoaxial);
    }

    @Test
    public void setSerialNumber_PrevNot0_new0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_PrevNot0_new0(defaultThinCoaxial);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSerialNumber_newLess0() throws Exception {
        connectionMethodsTestSupplier.setSerialNumber_newLess0(defaultThinCoaxial);
    }

    @Test
    public void defaultStatus() throws Exception {
        connectionMethodsTestSupplier.defaultStatus(defaultThinCoaxial);
    }

    @Test
    public void setGetStatus() throws Exception {
        connectionMethodsTestSupplier.setGetStatus(defaultThinCoaxial);
    }

    @Test
    public void getConnectorType() throws Exception {
        assertEquals(ConnectorType.TConnector, defaultThinCoaxial.getConnectorType());
        assertEquals(ConnectorType.TConnector, thinCoaxial.getConnectorType());
    }

    @Test
    public void addDevice() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();
        ThinCoaxial<Router> thinCoaxial = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial<>(6);
        boolean result1 = thinCoaxial.addDevice(aSwitch);

        Set<Router> result2 = thinCoaxial.getAllDevices();

        assertTrue(result1);
        assertTrue(result2.contains(aSwitch));
    }

    @Test
    public void removeDevice() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();
        ThinCoaxial<Router> thinCoaxial = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial<>(6);
        thinCoaxial.addDevice(aSwitch);
        thinCoaxial.addDevice(wifiRouter);
        boolean result1 = thinCoaxial.containDevice(aSwitch);
        boolean result2 = thinCoaxial.containDevice(wifiRouter);
        thinCoaxial.removeDevice(aSwitch);
        boolean result3 = thinCoaxial.containDevice(aSwitch);
        boolean result4 = thinCoaxial.containDevice(wifiRouter);
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
        assertTrue(result4);
    }

    @Test
    public void containDevice() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();
        ThinCoaxial<Router> thinCoaxial = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial<>(6);
        boolean result1 = thinCoaxial.containDevice(aSwitch);
        thinCoaxial.addDevice(aSwitch);
        boolean result2 = thinCoaxial.containDevice(aSwitch);
        assertFalse(result1);
        assertTrue(result2);
    }

    @Test
    public void defaultAllDevices() throws Exception {
        Set<Router> result = defaultThinCoaxial.getAllDevices();

        assertEquals(new HashSet<Router>(), result);
    }

    @Test
    public void getAllDevices() throws Exception {
        Set<Router> expSet = new HashSet<>();
        Router router = CreateUtilities.createRouter();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();
        Switch aSwitch = CreateUtilities.createSwitch();
        expSet.add(router);
        expSet.add(wifiRouter);
        expSet.add(aSwitch);
        ThinCoaxial<Router> thinCoaxial = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial<>(6);
        thinCoaxial.addDevice(aSwitch);
        thinCoaxial.addDevice(router);
        thinCoaxial.addDevice(aSwitch);
        thinCoaxial.addDevice(wifiRouter);
        thinCoaxial.addDevice(router);

        Set<Router> result = thinCoaxial.getAllDevices();

        assertEquals(expSet, result);
    }

    @Test
    public void getCurSize() throws Exception {
        assertEquals(defaultCurSize, defaultThinCoaxial.getCurSize());
        assertEquals(curSize, thinCoaxial.getCurSize());
    }

    @Test
    public void getMaxSize() throws Exception {
        assertEquals(defaultMaxSize, defaultThinCoaxial.getMaxSize());
        assertEquals(maxSize, thinCoaxial.getMaxSize());
    }

    @Test
    public void testGetAndFillAllFields() throws Exception {
        ThinCoaxial result1 = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
        result1.fillAllFields(thinCoaxialWithDevices.getAllFields());

        AssertUtilities.assertThinCoaxial(thinCoaxialWithDevices, result1);
    }

    @Test
    public void testGetAndFillAllFields_EmptyConnection() throws Exception {
        ThinCoaxial result1 = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
        result1.fillAllFields(defaultThinCoaxial.getAllFields());

        AssertUtilities.assertThinCoaxial(defaultThinCoaxial, result1);
    }

    @Test
    public void isLazy() throws Exception {
        assertFalse(thinCoaxial.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        ConnectionPrimaryKey expConnectionPK = new ConnectionPK(thinCoaxial.getTrunk(), thinCoaxial.getSerialNumber());

        Unique.PrimaryKey primaryKey = thinCoaxial.getPrimaryKey();

        AssertUtilities.assertSomePK(expConnectionPK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultThinCoaxial.getPrimaryKey();

        assertNull(primaryKey);
    }

    @Test
    public void compareTo() throws Exception {
        Connection connection1 = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
        connection1.setTrunk(new TrunkImpl("", ""));
        connection1.setSerialNumber(1);
        Connection connection2 = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
        connection2.setTrunk(new TrunkImpl("", ""));
        connection2.setSerialNumber(2);
        Connection connection3 = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
        connection3.setTrunk(new TrunkImpl("", ""));
        connection3.setSerialNumber(3);
        Connection connection4 = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
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