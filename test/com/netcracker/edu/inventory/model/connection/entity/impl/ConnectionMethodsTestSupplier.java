package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.location.Trunk;
import com.netcracker.edu.location.impl.TrunkImpl;

import static org.junit.Assert.assertEquals;

/**
 * Created by makovetskyi on 21.10.18.
 */
public class ConnectionMethodsTestSupplier {

    int serialNumber = 5;
    Trunk trunk = new TrunkImpl("setGetTrunk", "setGetTrunk");
    String defaultStatus = Connection.PLANED;
    String status = Connection.READY;

    int newSerialNumber0 = 0;
    int newSerialNumberNot0 = 6;
    int newSerialNumberLess0 = -6;

    public void setGetTrunk(Connection connection) throws Exception {
        connection.setTrunk(trunk);
        Trunk result = connection.getTrunk();

        assertEquals(trunk, result);
    }

    public void setGetSerialNumber(Connection connection) throws Exception {
        connection.setSerialNumber(serialNumber);
        int result = connection.getSerialNumber();

        assertEquals(serialNumber, result);
    }

    public void setSerialNumber_PrevNot0_newNot0(Connection connection) throws Exception {
        connection.setSerialNumber(serialNumber);

        connection.setSerialNumber(newSerialNumberNot0);
        int result = connection.getSerialNumber();

        assertEquals(serialNumber, result);
    }

    public void setSerialNumber_PrevNot0_new0(Connection connection) throws Exception {
        connection.setSerialNumber(serialNumber);

        connection.setSerialNumber(newSerialNumber0);
        int result = connection.getSerialNumber();

        assertEquals(serialNumber, result);
    }

    public void setSerialNumber_newLess0(Connection connection) throws Exception {
        connection.setSerialNumber(0);

        connection.setSerialNumber(newSerialNumberLess0);
    }

    public void defaultStatus(Connection connection) throws Exception {
        String result = connection.getStatus();

        assertEquals(defaultStatus, result);
    }

    public void setGetStatus(Connection connection) throws Exception {
        connection.setStatus(status);
        String result = connection.getStatus();

        assertEquals(status, result);
    }
}
