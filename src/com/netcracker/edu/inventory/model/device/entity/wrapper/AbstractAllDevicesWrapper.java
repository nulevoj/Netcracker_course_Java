package com.netcracker.edu.inventory.model.device.entity.wrapper;


import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;

import java.util.Date;
import java.util.List;
import java.util.Queue;

abstract class AbstractAllDevicesWrapper implements AllDevices {

    protected Device device;

    public AbstractAllDevicesWrapper(Device device) {
        this.device = device;
    }

    @Override
    public boolean isLazy() {
        return device.isLazy();
    }

    @Override
    public DevicePrimaryKey getPrimaryKey() {
        return device.getPrimaryKey();
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        device.fillAllFields(fields);
    }

    @Override
    public Queue<Field> getAllFields() {
        return device.getAllFields();
    }

    @Override
    public int getIn() {
        return device.getIn();
    }

    @Override
    public void setIn(int in) {
        device.setIn(in);
    }

    @Override
    public String getType() {
        return device.getType();
    }

    @Override
    public void setType(String type) {
        device.setType(type);
    }

    @Override
    public String getManufacturer() {
        return device.getManufacturer();
    }

    @Override
    public void setManufacturer(String manufacturer) {
        device.setManufacturer(manufacturer);
    }

    @Override
    public String getModel() {
        return device.getModel();
    }

    @Override
    public void setModel(String model) {
        device.setModel(model);
    }

    @Override
    public Date getProductionDate() {
        return device.getProductionDate();
    }

    @Override
    public void setProductionDate(Date productionDate) {
        device.setProductionDate(productionDate);
    }

    @Override
    public int compareTo(Device o) {
        return device.compareTo(o);
    }

    @Override
    public int getChargeVolume() {
        return ((Battery) device).getChargeVolume();
    }

    @Override
    public void setChargeVolume(int chargeVolume) {
        ((Battery) device).setChargeVolume(chargeVolume);
    }

    @Override
    public int getDataRate() {
        return ((Router) device).getDataRate();
    }

    @Override
    public void setDataRate(int dataRate) {
        ((Router) device).setDataRate(dataRate);
    }

    @Override
    public int getNumberOfPorts() {
        return ((Switch) device).getNumberOfPorts();
    }

    @Override
    public void setNumberOfPorts(int numberOfPorts) {
        ((Switch) device).setNumberOfPorts(numberOfPorts);
    }

    @Override
    public ConnectorType getPortsType() {
        return ((Switch) device).getPortsType();
    }

    @Override
    public Connection getPortConnection(int portNumber) {
        return ((Switch) device).getPortConnection(portNumber);
    }

    @Override
    public void setPortConnection(Connection connection, int portNumber) {
        ((Switch) device).setPortConnection(connection, portNumber);
    }

    @Override
    public List<Connection> getAllPortConnections() {
        return ((Switch) device).getAllPortConnections();
    }

    @Override
    public String getTechnologyVersion() {
        return ((WifiRouter) device).getTechnologyVersion();
    }

    @Override
    public String getSecurityProtocol() {
        return ((WifiRouter) device).getSecurityProtocol();
    }

    @Override
    public void setSecurityProtocol(String securityProtocol) {
        ((WifiRouter) device).setSecurityProtocol(securityProtocol);
    }

    @Override
    public Connection getWirelessConnection() {
        return ((WifiRouter) device).getWirelessConnection();
    }

    @Override
    public void setWirelessConnection(Connection wirelessConnection) {
        ((WifiRouter) device).setWirelessConnection(wirelessConnection);
    }

    @Override
    public ConnectorType getWirePortType() {
        return ((WifiRouter) device).getWirePortType();
    }

    @Override
    public Connection getWireConnection() {
        return ((WifiRouter) device).getWireConnection();
    }

    @Override
    public void setWireConnection(Connection wireConnection) {
        ((WifiRouter) device).setWireConnection(wireConnection);
    }

}
