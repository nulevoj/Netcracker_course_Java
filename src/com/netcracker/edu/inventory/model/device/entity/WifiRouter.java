package com.netcracker.edu.inventory.model.device.entity;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;

/**
 * Created by makovetskyi on 15.11.17.
 */
public interface WifiRouter extends Router {

    String getTechnologyVersion();

    String getSecurityProtocol();

    void setSecurityProtocol(String securityProtocol);

    Connection getWirelessConnection();

    void setWirelessConnection(Connection wirelessConnection);

    ConnectorType getWirePortType();

    Connection getWireConnection();

    void setWireConnection(Connection wireConnection);

}
