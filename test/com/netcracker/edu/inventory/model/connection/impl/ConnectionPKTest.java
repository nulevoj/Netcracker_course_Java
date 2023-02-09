package com.netcracker.edu.inventory.model.connection.impl;

import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.location.Trunk;
import com.netcracker.edu.location.impl.TrunkImpl;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 23.11.2016.
 */
public class ConnectionPKTest {

    ConnectionPK connectionPK;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        connectionPK = new ConnectionPK(new TrunkImpl("Test", "test"), 3);
    }

    @Test
    public void getTrunk() throws Exception {
        Trunk expTrunk = new TrunkImpl("Another", "Trunk");
        int expSerialNumber = 5;
        ConnectionPK pk = new ConnectionPK(expTrunk, expSerialNumber);

        Trunk trunk = pk.getTrunk();

        assertEquals(expTrunk, trunk);
    }

    @Test
    public void getSerialNumber() throws Exception {
        Trunk expTrunk = new TrunkImpl("Another", "Trunk");
        int expSerialNumber = 5;
        ConnectionPK pk = new ConnectionPK(expTrunk, expSerialNumber);

        int serialNumber = pk.getSerialNumber();

        assertEquals(expSerialNumber, serialNumber);
    }

    @Test
    public void equals() throws Exception {
        Trunk trunk1 = new TrunkImpl("Trunk", "1");
        Trunk trunk2 = new TrunkImpl("Trunk", "2");
        ConnectionPK pk1 = new ConnectionPK(trunk1, 1);
        ConnectionPK pk2 = new ConnectionPK(trunk1, 2);
        ConnectionPK pk3 = new ConnectionPK(trunk1, 2);
        ConnectionPK pk4 = new ConnectionPK(trunk1, 1);
        ConnectionPK pk5 = new ConnectionPK(trunk2, 1);
        ConnectionPK pk6 = new ConnectionPK(trunk2, 1);

        assertFalse(pk1.equals(pk2));
        assertFalse(pk2.equals(pk1));
        assertTrue(pk2.equals(pk3));
        assertTrue(pk3.equals(pk2));
        assertFalse(pk4.equals(pk5));
        assertFalse(pk5.equals(pk4));
        assertTrue(pk5.equals(pk6));
        assertTrue(pk6.equals(pk5));
    }

    @Test
    public void compareTo() throws Exception {
        Trunk trunk = new TrunkImpl("Trunk", "trunk");
        ConnectionPK pk1 = new ConnectionPK(trunk, 1);
        ConnectionPK pk2 = new ConnectionPK(trunk, 2);
        ConnectionPK pk3 = new ConnectionPK(trunk, 3);
        ConnectionPK pk4 = new ConnectionPK(trunk, 2);

        int result1 = pk2.compareTo(pk1);
        int result2 = pk2.compareTo(pk3);
        int result3 = pk2.compareTo(pk4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);
    }

}