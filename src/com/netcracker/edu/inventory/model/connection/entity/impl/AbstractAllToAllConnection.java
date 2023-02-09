package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.AllToAllConnection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractAllToAllConnection<T extends Device>
        extends AbstractConnection<T, T>
        implements AllToAllConnection<T> {

    private static final Logger log = Logger.getLogger(AbstractAllToAllConnection.class.getName());

    protected int maxSize = 0;
    protected ConnectorType connectorType = ConnectorType.need_init;
    protected CopyOnWriteArraySet<T> devices = new CopyOnWriteArraySet<>();

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    protected void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public ConnectorType getConnectorType() {
        return connectorType;
    }

    protected void setConnectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
    }

    @Override
    public Set<T> getAllDevices() {
        return devices;
    }

    protected void setDevices(Set<T> devices) {
        this.devices = (CopyOnWriteArraySet<T>) devices;
    }

    @Override
    public boolean addDevice(T device) {
        return devices.add(device);
    }

    @Override
    public boolean removeDevice(T device) {
        return devices.remove(device);
    }

    @Override
    public boolean containDevice(T device) {
        return devices.contains(device);
    }

    @Override
    public int getCurSize() {
        return devices.size();
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setMaxSize((int) fields.remove().getValue());
            setConnectorType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setDevices(new CopyOnWriteArraySet<>(Arrays.asList((T[]) fields.remove().getValue())));
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(Integer.class, getMaxSize()));
        fields.add(new Field(String.class, getConnectorType().toString()));
        fields.add(new Field(Device[].class, getAllDevices().toArray(new Device[0])));
        return fields;
    }

}
