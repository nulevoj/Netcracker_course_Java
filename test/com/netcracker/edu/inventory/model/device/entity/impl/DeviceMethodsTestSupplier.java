package com.netcracker.edu.inventory.model.device.entity.impl;

import com.netcracker.edu.inventory.model.device.Device;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by makovetskyi on 28.10.18.
 */
public class DeviceMethodsTestSupplier {

    int in = 5;
    String type = "Bridge";
    String manufacturer = "D-Link";
    String model = "none";
    Date productionDate = new Date();

    int newIn0 = 0;
    int newInNot0 = 6;
    int newLess0 = -6;

    public void setGetIn(Device device) throws Exception {
        device.setIn(in);
        int result = device.getIn();

        assertEquals(in, result);
    }

    public void setIn_PrevNot0_newNot0(Device device) throws Exception {
        device.setIn(in);

        device.setIn(newInNot0);
        int result = device.getIn();

        assertEquals(in, result);
    }

    public void setIn_PrevNot0_new0(Device device) throws Exception {
        device.setIn(in);

        device.setIn(newIn0);
        int result = device.getIn();

        assertEquals(in, result);
    }

    public void setIn_newLess0(Device device) throws Exception {
        device.setIn(0);

        device.setIn(newLess0);
    }

    public void setGetType(Device device) throws Exception {
        device.setType(type);
        String result = device.getType();

        assertEquals(type, result);
    }

    public void setGetManufacturer(Device device) throws Exception {
        device.setManufacturer(manufacturer);
        String result = device.getManufacturer();

        assertEquals(manufacturer, result);
    }

    public void setGetModel(Device device) throws Exception {
        device.setModel(model);
        String result = device.getModel();

        assertEquals(model, result);
    }

    public void setGetProductionDate(Device device) throws Exception {
        device.setProductionDate(productionDate);
        Date result = device.getProductionDate();

        assertEquals(productionDate, result);
    }

    public void compareTo(Device device1, Device device2, Device device3, Device device4) throws Exception {
        device1.setIn(1);
        device2.setIn(2);
        device3.setIn(3);
        device4.setIn(2);

        int result1 = device2.compareTo(device1);
        int result2 = device2.compareTo(device3);
        int result3 = device2.compareTo(device4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);
    }

}