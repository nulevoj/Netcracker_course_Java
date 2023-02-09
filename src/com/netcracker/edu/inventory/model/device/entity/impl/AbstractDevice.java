package com.netcracker.edu.inventory.model.device.entity.impl;

import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.impl.DevicePK;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractDevice implements Device {

    private static final Logger log = Logger.getLogger(AbstractDevice.class.getName());

    protected int in;
    protected String type;
    protected String manufacturer;
    protected String model;
    protected Date productionDate;

    @Override
    public int getIn() {
        return in;
    }

    @Override
    public void setIn(int in) {
        if (in < 0) {
            IllegalArgumentException e = new IllegalArgumentException("IN can not be negative");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (this.in != 0) {
            log.warning("Inventory number can not be reset");
            return;
        }
        this.in = in;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public Date getProductionDate() {
        return productionDate;
    }

    @Override
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        try {
            setIn((int) fields.remove().getValue());
            setType((String) fields.remove().getValue());
            setManufacturer((String) fields.remove().getValue());
            setModel((String) fields.remove().getValue());
            setProductionDate((Date) fields.remove().getValue());
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = new ArrayDeque<>();
        fields.add(new Field(Integer.class, getIn()));
        fields.add(new Field(String.class, getType()));
        fields.add(new Field(String.class, getManufacturer()));
        fields.add(new Field(String.class, getModel()));
        fields.add(new Field(Date.class, getProductionDate()));
        return fields;
    }

    @Override
    public int compareTo(Device o) {
        return Integer.compare(this.getIn(), o.getIn());
    }

    @Override
    public boolean isLazy() {
        return false;
    }

    @Override
    public DevicePrimaryKey getPrimaryKey() {
        if (this.getIn() == 0) {
            log.warning("in equals 0");
            return null;
        }
        return new DevicePK(this.getIn());
    }

}
