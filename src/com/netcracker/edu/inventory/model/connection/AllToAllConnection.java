package com.netcracker.edu.inventory.model.connection;

import com.netcracker.edu.inventory.model.device.Device;

import java.util.Set;

/**
 * Created by makovetskyi on 10.11.16.
 */
public interface AllToAllConnection<T extends Device> extends Connection<T, T> {

    ConnectorType getConnectorType();
    boolean addDevice(T device);
    boolean removeDevice(T device);
    boolean containDevice(T device);
    Set<T> getAllDevices();
    int getCurSize();
    int getMaxSize();
    
}
