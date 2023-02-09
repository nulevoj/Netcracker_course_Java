package com.netcracker.edu.inventory.model.device.impl;

import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;

import java.util.Date;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DummyDevice implements Device {

    private static final Logger log = Logger.getLogger(DummyDevice.class.getName());

    protected DevicePrimaryKey devicePK;

    public DummyDevice(DevicePrimaryKey devicePK) {
        this.devicePK = devicePK;
    }

    @Override
    public boolean isLazy() {
        return true;
    }

    @Override
    public DevicePrimaryKey getPrimaryKey() {
        return devicePK;
    }

    @Override
    public int hashCode() {
        return getPrimaryKey().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DummyDevice) {
            return this.getPrimaryKey().equals(((DummyDevice) obj).getPrimaryKey());
        }
        return false;
    }

    @Override
    public int compareTo(Device o) {
        return (this.getPrimaryKey()).compareTo((o.getPrimaryKey()));
    }

    protected void throwDummyException() throws UnsupportedOperationException {
        UnsupportedOperationException e = new UnsupportedOperationException("Device is Dummy");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        throwDummyException();
    }

    @Override
    public Queue<Field> getAllFields() {
        throwDummyException();
        return null;
    }

    @Override
    public int getIn() {
        throwDummyException();
        return 0;
    }

    @Override
    public void setIn(int in) {
        throwDummyException();
    }

    @Override
    public String getType() {
        throwDummyException();
        return null;
    }

    @Override
    public void setType(String type) {
        throwDummyException();
    }

    @Override
    public String getManufacturer() {
        throwDummyException();
        return null;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        throwDummyException();
    }

    @Override
    public String getModel() {
        throwDummyException();
        return null;
    }

    @Override
    public void setModel(String model) {
        throwDummyException();
    }

    @Override
    public Date getProductionDate() {
        throwDummyException();
        return null;
    }

    @Override
    public void setProductionDate(Date productionDate) {
        throwDummyException();
    }

}
