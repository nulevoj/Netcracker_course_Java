package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwistedPair<A extends Device, B extends Device>
        extends AbstractOneToOneConnection<A, B>
        implements com.netcracker.edu.inventory.model.connection.entity.TwistedPair<A, B> {

    private static final Logger log = Logger.getLogger(TwistedPair.class.getName());

    protected int length;
    protected Type type = Type.need_init;

    public TwistedPair() {
        APointConnectorType = ConnectorType.RJ45;
        BPointConnectorType = ConnectorType.RJ45;
    }

    public TwistedPair(Type type, int serialNumber) {
        this();
        this.type = type;
        this.serialNumber = serialNumber;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        if (this.type == Type.need_init) {
            this.type = type;
            return;
        }
        log.warning("Can not change not default type");
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setLength((int) fields.remove().getValue());
            setType((Type.valueOf((String) fields.remove().getValue())));
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(Integer.class, getLength()));
        fields.add(new Field(String.class, getType().toString()));
        return fields;
    }

}
