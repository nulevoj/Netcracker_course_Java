package com.netcracker.edu.inventory.model.rack.impl;

import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.impl.LocationImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 23.11.2016.
 */
public class RackPKTest {

    RackPK rackPK;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        rackPK = new RackPK(new LocationImpl("Test", "test"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getLocation() throws Exception {
        Location expLocation = new LocationImpl("Another", "Trunk");
        RackPK pk = new RackPK(expLocation);

        Location location = pk.getLocation();

        assertEquals(expLocation, location);
    }

    @Test
    public void equals() throws Exception {
        Location location1 = new LocationImpl("Location", "1");
        Location location2 = new LocationImpl("Location", "2");
        RackPK pk1 = new RackPK(location1);
        RackPK pk2 = new RackPK(location2);
        RackPK pk3 = new RackPK(location2);

        assertFalse(pk1.equals(pk2));
        assertFalse(pk2.equals(pk1));
        assertTrue(pk2.equals(pk3));
        assertTrue(pk3.equals(pk2));
    }

}