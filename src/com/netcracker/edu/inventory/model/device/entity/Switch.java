package com.netcracker.edu.inventory.model.device.entity;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;

import java.util.List;

/**
 * Created by makovetskyi on 15.11.17.
 */
public interface Switch extends Router {

    int getNumberOfPorts();

    void setNumberOfPorts(int numberOfPorts);

    ConnectorType getPortsType();

    Connection getPortConnection(int portNumber);

    void setPortConnection(Connection connection, int portNumber);

    List<Connection> getAllPortConnections();

}
