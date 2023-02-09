package com.netcracker.edu.inventory.model.device.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.device.Device;

import java.beans.PropertyChangeListener;
import java.util.Date;

public class SynchronizedDeviceWrapper extends ObservableDeviceWrapper {

    public SynchronizedDeviceWrapper(Device device) {
        super(device);
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
    public synchronized void setIn(int in) {
        super.setIn(in);
    }

    @Override
    public synchronized void setType(String type) {
        super.setType(type);
    }

    @Override
    public synchronized void setManufacturer(String manufacturer) {
        super.setManufacturer(manufacturer);
    }

    @Override
    public synchronized void setModel(String model) {
        super.setModel(model);
    }

    @Override
    public synchronized void setProductionDate(Date productionDate) {
        super.setProductionDate(productionDate);
    }

    @Override
    public synchronized void setChargeVolume(int chargeVolume) {
        super.setChargeVolume(chargeVolume);
    }

    @Override
    public synchronized void setDataRate(int dataRate) {
        super.setDataRate(dataRate);
    }

    @Override
    public synchronized void setNumberOfPorts(int numberOfPorts) {
        super.setNumberOfPorts(numberOfPorts);
    }

    @Override
    public synchronized void setPortConnection(Connection connection, int portNumber) {
        super.setPortConnection(connection, portNumber);
    }

    @Override
    public synchronized void setSecurityProtocol(String securityProtocol) {
        super.setSecurityProtocol(securityProtocol);
    }

    @Override
    public synchronized void setWirelessConnection(Connection wirelessConnection) {
        super.setWirelessConnection(wirelessConnection);
    }

    @Override
    public synchronized void setWireConnection(Connection wireConnection) {
        super.setWireConnection(wireConnection);
    }

}
