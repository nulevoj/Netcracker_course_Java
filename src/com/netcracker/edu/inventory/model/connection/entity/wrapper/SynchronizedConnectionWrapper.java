package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.location.Trunk;

import java.beans.PropertyChangeListener;
import java.util.List;

public class SynchronizedConnectionWrapper extends ObservableConnectionWrapper {

    public SynchronizedConnectionWrapper(Connection connection) {
        super(connection);
    }

    @Override
    public synchronized boolean addListener(PropertyChangeListener listener) {
        return super.addListener(listener);
    }

    @Override
    public synchronized boolean removeListener(PropertyChangeListener listener) {
        return super.removeListener(listener);
    }

    @Override
    protected synchronized void notifyListeners(Object source, String propertyName, Object oldValue, Object newValue) {
        super.notifyListeners(source, propertyName, oldValue, newValue);
    }

    @Override
    public synchronized void setTrunk(Trunk trunk) {
        super.setTrunk(trunk);
    }

    @Override
    public synchronized void setSerialNumber(int serialNumber) {
        super.setSerialNumber(serialNumber);
    }

    @Override
    public synchronized void setStatus(String status) {
        super.setStatus(status);
    }

    @Override
    public synchronized void setAPoint(Device device) {
        super.setAPoint(device);
    }

    @Override
    public synchronized void setBPoint(Device device) {
        super.setBPoint(device);
    }

    @Override
    public synchronized void setAPoints(List devices) {
        super.setAPoints(devices);
    }

    @Override
    public synchronized void setAPoint(Device device, int deviceNumber) {
        super.setAPoint(device, deviceNumber);
    }

    @Override
    public synchronized void setBPoints(List devices) {
        super.setBPoints(devices);
    }

    @Override
    public synchronized void setBPoint(Device device, int deviceNumber) {
        super.setBPoint(device, deviceNumber);
    }

    @Override
    public synchronized boolean addDevice(Device device) {
        return super.addDevice(device);
    }

    @Override
    public synchronized boolean removeDevice(Device device) {
        return super.removeDevice(device);
    }

    @Override
    public synchronized void setLength(int length) {
        super.setLength(length);
    }

    @Override
    public synchronized void setProtocol(String protocol) {
        super.setProtocol(protocol);
    }

    @Override
    public synchronized void setVersion(int version) {
        super.setVersion(version);
    }

}
