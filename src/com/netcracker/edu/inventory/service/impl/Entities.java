package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.NetworkElement;

public enum Entities {
    NetworkElement(com.netcracker.edu.inventory.model.NetworkElement.class),

    Connection(com.netcracker.edu.inventory.model.connection.Connection.class),
    OpticFiber(com.netcracker.edu.inventory.model.connection.entity.OpticFiber.class),
    ThinCoaxial(com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial.class),
    TwistedPair(com.netcracker.edu.inventory.model.connection.entity.TwistedPair.class),
    Wireless(com.netcracker.edu.inventory.model.connection.entity.Wireless.class),

    Device(com.netcracker.edu.inventory.model.device.Device.class),
    Battery(com.netcracker.edu.inventory.model.device.entity.Battery.class),
    Router(com.netcracker.edu.inventory.model.device.entity.Router.class),
    Switch(com.netcracker.edu.inventory.model.device.entity.Switch.class),
    WifiRouter(com.netcracker.edu.inventory.model.device.entity.WifiRouter.class);

    private Class<? extends NetworkElement> clazz;

    Entities(Class<? extends NetworkElement> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends NetworkElement> getClazz() {
        return clazz;
    }

}
