package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.connection.OneToManyConnection;
import com.netcracker.edu.inventory.service.impl.CommonUtility;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractOneToManyConnection<A extends Device, B extends Device>
        extends AbstractConnection<A, B>
        implements OneToManyConnection<A, B> {

    private static final Logger log = Logger.getLogger(AbstractOneToManyConnection.class.getName());

    protected ConnectorType APointConnectorType = ConnectorType.need_init;
    protected ConnectorType BPointConnectorType = ConnectorType.need_init;
    protected A APoint;
    protected B[] BPoints = (B[]) new Device[0];

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
    public A getAPoint() {
        return APoint;
    }

    @Override
    public void setAPoint(A device) {
        this.APoint = device;
    }

    @Override
    public List<B> getBPoints() {
        return Arrays.asList(BPoints);
    }

    @Override
    public void setBPoints(List<B> devices) {
        BPoints = devices.toArray(BPoints);
    }

    @Override
    public int getBCapacity() {
        return BPoints.length;
    }

    @Override
    public B getBPoint(int deviceNumber) {
        CommonUtility.checkIndexOutOfRange(deviceNumber, BPoints.length);
        return BPoints[deviceNumber];
    }

    @Override
    public void setBPoint(B device, int deviceNumber) {
        CommonUtility.checkIndexOutOfRange(deviceNumber, BPoints.length);
        BPoints[deviceNumber] = device;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setAPointConnectorType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setBPointConnectorType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setAPoint((A) fields.remove().getValue());
            setBPoints(Arrays.asList((B[]) fields.remove().getValue()));
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
        fields.add(new Field(Device.class, getAPoint()));
        fields.add(new Field(Device[].class, getBPoints().toArray()));
        return fields;
    }

}
