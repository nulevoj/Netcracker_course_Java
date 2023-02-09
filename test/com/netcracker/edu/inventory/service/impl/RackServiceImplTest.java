package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.rack.impl.RackArrayImpl;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.location.impl.LocationImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class RackServiceImplTest {

    private static final int PIPED_BUFER_SIZE = 1024*4;
    private static final String FILE_NAME = "testOutRack.bin";

    RackService rackService;
    Rack<Device> emptyRack;
    Rack<Router> partlyRack;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        rackService = new RackServiceImpl();

        emptyRack = new RackArrayImpl(1, Device.class);
        emptyRack.setLocation(new LocationImpl("ua.od.onpu.ics.607.storeroom", "NC_TC_Odessa_Server"));

        partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);
    }

    @Test
    public void isValidRackForOutputToStream() throws Exception {

        boolean result1 = rackService.isValidRackForOutputToStream(emptyRack);
        boolean result2 = rackService.isValidRackForOutputToStream(partlyRack);

        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    public void isValidRackForOutputToStream_DeviceNull() throws Exception {
        boolean result = rackService.isValidRackForOutputToStream(null);

        assertFalse(result);
    }

    @Test
    public void isValidRackForOutputToStream_RackAttributeInvalid() throws Exception {
        emptyRack.setLocation(new LocationImpl("ua.od.onpu.ics.607.storeroom", "NC_TC_Odessa\nServer"));

        boolean result = rackService.isValidRackForOutputToStream(emptyRack);

        assertFalse(result);
    }

    @Test
    public void isValidRackForOutputToStream_DeviceAttributeInvalid() throws Exception {
        partlyRack.getDevAtSlot(0).setModel("Super\nPuper");

        boolean result = rackService.isValidRackForOutputToStream(partlyRack);

        assertFalse(result);
    }

    @Test
    public void outputInputRack() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);

        rackService.outputRack(emptyRack, pipedOutputStream);
        rackService.outputRack(partlyRack, pipedOutputStream);
        pipedOutputStream.close();

        Rack result1 = rackService.inputRack(pipedInputStream);
        Rack result2 = rackService.inputRack(pipedInputStream);
        pipedInputStream.close();

        AssertUtilities.assertRack(emptyRack, result1);
        AssertUtilities.assertRack(partlyRack, result2);
    }

    @Test
    public void outputRackRackNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        rackService.outputRack(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        rackService.outputRack(emptyRack, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputRackNull() throws Exception {
        rackService.inputRack(null);
    }

    @Test
    public void outputToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);

        rackService.outputRack(emptyRack, fileOutputStream);
        rackService.outputRack(partlyRack, fileOutputStream);
        fileOutputStream.close();

        Files.delete(Paths.get(FILE_NAME));
    }

}