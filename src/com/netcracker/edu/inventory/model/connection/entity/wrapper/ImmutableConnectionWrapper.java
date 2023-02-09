package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.location.Trunk;

import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

public class ImmutableConnectionWrapper extends AbstractAllConnectionsWrapper {

    private static final Logger log = Logger.getLogger(ImmutableConnectionWrapper.class.getName());

    public ImmutableConnectionWrapper(Connection connection) {
        super(connection);
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        log.warning("Can not fill all fields for immutable connection");
    }

    @Override
    public void setTrunk(Trunk trunk) {
        log.warning("Can not set trunk for immutable connection");
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        log.warning("Can not set serialNumber for immutable connection");
    }

    @Override
    public void setStatus(String status) {
        log.warning("Can not set status for immutable connection");
    }

    @Override
    public void setAPoint(Device device) {
        log.warning("Can not set APoint for immutable connection");
    }

    @Override
    public void setBPoint(Device device) {
        log.warning("Can not set BPoint for immutable connection");
    }

    @Override
    public void setAPoints(List devices) {
        log.warning("Can not set APoints for immutable connection");
    }

    @Override
    public void setAPoint(Device device, int deviceNumber) {
        log.warning("Can not set APoint for immutable connection");
    }

    @Override
    public void setBPoints(List devices) {
        log.warning("Can not set BPoints for immutable connection");
    }

    @Override
    public void setBPoint(Device device, int deviceNumber) {
        log.warning("Can not set BPoint for immutable connection");
    }

    @Override
    public boolean addDevice(Device device) {
        log.warning("Can not add device to immutable connection");
        return false;
    }

    @Override
    public boolean removeDevice(Device device) {
        log.warning("Can not remove device from immutable connection");
        return false;
    }

    @Override
    public void setLength(int length) {
        log.warning("Can not set length for immutable connection");
    }

    @Override
    public void setProtocol(String protocol) {
        log.warning("Can not set protocol for immutable connection");
    }

    @Override
    public void setVersion(int version) {
        log.warning("Can not set version for immutable connection");
    }

}
