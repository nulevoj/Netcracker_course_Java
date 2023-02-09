package com.netcracker.edu.inventory.model.connection.impl;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.location.Trunk;
import com.netcracker.edu.location.impl.TrunkImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 20.11.18.
 */
public class DummyConnectionTest {

    DummyConnection dummyConnection;

    @Before
    public void setUp() throws Exception {
        dummyConnection = new DummyConnection(new ConnectionPK(new TrunkImpl("Test", "test"), 3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void fillAllFields() throws Exception {
        dummyConnection.fillAllFields(new LinkedList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllFields() throws Exception {
        dummyConnection.getAllFields();
    }

    @Test
    public void isLazy() throws Exception {
        assertTrue(dummyConnection.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        assertTrue(new ConnectionPK(new TrunkImpl("Test", "test"), 3)
                .equals(dummyConnection.getPrimaryKey()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getTrunk() throws Exception {
        dummyConnection.getTrunk();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setTrunk() throws Exception {
        dummyConnection.setTrunk(new TrunkImpl("Begin-End", "BE"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getSerialNumber() throws Exception {
        dummyConnection.getSerialNumber();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setSerialNumber() throws Exception {
        dummyConnection.setSerialNumber(9);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getStatus() throws Exception {
        dummyConnection.getStatus();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setStatus() throws Exception {
        dummyConnection.setStatus(Connection.DISASSEMBLED);
    }

    @Test
    public void equals() throws Exception {
        Trunk trunk1 = new TrunkImpl("Trunk", "1");
        Trunk trunk2 = new TrunkImpl("Trunk", "2");
        DummyConnection dummyConnection1 = new DummyConnection(new ConnectionPK(trunk1, 1));
        DummyConnection dummyConnection2 = new DummyConnection(new ConnectionPK(trunk1, 2));
        DummyConnection dummyConnection3 = new DummyConnection(new ConnectionPK(trunk1, 2));
        DummyConnection dummyConnection4 = new DummyConnection(new ConnectionPK(trunk1, 1));
        DummyConnection dummyConnection5 = new DummyConnection(new ConnectionPK(trunk2, 1));
        DummyConnection dummyConnection6 = new DummyConnection(new ConnectionPK(trunk2, 1));

        assertFalse(dummyConnection1.equals(dummyConnection2));
        assertFalse(dummyConnection2.equals(dummyConnection1));
        assertTrue(dummyConnection2.equals(dummyConnection3));
        assertTrue(dummyConnection3.equals(dummyConnection2));
        assertFalse(dummyConnection4.equals(dummyConnection5));
        assertFalse(dummyConnection5.equals(dummyConnection4));
        assertTrue(dummyConnection5.equals(dummyConnection6));
        assertTrue(dummyConnection6.equals(dummyConnection5));
    }

    @Test
    public void compareTo() throws Exception {
        Trunk trunk = new TrunkImpl("Trunk", "trunk");
        DummyConnection dummyConnection1 = new DummyConnection(new ConnectionPK(trunk, 1));
        DummyConnection dummyConnection2 = new DummyConnection(new ConnectionPK(trunk, 2));
        DummyConnection dummyConnection3 = new DummyConnection(new ConnectionPK(trunk, 3));
        DummyConnection dummyConnection4 = new DummyConnection(new ConnectionPK(trunk, 2));

        int result1 = dummyConnection2.compareTo(dummyConnection1);
        int result2 = dummyConnection2.compareTo(dummyConnection3);
        int result3 = dummyConnection2.compareTo(dummyConnection4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);
    }

}