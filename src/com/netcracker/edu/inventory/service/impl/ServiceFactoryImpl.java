package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.service.*;

public class ServiceFactoryImpl implements ServiceFactory {

    @Override
    public DeviceService createDeviceServiceImpl() {
        return new DeviceServiceImpl();
    }

    @Override
    public ConnectionService createConnectionServiceImpl() {
        return new ConnectionServiceImpl();
    }

    @Override
    public RackService createRackServiceImpl() {
        return new RackServiceImpl();
    }

    @Override
    public ConcurrentService createConcurrentServiceImpl() {
        return new ConcurrentServiceImpl();
    }

}
