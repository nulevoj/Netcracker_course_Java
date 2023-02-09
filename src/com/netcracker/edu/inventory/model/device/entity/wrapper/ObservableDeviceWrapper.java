package com.netcracker.edu.inventory.model.device.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.device.Device;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ObservableDeviceWrapper extends AbstractAllDevicesWrapper {

    protected Set<PropertyChangeListener> listeners = new HashSet<>();

    public ObservableDeviceWrapper(Device device) {
        super(device);
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
    public void setIn(int in) {
        notifyListeners(this, "in", getIn(), in);
        super.setIn(in);
    }

    @Override
    public void setType(String type) {
        notifyListeners(this, "type", getType(), type);
        super.setType(type);
    }

    @Override
    public void setManufacturer(String manufacturer) {
        notifyListeners(this, "manufacturer", getManufacturer(), manufacturer);
        super.setManufacturer(manufacturer);
    }

    @Override
    public void setModel(String model) {
        notifyListeners(this, "model", getModel(), model);
        super.setModel(model);
    }

    @Override
    public void setProductionDate(Date productionDate) {
        notifyListeners(this, "productionDate", getProductionDate(), productionDate);
        super.setProductionDate(productionDate);
    }

    @Override
    public void setChargeVolume(int chargeVolume) {
        notifyListeners(this, "chargeVolume", getChargeVolume(), chargeVolume);
        super.setChargeVolume(chargeVolume);
    }

    @Override
    public void setDataRate(int dataRate) {
        notifyListeners(this, "dataRate", getDataRate(), dataRate);
        super.setDataRate(dataRate);
    }

    @Override
    public void setNumberOfPorts(int numberOfPorts) {
        notifyListeners(this, "numberOfPorts", getNumberOfPorts(), numberOfPorts);
        super.setNumberOfPorts(numberOfPorts);
    }

    @Override
    public void setPortConnection(Connection connection, int portNumber) {
        notifyListeners(this, "portConnection", getPortConnection(portNumber), connection);
        super.setPortConnection(connection, portNumber);
    }

    @Override
    public void setSecurityProtocol(String securityProtocol) {
        notifyListeners(this, "securityProtocol", getSecurityProtocol(), securityProtocol);
        super.setSecurityProtocol(securityProtocol);
    }

    @Override
    public void setWirelessConnection(Connection wirelessConnection) {
        notifyListeners(this, "wirelessConnection", getWirelessConnection(), wirelessConnection);
        super.setWirelessConnection(wirelessConnection);
    }

    @Override
    public void setWireConnection(Connection wireConnection) {
        notifyListeners(this, "wireConnection", getWireConnection(), wireConnection);
        super.setWireConnection(wireConnection);
    }

}
