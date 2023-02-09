package com.netcracker.edu.inventory.model.device.entity.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.impl.DevicePK;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class BatteryTest {

    DeviceMethodsTestSupplier deviceMethodsTestSupplier;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    Battery defaultBattery;
    Battery battery;

    int chargeVolume = 5;

    @Before
    public void before() throws Exception {
        deviceMethodsTestSupplier = new DeviceMethodsTestSupplier();
        defaultBattery = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery = CreateUtilities.createBattery();
    }

    @Test
    public void setGetIn() throws Exception {
        deviceMethodsTestSupplier.setGetIn(defaultBattery);
    }

    @Test
    public void setIn_PrevNot0_newNot0() throws Exception {
        deviceMethodsTestSupplier.setIn_PrevNot0_newNot0(defaultBattery);
    }

    @Test
    public void setIn_PrevNot0_new0() throws Exception {
        deviceMethodsTestSupplier.setIn_PrevNot0_new0(defaultBattery);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIn_newLess0() throws Exception {
        deviceMethodsTestSupplier.setIn_newLess0(defaultBattery);
    }

    @Test
    public void setGetType() throws Exception {
        deviceMethodsTestSupplier.setGetType(defaultBattery);
    }

    @Test
    public void setGetManufacturer() throws Exception {
        deviceMethodsTestSupplier.setGetManufacturer(defaultBattery);
    }

    @Test
    public void setGetModel() throws Exception {
        deviceMethodsTestSupplier.setGetModel(defaultBattery);
    }

    @Test
    public void setGetProductionDate() throws Exception {
        deviceMethodsTestSupplier.setGetProductionDate(defaultBattery);
    }

    @Test
    public void compareTo() throws Exception {
        deviceMethodsTestSupplier.compareTo(
                new com.netcracker.edu.inventory.model.device.entity.impl.Battery(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Battery(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Battery(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Battery());
    }

    @Test
    public void setGetChargeVolume() throws Exception {
        defaultBattery.setChargeVolume(chargeVolume);
        int result = defaultBattery.getChargeVolume();

        assertEquals(chargeVolume, result);
    }

    @Test
    public void testGetAndFillAllFields() throws Exception {
        Battery result = defaultBattery;
        result.fillAllFields(battery.getAllFields());

        AssertUtilities.assertBattery(battery, result);
    }

    @Test
    public void testGetAndFillAllFields_EmptyDevice() throws Exception {
        Battery result = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        result.fillAllFields(defaultBattery.getAllFields());

        AssertUtilities.assertBattery(defaultBattery, result);
    }

    @Test
    public void isLazy() throws Exception {
        assertFalse(battery.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        DevicePrimaryKey expDevicePK = new DevicePK(battery.getIn());

        Unique.PrimaryKey primaryKey = battery.getPrimaryKey();

        AssertUtilities.assertSomePK(expDevicePK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultBattery.getPrimaryKey();

        assertNull(primaryKey);
    }

}