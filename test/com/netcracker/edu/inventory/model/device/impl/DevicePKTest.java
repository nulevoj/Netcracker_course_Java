package com.netcracker.edu.inventory.model.device.impl;

import com.netcracker.edu.inventory.LoggerInitializer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 23.11.2016.
 */
public class DevicePKTest {

    DevicePK devicePK;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        devicePK = new DevicePK(7);
    }

    @Test
    public void getIn() throws Exception {
        int expIN = 6;
        DevicePK pk = new DevicePK(expIN);

        int in = pk.getIn();

        assertEquals(expIN, in);
    }

    @Test
    public void equals() throws Exception {
        DevicePK pk1 = new DevicePK(1);
        DevicePK pk2 = new DevicePK(2);
        DevicePK pk3 = new DevicePK(2);

        assertFalse(pk1.equals(pk2));
        assertFalse(pk2.equals(pk1));
        assertTrue(pk2.equals(pk3));
        assertTrue(pk3.equals(pk2));
    }

    @Test
    public void compareTo() throws Exception {
        DevicePK pk1 = new DevicePK(1);
        DevicePK pk2 = new DevicePK(2);
        DevicePK pk3 = new DevicePK(3);
        DevicePK pk4 = new DevicePK(2);

        int result1 = pk2.compareTo(pk1);
        int result2 = pk2.compareTo(pk3);
        int result3 = pk2.compareTo(pk4);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
        assertTrue(result3 == 0);
    }

}