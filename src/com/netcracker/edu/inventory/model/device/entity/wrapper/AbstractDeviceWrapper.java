package com.netcracker.edu.inventory.model.device.entity.wrapper;

import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;

import java.util.Date;
import java.util.Queue;

public abstract class AbstractDeviceWrapper implements Device {

    protected AbstractAllDevicesWrapper wrapper;

    public AbstractDeviceWrapper(AbstractAllDevicesWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public AbstractAllDevicesWrapper getWrapper() {
        return wrapper;
    }

    @Override
    public boolean isLazy() {
        return wrapper.isLazy();
    }

    @Override
    public DevicePrimaryKey getPrimaryKey() {
        return wrapper.getPrimaryKey();
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        wrapper.fillAllFields(fields);
    }

    @Override
    public Queue<Field> getAllFields() {
        return wrapper.getAllFields();
    }

    @Override
    public int getIn() {
        return wrapper.getIn();
    }

    @Override
    public void setIn(int in) {
        wrapper.setIn(in);
    }

    @Override
    public String getType() {
        return wrapper.getType();
    }

    @Override
    public void setType(String type) {
        wrapper.setType(type);
    }

    @Override
    public String getManufacturer() {
        return wrapper.getManufacturer();
    }

    @Override
    public void setManufacturer(String manufacturer) {
        wrapper.setManufacturer(manufacturer);
    }

    @Override
    public String getModel() {
        return wrapper.getModel();
    }

    @Override
    public void setModel(String model) {
        wrapper.setModel(model);
    }

    @Override
    public Date getProductionDate() {
        return wrapper.getProductionDate();
    }

    @Override
    public void setProductionDate(Date productionDate) {
        wrapper.setProductionDate(productionDate);
    }

    @Override
    public int compareTo(Device o) {
        return wrapper.compareTo(o);
    }

}
