package com.netcracker.edu.inventory.model.device.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;

public class WifiRouterWrapper extends RouterWrapper implements WifiRouter {

    public WifiRouterWrapper(AbstractAllDevicesWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public String getTechnologyVersion() {
        return wrapper.getTechnologyVersion();
    }

    @Override
    public String getSecurityProtocol() {
        return wrapper.getSecurityProtocol();
    }

    @Override
    public void setSecurityProtocol(String securityProtocol) {
        wrapper.setSecurityProtocol(securityProtocol);
    }

    @Override
    public Connection getWirelessConnection() {
        return wrapper.getWirelessConnection();
    }

    @Override
    public void setWirelessConnection(Connection wirelessConnection) {
        wrapper.setWirelessConnection(wirelessConnection);
    }

    @Override
    public ConnectorType getWirePortType() {
        return wrapper.getWirePortType();
    }

    @Override
    public Connection getWireConnection() {
        return wrapper.getWireConnection();
    }

    @Override
    public void setWireConnection(Connection wireConnection) {
        wrapper.setWireConnection(wireConnection);
    }

}
