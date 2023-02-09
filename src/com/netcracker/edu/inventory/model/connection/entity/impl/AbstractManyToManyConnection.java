package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.ManyToManyConnection;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractManyToManyConnection<A extends Device, B extends Device>
        extends AbstractConnection<A, B>
        implements ManyToManyConnection<A, B> {

    private static final Logger log = Logger.getLogger(AbstractManyToManyConnection.class.getName());

    protected ConnectorType APointConnectorType = ConnectorType.need_init;
    protected ConnectorType BPointConnectorType = ConnectorType.need_init;
    protected CopyOnWriteArrayList<A> APoints = new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<B> BPoints = new CopyOnWriteArrayList<>();

    @Override
    public ConnectorType getAPointConnectorType() {
        return APointConnectorType;
    }

    protected void setAPointConnectorType(ConnectorType APointConnectorType) {
        this.APointConnectorType = APointConnectorType;
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return BPointConnectorType;
    }

    protected void setBPointConnectorType(ConnectorType BPointConnectorType) {
        this.BPointConnectorType = BPointConnectorType;
    }

    @Override
    public List<A> getAPoints() {
        return APoints;
    }

    @Override
    public void setAPoints(List<A> devices) {
        this.APoints = (CopyOnWriteArrayList<A>) devices;
    }

    @Override
    public int getACapacity() {
        return APoints.size();
    }

    @Override
    public A getAPoint(int deviceNumber) {
        return APoints.get(deviceNumber);
    }

    @Override
    public void setAPoint(A device, int deviceNumber) {
        APoints.set(deviceNumber, device);
    }

    @Override
    public List<B> getBPoints() {
        return BPoints;
    }

    @Override
    public void setBPoints(List<B> devices) {
        this.BPoints = (CopyOnWriteArrayList<B>) devices;
    }

    @Override
    public int getBCapacity() {
        return BPoints.size();
    }

    @Override
    public B getBPoint(int deviceNumber) {
        return BPoints.get(deviceNumber);
    }

    @Override
    public void setBPoint(B device, int deviceNumber) {
        BPoints.set(deviceNumber, device);
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setAPointConnectorType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setBPointConnectorType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setAPoints(new CopyOnWriteArrayList<>((A[]) fields.remove().getValue()));
            setBPoints(new CopyOnWriteArrayList<>((B[]) fields.remove().getValue()));
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(String.class, getAPointConnectorType().toString()));
        fields.add(new Field(String.class, getBPointConnectorType().toString()));
        fields.add(new Field(Device[].class, getAPoints().toArray()));
        fields.add(new Field(Device[].class, getBPoints().toArray()));
        return fields;
    }

}
