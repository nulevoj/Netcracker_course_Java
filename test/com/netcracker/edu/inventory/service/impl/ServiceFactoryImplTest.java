package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.service.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by makovetskyi on 27.05.17.
 */
public class ServiceFactoryImplTest {

    ServiceFactory serviceFactory;

    @Before
    public void setUp() throws Exception {
        serviceFactory = new ServiceFactoryImpl();
    }

    @Test
    public void createDeviceServiceImpl() throws Exception {
        DeviceService deviceService = serviceFactory.createDeviceServiceImpl();

        assertNotNull(deviceService);
    }

    @Test
    public void createConnectionServiceImpl() throws Exception {
        ConnectionService connectionService = serviceFactory.createConnectionServiceImpl();

        assertNotNull(connectionService);
    }

    @Test
    public void createRackServiceImpl() throws Exception {
        RackService rackService = serviceFactory.createRackServiceImpl();

        assertNotNull(rackService);
    }

    @Test
    public void createConcurrentServiceImpl() throws Exception {
        ConcurrentService concurrentIOService = serviceFactory.createConcurrentServiceImpl();

        assertNotNull(concurrentIOService);
    }

}