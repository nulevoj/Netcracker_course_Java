package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.OneToOneConnection;
import com.netcracker.edu.inventory.model.device.Device;

abstract class AbstractOneToOneWrapper extends AbstractConnectionWrapper implements OneToOneConnection {

    public AbstractOneToOneWrapper(AbstractAllConnectionsWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return wrapper.getAPointConnectorType();
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return wrapper.getBPointConnectorType();
    }

    @Override
    public Device getAPoint() {
        return wrapper.getAPoint();
    }

    @Override
    public void setAPoint(Device device) {
        wrapper.setAPoint(device);
    }

    @Override
    public Device getBPoint() {
        return wrapper.getBPoint();
    }

    @Override
    public void setBPoint(Device device) {
        wrapper.setBPoint(device);
    }

}
