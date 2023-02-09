package com.netcracker.edu.inventory.model.device.entity.impl;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WifiRouter extends Router implements com.netcracker.edu.inventory.model.device.entity.WifiRouter {

    private static final Logger log = Logger.getLogger(WifiRouter.class.getName());

    protected String securityProtocol;
    protected String technologyVersion;
    protected Connection wirelessConnection;
    protected ConnectorType wirePortType = ConnectorType.need_init;
    protected Connection wireConnection;

    public WifiRouter() {

    }

    public WifiRouter(String technologyVersion, ConnectorType wirePortType) {
        this.technologyVersion = technologyVersion;
        this.wirePortType = wirePortType;
    }

    @Override
    public String getSecurityProtocol() {
        return securityProtocol;
    }

    @Override
    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    @Override
    public String getTechnologyVersion() {
        return technologyVersion;
    }

    protected void setTechnologyVersion(String technologyVersion) {
        if (this.technologyVersion == null) {
            this.technologyVersion = technologyVersion;
            return;
        }
        log.warning("Can not change not default technologyVersion");
    }

    @Override
    public Connection getWirelessConnection() {
        return wirelessConnection;
    }

    @Override
    public void setWirelessConnection(Connection wirelessConnection) {
        this.wirelessConnection = wirelessConnection;
    }

    @Override
    public ConnectorType getWirePortType() {
        return wirePortType;
    }

    protected void setWirePortType(ConnectorType wirePortType) {
        if (this.wirePortType == ConnectorType.need_init) {
            this.wirePortType = wirePortType;
            return;
        }
        log.warning("Can not change not default wirePortType");
    }

    @Override
    public Connection getWireConnection() {
        return wireConnection;
    }

    @Override
    public void setWireConnection(Connection wireConnection) {
        this.wireConnection = wireConnection;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setSecurityProtocol((String) fields.remove().getValue());
            setTechnologyVersion((String) fields.remove().getValue());
            setWirelessConnection((Connection) fields.remove().getValue());
            setWirePortType(ConnectorType.valueOf((String) fields.remove().getValue()));
            setWireConnection((Connection) fields.remove().getValue());
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(String.class, getSecurityProtocol()));
        fields.add(new Field(String.class, getTechnologyVersion()));
        fields.add(new Field(Connection.class, getWirelessConnection()));
        fields.add(new Field(String.class, getWirePortType().toString()));
        fields.add(new Field(Connection.class, getWireConnection()));
        return fields;
    }

}
