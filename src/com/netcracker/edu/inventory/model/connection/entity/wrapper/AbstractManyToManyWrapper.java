package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.ManyToManyConnection;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.List;

abstract class AbstractManyToManyWrapper extends AbstractConnectionWrapper implements ManyToManyConnection {

    public AbstractManyToManyWrapper(AbstractAllConnectionsWrapper wrapper) {
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
    public List getAPoints() {
        return wrapper.getAPoints();
    }

    @Override
    public void setAPoints(List devices) {
        wrapper.setAPoints(devices);
    }

    @Override
    public int getACapacity() {
        return wrapper.getACapacity();
    }

    @Override
    public Device getAPoint(int deviceNumber) {
        return wrapper.getAPoint(deviceNumber);
    }

    @Override
    public void setAPoint(Device device, int deviceNumber) {
        wrapper.setAPoint(device, deviceNumber);
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
