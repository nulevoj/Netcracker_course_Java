package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.connection.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.location.Trunk;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractConnection<A extends Device, B extends Device>
        implements Connection<A, B> {

    private static final Logger log = Logger.getLogger(AbstractConnection.class.getName());

    protected int serialNumber;
    protected String status = "Planed";
    protected Trunk trunk;

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        if (serialNumber < 0) {
            IllegalArgumentException e = new IllegalArgumentException("serialNumber can not be negative");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (this.serialNumber != 0) {
            log.warning("Serial number can not be reset");
            return;
        }
        this.serialNumber = serialNumber;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Trunk getTrunk() {
        return trunk;
    }

    @Override
    public void setTrunk(Trunk trunk) {
        this.trunk = trunk;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        try {
            setSerialNumber((int) fields.remove().getValue());
            setStatus((String) fields.remove().getValue());
            setTrunk((Trunk) fields.remove().getValue());
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = new ArrayDeque<>();
        fields.add(new Field(Integer.class, getSerialNumber()));
        fields.add(new Field(String.class, getStatus()));
        fields.add(new Field(Trunk.class, getTrunk()));
        return fields;
    }

    @Override
    public int compareTo(Connection o) {
        return Integer.compare(this.getSerialNumber(), o.getSerialNumber());
    }

    @Override
    public boolean isLazy() {
        return false;
    }

    @Override
    public ConnectionPrimaryKey getPrimaryKey() {
        if (this.getSerialNumber() == 0) {
            log.warning("serialNumber equals 0");
            return null;
        }
        if (this.getTrunk() == null) {
            log.warning("trunk is null");
            return null;
        }
        return new ConnectionPK(this.getTrunk(), this.getSerialNumber());
    }

}
