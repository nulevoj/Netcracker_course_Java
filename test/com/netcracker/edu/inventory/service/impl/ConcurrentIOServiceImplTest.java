package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.NetworkElement;
import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.rack.impl.RackArrayImpl;
import com.netcracker.edu.inventory.service.ConcurrentService;
import com.netcracker.edu.location.impl.LocationImpl;
import org.junit.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * Created by makovetskyi on 01.12.16.
 */
public class ConcurrentIOServiceImplTest {

    private static final int PIPED_BUFER_SIZE = 1024 * 4;
    private static final String FILE_NAME = "concurrentOut.txt";

    protected ConcurrentService service = new ConcurrentServiceImpl();
    protected PipedOutputStream pipedOutputStream;
    protected PipedInputStream pipedInputStream;
    protected PipedWriter pipedWriter;
    protected PipedReader pipedReader;
    protected Battery battery;
    protected TwistedPair twistedPair;
    protected WifiRouter wifiRouter;
    protected ThinCoaxial thinCoaxial;
    protected Rack emptyRack;
    protected Rack partlyRack;
    protected Collection<NetworkElement> elements;
    protected Collection<Rack> racks;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void setUp() throws Exception {
        pipedOutputStream = new PipedOutputStream();
        pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        pipedWriter = new PipedWriter();
        pipedReader = new PipedReader(pipedWriter, PIPED_BUFER_SIZE);

        elements = new ArrayList<>(4);
        elements.add(battery = CreateUtilities.createBattery());
        elements.add(twistedPair = CreateUtilities.createTwistedPair());
        elements.add(wifiRouter = CreateUtilities.createWifiRouter());
        elements.add(thinCoaxial = CreateUtilities.createThinCoaxial());

        emptyRack = new RackArrayImpl(1, Device.class);
        emptyRack.setLocation(new LocationImpl("ua.od.onpu.ics.607.storeroom", "NC_TC_Odessa_Server"));
        partlyRack = new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);
        racks = new ArrayList<>(2);
        racks.add(emptyRack);
        racks.add(partlyRack);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parallelOutputInputElements() throws Exception {
        Future<Collection<NetworkElement>> future = service.parallelOutputElements(elements, pipedOutputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        pipedOutputStream.close();

        future = service.parallelInputElements(3, pipedInputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        NetworkElement[] result1 = future.get().toArray(new NetworkElement[0]);
        future = service.parallelInputElements(3, pipedInputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        NetworkElement[] result2 = future.get().toArray(new NetworkElement[0]);
        pipedInputStream.close();

        assertEquals(3, result1.length);
        for (NetworkElement networkElement : result1) {
            if (battery.getClass() == networkElement.getClass()) {
                AssertUtilities.assertBattery(battery, (Battery) networkElement);
            } else if (twistedPair.getClass() == networkElement.getClass()) {
                AssertUtilities.assertTwistedPair(twistedPair, (TwistedPair) networkElement);
            } else if (wifiRouter.getClass() == networkElement.getClass()) {
                AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) networkElement);
            } else assert false;
        }
        assertEquals(1, result2.length);
        NetworkElement networkElement = result2[0];
        if (thinCoaxial.getClass() == networkElement.getClass()) {
            AssertUtilities.assertThinCoaxial(thinCoaxial, (ThinCoaxial) networkElement);
        } else assert false;
    }

    @Test
    public void parallelOutputInputRacks() throws Exception {
        Future<Collection<Rack>> future = service.parallelOutputRacks(racks, pipedOutputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        pipedOutputStream.close();

        future = service.parallelInputRacks(2, pipedInputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        Rack[] result1 = future.get().toArray(new Rack[0]);
        future = service.parallelInputRacks(2, pipedInputStream);
        while (!future.isDone()) {
            Thread.sleep(100);
        }
        Rack[] result2 = future.get().toArray(new Rack[0]);
        pipedInputStream.close();

        assertEquals(2, result1.length);
        for (Rack rack : result1) {
            if (rack.getSize() == 1) {
                AssertUtilities.assertRack(emptyRack, rack);
            } else if (rack.getSize() == 3) {
                AssertUtilities.assertRack(partlyRack, rack);
            } else throw new AssertionError();
        }
        assertEquals(0, result2.length);
    }

    @Test
    public void concurrency() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();

        Collection<NetworkElement> entities1 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            entities1.add(CreateUtilities.createBattery());
            entities1.add(CreateUtilities.createThinCoaxial());
            entities1.add(CreateUtilities.createRouter());
        }
        Collection<NetworkElement> entities2 = new ArrayList<>(entities1);
        for (int i = 0; i < 100; i++) {
            entities2.add(CreateUtilities.createTwistedPair());
            entities2.add(CreateUtilities.createSwitch());
            entities2.add(CreateUtilities.createOpticFiber());
        }
        Collection<NetworkElement> entities3 = new ArrayList<>(entities2);
        for (int i = 0; i < 100; i++) {
            entities3.add(CreateUtilities.createWifiRouter());
            entities3.add(CreateUtilities.createWireless());
        }

        Future[] futures = new Future[3];
        List<String> log = new ArrayList<>();
        int finished = 0;
        futures[0] = service.parallelOutputElements(entities3, byteArrayOutputStream1);
        futures[1] = service.parallelOutputElements(entities2, byteArrayOutputStream2);
        futures[2] = service.parallelOutputElements(entities1, byteArrayOutputStream3);

        while (finished < 3) {
            log.add("-tik-");
            for (int i = 0; i < 3; i++) {
                if ((futures[i] != null) && (futures[i].isDone())) {
                    finished++;
                    log.add("Task " + i + " finish!");
                    futures[i] = null;
                }
            }
            Thread.sleep(3);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (String line : log) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();

        Files.delete(Paths.get(FILE_NAME));
    }

}