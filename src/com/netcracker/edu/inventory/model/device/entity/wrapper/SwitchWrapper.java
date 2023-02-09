package com.netcracker.edu.inventory.model.device.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.entity.Switch;

import java.util.List;

public class SwitchWrapper extends RouterWrapper implements Switch {

    public SwitchWrapper(AbstractAllDevicesWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public int getNumberOfPorts() {
        return wrapper.getNumberOfPorts();
    }

    @Override
    public void setNumberOfPorts(int numberOfPorts) {
        wrapper.setNumberOfPorts(numberOfPorts);
    }

    @Override
    public ConnectorType getPortsType() {
        return wrapper.getPortsType();
    }

    @Override
    public Connection getPortConnection(int portNumber) {
        return wrapper.getPortConnection(portNumber);
    }

    @Override
    public void setPortConnection(Connection connection, int portNumber) {
        wrapper.setPortConnection(connection, portNumber);
    }

    @Override
    public List<Connection> getAllPortConnections() {
        return wrapper.getAllPortConnections();
    }

}
