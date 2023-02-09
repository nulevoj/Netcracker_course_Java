package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.OneToManyConnection;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.List;

abstract class AbstractOneToManyWrapper extends AbstractConnectionWrapper implements OneToManyConnection {

    public AbstractOneToManyWrapper(AbstractAllConnectionsWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return wrapper.getAPointConnectorType();
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return wrapper.getBPointConnectorType();
    }

    @Override
    public Device getAPoint() {
        return wrapper.getAPoint();
    }

    @Override
    public void setAPoint(Device device) {
        wrapper.setAPoint(device);
    }

    @Override
    public List getBPoints() {
        return wrapper.getBPoints();
    }

    @Override
    public void setBPoints(List devices) {
        wrapper.setBPoints(devices);
    }

    @Override
    public int getBCapacity() {
        return wrapper.getBCapacity();
    }

    @Override
    public Device getBPoint(int deviceNumber) {
        return wrapper.getBPoint(deviceNumber);
    }

    @Override
    public void setBPoint(Device device, int deviceNumber) {
        wrapper.setBPoint(device, deviceNumber);
    }

}
