package com.netcracker.edu.inventory.model.connection.impl;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.location.Trunk;

import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DummyConnection implements Connection {

    private static final Logger log = Logger.getLogger(DummyConnection.class.getName());

    protected ConnectionPrimaryKey connectionPK;

    public DummyConnection(ConnectionPrimaryKey connectionPK) {
        this.connectionPK = connectionPK;
    }

    @Override
    public boolean isLazy() {
        return true;
    }

    @Override
    public PrimaryKey getPrimaryKey() {
        return connectionPK;
    }

    @Override
    public int hashCode() {
        return getConnectionPK().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DummyConnection) {
            return this.getConnectionPK().equals(((DummyConnection) obj).getPrimaryKey());
        }
        return getConnectionPK().equals(obj);
    }

    @Override
    public int compareTo(Object o) {
        return (this.getConnectionPK()).compareTo((((DummyConnection) o).getConnectionPK()));
    }

    protected ConnectionPrimaryKey getConnectionPK() {
        return (ConnectionPrimaryKey) getPrimaryKey();
    }

    protected void throwDummyException() throws UnsupportedOperationException {
        UnsupportedOperationException e = new UnsupportedOperationException("Connection is Dummy");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public Queue<Field> getAllFields() {
        throwDummyException();
        return null;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        throwDummyException();
    }

    @Override
    public Trunk getTrunk() {
        throwDummyException();
        return null;
    }

    @Override
    public void setTrunk(Trunk trunk) {
        throwDummyException();
    }

    @Override
    public int getSerialNumber() {
        throwDummyException();
        return 0;
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        throwDummyException();
    }

    @Override
    public String getStatus() {
        throwDummyException();
        return null;
    }

    @Override
    public void setStatus(String status) {
        throwDummyException();
    }

}
