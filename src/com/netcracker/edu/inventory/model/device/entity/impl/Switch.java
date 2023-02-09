package com.netcracker.edu.inventory.model.device.entity.impl;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.service.impl.CommonUtility;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Switch extends Router implements com.netcracker.edu.inventory.model.device.entity.Switch {

    private static final Logger log = Logger.getLogger(Switch.class.getName());

    protected int numberOfPorts;
    protected ConnectorType portsType = ConnectorType.need_init;
    protected Connection[] portsConnections = new Connection[0];

    public Switch() {

    }

    public Switch(ConnectorType portsType) {
        this.portsType = portsType;
    }

    @Override
    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    @Override
    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
        portsConnections = new Connection[this.numberOfPorts];
    }

    @Override
    public ConnectorType getPortsType() {
        return portsType;
    }

    protected void setPortsType(ConnectorType portsType) {
        if (this.portsType == ConnectorType.need_init) {
            this.portsType = ConnectorType.valueOf(portsType.toString());
            return;
        }
        log.warning("Can not change not default portsType");
    }

    @Override
    public Connection getPortConnection(int portNumber) throws IndexOutOfBoundsException {
        CommonUtility.checkIndexOutOfRange(portNumber, portsConnections.length);
        return portsConnections[portNumber];
    }

    @Override
    public void setPortConnection(Connection connection, int portNumber) throws IndexOutOfBoundsException {
        CommonUtility.checkIndexOutOfRange(portNumber, portsConnections.length);
        portsConnections[portNumber] = connection;
    }

    @Override
    public List<Connection> getAllPortConnections() {
        return Arrays.asList(portsConnections);
    }

    protected void setPortConnections(Connection[] portsConnections) {
        this.portsConnections = portsConnections;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setNumberOfPorts((int) fields.remove().getValue());
            setPortsType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setPortConnections((Connection[]) fields.remove().getValue());
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(Integer.class, getNumberOfPorts()));
        fields.add(new Field(String.class, getPortsType().toString()));
        fields.add(new Field(Connection[].class, portsConnections));
        return fields;
    }

}
