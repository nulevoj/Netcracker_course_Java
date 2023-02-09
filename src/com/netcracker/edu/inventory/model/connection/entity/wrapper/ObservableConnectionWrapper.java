package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.location.Trunk;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObservableConnectionWrapper extends AbstractAllConnectionsWrapper {

    protected Set<PropertyChangeListener> listeners = new HashSet<>();

    public ObservableConnectionWrapper(Connection connection) {
        super(connection);
    }

    public boolean addListener(PropertyChangeListener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(PropertyChangeListener listener) {
        return listeners.remove(listener);
    }

    protected void notifyListeners(Object source, String propertyName, Object oldValue, Object newValue) {
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(new PropertyChangeEvent(source, propertyName, oldValue, newValue));
        }
    }

    @Override
    public void setTrunk(Trunk trunk) {
        notifyListeners(this, "trunk", getTrunk(), trunk);
        super.setTrunk(trunk);
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        notifyListeners(this, "serialNumber", getSerialNumber(), serialNumber);
        super.setSerialNumber(serialNumber);
    }

    @Override
    public void setStatus(String status) {
        notifyListeners(this, "status", getStatus(), status);
        super.setStatus(status);
    }

    @Override
    public void setAPoint(Device device) {
        notifyListeners(this, "aPoint", getAPoint(), device);
        super.setAPoint(device);
    }

    @Override
    public void setBPoint(Device device) {
        notifyListeners(this, "bPoint", getBPoint(), device);
        super.setBPoint(device);
    }

    @Override
    public void setAPoints(List devices) {
        notifyListeners(this, "aPoints", getAPoints(), devices);
        super.setAPoints(devices);
    }

    @Override
    public void setAPoint(Device device, int deviceNumber) {
        notifyListeners(this, "aPoints", getAPoint(deviceNumber), device);
        super.setAPoint(device, deviceNumber);
    }

    @Override
    public void setBPoints(List devices) {
        notifyListeners(this, "bPoints", getBPoints(), devices);
        super.setBPoints(devices);
    }

    @Override
    public void setBPoint(Device device, int deviceNumber) {
        notifyListeners(this, "bPoints", getBPoint(deviceNumber), device);
        super.setBPoint(device, deviceNumber);
    }

    @Override
    public boolean addDevice(Device device) {
        notifyListeners(this, "devices", null, device);
        return super.addDevice(device);
    }

    @Override
    public boolean removeDevice(Device device) {
        notifyListeners(this, "devices", device, null);
        return super.removeDevice(device);
    }

    @Override
    public void setLength(int length) {
        notifyListeners(this, "length", getLength(), length);
        super.setLength(length);
    }

    @Override
    public void setProtocol(String protocol) {
        notifyListeners(this, "protocol", getProtocol(), protocol);
        super.setProtocol(protocol);
    }

    @Override
    public void setVersion(int version) {
        notifyListeners(this, "version", getVersion(), version);
        super.setVersion(version);
    }

}
