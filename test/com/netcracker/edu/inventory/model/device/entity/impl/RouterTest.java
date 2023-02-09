package com.netcracker.edu.inventory.model.device.entity.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.impl.DevicePK;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oleksandr on 05.10.16.
 */
public class RouterTest {

    DeviceMethodsTestSupplier deviceMethodsTestSupplier;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    Router defaultRouter;
    Router router;

    int dataRate = 0;

    @Before
    public void before() throws Exception {
        deviceMethodsTestSupplier = new DeviceMethodsTestSupplier();
        defaultRouter = new com.netcracker.edu.inventory.model.device.entity.impl.Router();
        router = CreateUtilities.createRouter();
    }

    @Test
    public void setGetIn() throws Exception {
        deviceMethodsTestSupplier.setGetIn(defaultRouter);
    }

    @Test
    public void setIn_PrevNot0_newNot0() throws Exception {
        deviceMethodsTestSupplier.setIn_PrevNot0_newNot0(defaultRouter);
    }

    @Test
    public void setIn_PrevNot0_new0() throws Exception {
        deviceMethodsTestSupplier.setIn_PrevNot0_new0(defaultRouter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIn_newLess0() throws Exception {
        deviceMethodsTestSupplier.setIn_newLess0(defaultRouter);
    }

    @Test
    public void setGetType() throws Exception {
        deviceMethodsTestSupplier.setGetType(defaultRouter);
    }

    @Test
    public void setGetManufacturer() throws Exception {
        deviceMethodsTestSupplier.setGetManufacturer(defaultRouter);
    }

    @Test
    public void setGetModel() throws Exception {
        deviceMethodsTestSupplier.setGetModel(defaultRouter);
    }

    @Test
    public void setGetProductionDate() throws Exception {
        deviceMethodsTestSupplier.setGetProductionDate(defaultRouter);
    }

    @Test
    public void compareTo() throws Exception {
        deviceMethodsTestSupplier.compareTo(
                new com.netcracker.edu.inventory.model.device.entity.impl.Router(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Router(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Router(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Router());
    }

    @Test
    public void setGetDataRate() throws Exception {
        defaultRouter.setDataRate(dataRate);
        int result = defaultRouter.getDataRate();

        assertEquals(dataRate, result);
    }

    @Test
    public void testGetAndFillAllFields() throws Exception {
        Router result = defaultRouter;
        result.fillAllFields(router.getAllFields());

        AssertUtilities.assertRouter(router, result);
    }

    @Test
    public void testGetAndFillAllFields_EmptyDevice() throws Exception {
        Router result = new com.netcracker.edu.inventory.model.device.entity.impl.Router();
        result.fillAllFields(defaultRouter.getAllFields());

        AssertUtilities.assertRouter(defaultRouter, result);
    }

    @Test
    public void isLazy() throws Exception {
        assertFalse(router.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        DevicePrimaryKey expDevicePK = new DevicePK(router.getIn());

        Unique.PrimaryKey primaryKey = router.getPrimaryKey();

        AssertUtilities.assertSomePK(expDevicePK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultRouter.getPrimaryKey();

        assertNull(primaryKey);
    }

}