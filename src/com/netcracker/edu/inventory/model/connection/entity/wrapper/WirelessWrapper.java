package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.entity.Wireless;

public class WirelessWrapper extends AbstractOneToManyWrapper implements Wireless {

    public WirelessWrapper(AbstractAllConnectionsWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public String getTechnology() {
        return wrapper.getTechnology();
    }

    @Override
    public String getProtocol() {
        return wrapper.getProtocol();
    }

    @Override
    public void setProtocol(String protocol) {
        wrapper.setProtocol(protocol);
    }

    @Override
    public int getVersion() {
        return wrapper.getVersion();
    }

    @Override
    public void setVersion(int version) {
        wrapper.setVersion(version);
    }

}
