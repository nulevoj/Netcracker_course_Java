package com.netcracker.edu.inventory.model.rack.impl;

import com.netcracker.edu.inventory.InventoryFactoryManager;
import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.rack.RackPrimaryKey;
import com.netcracker.edu.inventory.service.impl.CommonUtility;
import com.netcracker.edu.location.Location;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RackArrayImpl<D extends Device> implements Rack<D> {

    private static final Logger log = Logger.getLogger(RackArrayImpl.class.getName());

    protected final Class clazz;
    protected D[] devices;
    protected Location location;

    public RackArrayImpl(int size) {
        this(size, Device.class);
    }

    public RackArrayImpl(int size, Class clazz) {
        if (size <= 0) {
            IllegalArgumentException e = new IllegalArgumentException
                    ("size of rack can not be 0 or less");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        CommonUtility.checkClazzIsNull(clazz);
        if (!Device.class.isAssignableFrom(clazz)) {
            IllegalArgumentException e = new IllegalArgumentException
                    ("clazz can not be non-Device");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        this.clazz = clazz;
        devices = (D[]) new Device[size];
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int getSize() {
        return devices.length;
    }

    @Override
    public int getFreeSize() {
        int freeSize = 0;
        for (D device : devices) {
            if (device == null) {
                freeSize++;
            }
        }
        return freeSize;
    }

    @Override
    public Class getTypeOfDevices() {
        return clazz;
    }

    @Override
    public boolean insertDevToSlot(D device, int index) {
        CommonUtility.checkIndexOutOfRange(index, devices.length);
        if (!InventoryFactoryManager.getServiceFactory().createDeviceServiceImpl().isValidDeviceForInsertToRack(device)) {
            DeviceValidationException e =
                    new DeviceValidationException("Rack.insertDevToSlot", device);
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (!clazz.isAssignableFrom(device.getClass())) {
            IllegalArgumentException e = new IllegalArgumentException(
                    String.format("Type of the device (%s) is incompatible with this rack (%s).",
                            device.getClass().getName(), clazz.getName()));
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (devices[index] != null) {
            log.warning(String.format
                    ("Can not insert device to full slot (index: %d)", index));
            return false;
        }
        devices[index] = device;
        return true;
    }

    @Override
    public D getDevAtSlot(int index) {
        CommonUtility.checkIndexOutOfRange(index, devices.length);
        if (devices[index] == null) {
            log.warning(String.format("Can not remove from empty slot %d", index));
        }
        return devices[index];
    }

    @Override
    public D removeDevFromSlot(int index) {
        CommonUtility.checkIndexOutOfRange(index, devices.length);
        D device = getDevAtSlot(index);
        devices[index] = null;
        return device;
    }

    @Override
    public D getDevByIN(int in) {
        for (D device : devices) {
            if (device == null) {
                continue;
            }
            if (device.getIn() == in) {
                return device;
            }
        }
        return null;
    }

    @Override
    public D[] getAllDeviceAsArray() {
        return (D[]) Arrays.stream(devices)
                .filter(Objects::nonNull)
                .toArray(Device[]::new);
    }

    @Override
    public boolean isLazy() {
        return false;
    }

    @Override
    public RackPrimaryKey getPrimaryKey() {
        if (this.getLocation() == null) {
            log.warning("location is null");
            return null;
        }
        return new RackPK(this.getLocation());
    }

}
