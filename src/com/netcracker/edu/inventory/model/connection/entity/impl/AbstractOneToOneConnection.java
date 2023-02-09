package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.connection.OneToOneConnection;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractOneToOneConnection<A extends Device, B extends Device>
        extends AbstractConnection<A, B>
        implements OneToOneConnection<A, B> {

    private static final Logger log = Logger.getLogger(AbstractOneToOneConnection.class.getName());

    protected ConnectorType APointConnectorType = ConnectorType.need_init;
    protected ConnectorType BPointConnectorType = ConnectorType.need_init;
    protected A APoint;
    protected B BPoint;

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
    public B getBPoint() {
        return BPoint;
    }

    @Override
    public void setBPoint(B device) {
        this.BPoint = device;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setAPointConnectorType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setBPointConnectorType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setAPoint((A) fields.remove().getValue());
            setBPoint((B) fields.remove().getValue());
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
        fields.add(new Field(Device.class, getBPoint()));
        return fields;
    }

}
