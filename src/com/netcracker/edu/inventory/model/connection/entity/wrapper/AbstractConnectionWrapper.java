package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.location.Trunk;

import java.util.Queue;

public abstract class AbstractConnectionWrapper implements Connection {

    protected AbstractAllConnectionsWrapper wrapper;

    public AbstractConnectionWrapper(AbstractAllConnectionsWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public AbstractAllConnectionsWrapper getWrapper() {
        return wrapper;
    }

    @Override
    public boolean isLazy() {
        return wrapper.isLazy();
    }

    @Override
    public PrimaryKey getPrimaryKey() {
        return wrapper.getPrimaryKey();
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        wrapper.fillAllFields(fields);
    }

    @Override
    public Queue<Field> getAllFields() {
        return wrapper.getAllFields();
    }

    @Override
    public Trunk getTrunk() {
        return wrapper.getTrunk();
    }

    @Override
    public void setTrunk(Trunk trunk) {
        wrapper.setTrunk(trunk);
    }

    @Override
    public int getSerialNumber() {
        return wrapper.getSerialNumber();
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        wrapper.setSerialNumber(serialNumber);
    }

    @Override
    public String getStatus() {
        return wrapper.getStatus();
    }

    @Override
    public void setStatus(String status) {
        wrapper.setStatus(status);
    }

    @Override
    public int compareTo(Object o) {
        return wrapper.compareTo(o);
    }

}
