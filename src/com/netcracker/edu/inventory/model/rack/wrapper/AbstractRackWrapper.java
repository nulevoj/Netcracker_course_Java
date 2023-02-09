package com.netcracker.edu.inventory.model.rack.wrapper;

import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.rack.RackPrimaryKey;
import com.netcracker.edu.location.Location;

abstract class AbstractRackWrapper<T extends Device> implements Rack<T> {

    protected Rack<T> rack;

    public AbstractRackWrapper(Rack<T> rack) {
        this.rack = rack;
    }

    @Override
    public boolean isLazy() {
        return rack.isLazy();
    }

    @Override
    public RackPrimaryKey getPrimaryKey() {
        return rack.getPrimaryKey();
    }

    @Override
    public Location getLocation() {
        return rack.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        rack.setLocation(location);
    }

    @Override
    public int getSize() {
        return rack.getSize();
    }

    @Override
    public int getFreeSize() {
        return rack.getFreeSize();
    }

    @Override
    public Class getTypeOfDevices() {
        return rack.getTypeOfDevices();
    }

    @Override
    public T getDevAtSlot(int index) {
        return rack.getDevAtSlot(index);
    }

    @Override
    public boolean insertDevToSlot(T device, int index) {
        return rack.insertDevToSlot(device, index);
    }

    @Override
    public T removeDevFromSlot(int index) {
        return rack.removeDevFromSlot(index);
    }

    @Override
    public T getDevByIN(int in) {
        return rack.getDevByIN(in);
    }

    @Override
    public T[] getAllDeviceAsArray() {
        return rack.getAllDeviceAsArray();
    }

}
