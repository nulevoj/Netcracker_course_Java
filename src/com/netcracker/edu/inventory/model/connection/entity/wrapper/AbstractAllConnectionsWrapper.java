package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.*;
import com.netcracker.edu.inventory.model.connection.entity.OpticFiber;
import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.connection.entity.Wireless;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.location.Trunk;

import java.util.List;
import java.util.Queue;
import java.util.Set;

abstract class AbstractAllConnectionsWrapper implements AllConnections {

    protected Connection connection;

    public AbstractAllConnectionsWrapper(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isLazy() {
        return connection.isLazy();
    }

    @Override
    public PrimaryKey getPrimaryKey() {
        return connection.getPrimaryKey();
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        connection.fillAllFields(fields);
    }

    @Override
    public Queue<Field> getAllFields() {
        return connection.getAllFields();
    }

    @Override
    public Trunk getTrunk() {
        return connection.getTrunk();
    }

    @Override
    public void setTrunk(Trunk trunk) {
        connection.setTrunk(trunk);
    }

    @Override
    public int getSerialNumber() {
        return connection.getSerialNumber();
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        connection.setSerialNumber(serialNumber);
    }

    @Override
    public String getStatus() {
        return connection.getStatus();
    }

    @Override
    public void setStatus(String status) {
        connection.setStatus(status);
    }

    @Override
    public int compareTo(Object o) {
        return connection.compareTo(o);
    }

    @Override
    public ConnectorType getAPointConnectorType() {
        return ((HaveAPoint) connection).getAPointConnectorType();
    }

    @Override
    public ConnectorType getBPointConnectorType() {
        return ((HaveBPoint) connection).getBPointConnectorType();
    }

    @Override
    public Device getAPoint() {
        return ((OneAPoint) connection).getAPoint();
    }

    @Override
    public void setAPoint(Device device) {
        ((OneAPoint) connection).setAPoint(device);
    }

    @Override
    public Device getBPoint() {
        return ((OneBPoint) connection).getBPoint();
    }

    @Override
    public void setBPoint(Device device) {
        ((OneBPoint) connection).setBPoint(device);
    }

    @Override
    public List getAPoints() {
        return ((MultipleAPoint) connection).getAPoints();
    }

    @Override
    public void setAPoints(List devices) {
        ((MultipleAPoint) connection).setAPoints(devices);
    }

    @Override
    public int getACapacity() {
        return ((MultipleAPoint) connection).getACapacity();
    }

    @Override
    public Device getAPoint(int deviceNumber) {
        return ((MultipleAPoint) connection).getAPoint(deviceNumber);
    }

    @Override
    public void setAPoint(Device device, int deviceNumber) {
        ((MultipleAPoint) connection).setAPoint(device, deviceNumber);
    }

    @Override
    public List getBPoints() {
        return ((MultipleBPoint) connection).getBPoints();
    }

    @Override
    public void setBPoints(List devices) {
        ((MultipleBPoint) connection).setBPoints(devices);
    }

    @Override
    public int getBCapacity() {
        return ((MultipleBPoint) connection).getBCapacity();
    }

    @Override
    public Device getBPoint(int deviceNumber) {
        return ((MultipleBPoint) connection).getBPoint(deviceNumber);
    }

    @Override
    public void setBPoint(Device device, int deviceNumber) {
        ((MultipleBPoint) connection).setBPoint(device, deviceNumber);
    }

    @Override
    public ConnectorType getConnectorType() {
        return ((ThinCoaxial) connection).getConnectorType();
    }

    @Override
    public boolean addDevice(Device device) {
        return ((AllToAllConnection) connection).addDevice(device);
    }

    @Override
    public boolean removeDevice(Device device) {
        return ((AllToAllConnection) connection).removeDevice(device);
    }

    @Override
    public boolean containDevice(Device device) {
        return ((AllToAllConnection) connection).containDevice(device);
    }

    @Override
    public Set getAllDevices() {
        return ((AllToAllConnection) connection).getAllDevices();
    }

    @Override
    public int getCurSize() {
        return ((AllToAllConnection) connection).getCurSize();
    }

    @Override
    public int getMaxSize() {
        return ((AllToAllConnection) connection).getMaxSize();
    }

    @Override
    public Mode getMode() {
        return ((OpticFiber) connection).getMode();
    }

    @Override
    public Type getType() {
        return ((TwistedPair) connection).getType();
    }

    @Override
    public int getLength() {
        if (connection instanceof OpticFiber) {
            return ((OpticFiber) connection).getLength();
        }
        if (connection instanceof TwistedPair) {
            return ((TwistedPair) connection).getLength();
        }
        return 0;
    }

    @Override
    public void setLength(int length) {
        if (connection instanceof OpticFiber) {
            ((OpticFiber) connection).setLength(length);
        }
        if (connection instanceof TwistedPair) {
            ((TwistedPair) connection).setLength(length);
        }
    }

    @Override
    public String getTechnology() {
        return ((Wireless) connection).getTechnology();
    }

    @Override
    public String getProtocol() {
        return ((Wireless) connection).getProtocol();
    }

    @Override
    public void setProtocol(String protocol) {
        ((Wireless) connection).setProtocol(protocol);
    }

    @Override
    public int getVersion() {
        return ((Wireless) connection).getVersion();
    }

    @Override
    public void setVersion(int version) {
        ((Wireless) connection).setVersion(version);
    }

}
