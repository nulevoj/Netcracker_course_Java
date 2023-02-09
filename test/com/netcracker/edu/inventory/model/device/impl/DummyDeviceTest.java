package com.netcracker.edu.inventory.model.device.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 20.11.18.
 */
public class DummyDeviceTest {

    DummyDevice dummyDevice;

    @Before
    public void setUp() throws Exception {
        dummyDevice = new DummyDevice(new DevicePK(7));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void fillAllFields() throws Exception {
        dummyDevice.fillAllFields(new LinkedList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllFields() throws Exception {
        dummyDevice.getAllFields();
    }

    @Test
    public void isLazy() throws Exception {
        assertTrue(dummyDevice.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        assertTrue(new DevicePK(7).equals(dummyDevice.getPrimaryKey()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getIn() throws Exception {
        dummyDevice.getIn();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setIn() throws Exception {
        dummyDevice.setIn(8);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getType() throws Exception {
        dummyDevice.getType();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setType() throws Exception {
        dummyDevice.setType("test");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getManufacturer() throws Exception {
        dummyDevice.getManufacturer();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setManufacturer() throws Exception {
        dummyDevice.setManufacturer("test");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getModel() throws Exception {
        dummyDevice.getModel();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setModel() throws Exception {
        dummyDevice.setModel("Test");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getProductionDate() throws Exception {
        dummyDevice.getProductionDate();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setProductionDate() throws Exception {
        dummyDevice.setProductionDate(new Date());
    }

    @Test
    public void equals() throws Exception {
        DummyDevice dummyDevice1 = new DummyDevice(new DevicePK(1));
        DummyDevice dummyDevice2 = new DummyDevice(new DevicePK(2));
        DummyDevice dummyDevice3 = new DummyDevice(new DevicePK(2));

        assertFalse(dummyDevice1.equals(dummyDevice2));
        assertFalse(dummyDevice2.equals(dummyDevice1));
        assertTrue(dummyDevice2.equals(dummyDevice3));
        assertTrue(dummyDevice3.equals(dummyDevice2));
    }

    @Test
    public void compareTo() throws Exception {
        DummyDevice dummyDevice1 = new DummyDevice(new DevicePK(1));
        DummyDevice dummyDevice2 = new DummyDevice(new DevicePK(2));
        DummyDevice dummyDevice3 = new DummyDevice(new DevicePK(3));
        DummyDevice dummyDevice4 = new DummyDevice(new DevicePK(2));

        int result1 = dummyDevice2.compareTo(dummyDevice1);
        int result2 = dummyDevice2.compareTo(dummyDevice3);
        int result3 = dummyDevice2.compareTo(dummyDevice4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);
    }

}