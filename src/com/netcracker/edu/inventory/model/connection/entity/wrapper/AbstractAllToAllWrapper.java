package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.AllToAllConnection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.Set;

abstract class AbstractAllToAllWrapper extends AbstractConnectionWrapper implements AllToAllConnection {

    public AbstractAllToAllWrapper(AbstractAllConnectionsWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public ConnectorType getConnectorType() {
        return wrapper.getConnectorType();
    }

    @Override
    public boolean addDevice(Device device) {
        return wrapper.addDevice(device);
    }

    @Override
    public boolean removeDevice(Device device) {
        return wrapper.removeDevice(device);
    }

    @Override
    public boolean containDevice(Device device) {
        return containDevice(device);
    }

    @Override
    public Set getAllDevices() {
        return wrapper.getAllDevices();
    }

    @Override
    public int getCurSize() {
        return wrapper.getCurSize();
    }

    @Override
    public int getMaxSize() {
        return wrapper.getMaxSize();
    }

}
