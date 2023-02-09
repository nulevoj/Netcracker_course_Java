package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Wireless<A extends Device, B extends Device>
        extends AbstractOneToManyConnection<A, B>
        implements com.netcracker.edu.inventory.model.connection.entity.Wireless<A, B> {

    private static final Logger log = Logger.getLogger(Wireless.class.getName());

    protected int version;
    protected String protocol;
    protected String technology;

    public Wireless() {
        APointConnectorType = ConnectorType.Wireless;
        BPointConnectorType = ConnectorType.Wireless;
    }

    public Wireless(int size, String technology) {
        this();
        BPoints = (B[]) new Device[size];
        this.technology = technology;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getTechnology() {
        return technology;
    }

    protected void setTechnology(String technology) {
        if (this.technology == null) {
            this.technology = technology;
            return;
        }
        log.warning("Can not change not default technology");
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setVersion((int) fields.remove().getValue());
            setProtocol((String) fields.remove().getValue());
            setTechnology((String) fields.remove().getValue());
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(Integer.class, getVersion()));
        fields.add(new Field(String.class, getProtocol()));
        fields.add(new Field(String.class, getTechnology()));
        return fields;
    }

}
