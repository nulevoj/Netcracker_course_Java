package com.netcracker.edu.inventory.model.device.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.Date;
import java.util.Queue;
import java.util.logging.Logger;

public class ImmutableDeviceWrapper extends AbstractAllDevicesWrapper {

    private static final Logger log = Logger.getLogger(ImmutableDeviceWrapper.class.getName());

    public ImmutableDeviceWrapper(Device device) {
        super(device);
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        log.warning("Can not fill all fields for immutable device");
    }

    @Override
    public void setIn(int in) {
        log.warning("Can not set IN for immutable device");
    }

    @Override
    public void setType(String type) {
        log.warning("Can not set type for immutable device");
    }

    @Override
    public void setManufacturer(String manufacturer) {
        log.warning("Can not set manufacturer for immutable device");
    }

    @Override
    public void setModel(String model) {
        log.warning("Can not set model for immutable device");
    }

    @Override
    public void setProductionDate(Date productionDate) {
        log.warning("Can not set productionDate for immutable device");
    }

    @Override
    public void setChargeVolume(int chargeVolume) {
        log.warning("Can not set chargeVolume for immutable device");
    }

    @Override
    public void setDataRate(int dataRate) {
        log.warning("Can not set dataRate for immutable device");
    }

    @Override
    public void setNumberOfPorts(int numberOfPorts) {
        log.warning("Can not set numberOfPorts for immutable device");
    }

    @Override
    public void setPortConnection(Connection connection, int portNumber) {
        log.warning("Can not set connection for immutable device");
    }

    @Override
    public void setSecurityProtocol(String securityProtocol) {
        log.warning("Can not set securityProtocol for immutable device");
    }

    @Override
    public void setWirelessConnection(Connection wirelessConnection) {
        log.warning("Can not set wirelessConnection for immutable device");
    }

    @Override
    public void setWireConnection(Connection wireConnection) {
        log.warning("Can not set wireConnection for immutable device");
    }

}
