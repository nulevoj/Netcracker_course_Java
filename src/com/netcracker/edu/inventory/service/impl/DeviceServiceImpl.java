package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.io.impl.IOServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

class DeviceServiceImpl implements DeviceService {

    private static final Logger log = Logger.getLogger(DeviceServiceImpl.class.getName());
    private static final IOServiceImpl ioService = new IOServiceImpl();

    @Override
    public void sortByIN(Device[] devices) {
        Arrays.sort(devices, new DeviceByInComparator());
    }

    @Override
    public void sortByProductionDate(Device[] devices) {
        Arrays.sort(devices, new DeviceByProductionDateComparator());
    }

    @Override
    public void filtrateByType(Device[] devices, String type) {
        DeviceFilter.filtrateByType(devices, type);
    }

    @Override
    public void filtrateByManufacturer(Device[] devices, String manufacturer) {
        DeviceFilter.filtrateByManufacturer(devices, manufacturer);
    }

    @Override
    public void filtrateByModel(Device[] devices, String model) {
        DeviceFilter.filtrateByModel(devices, model);
    }

    @Override
    public boolean isValidDeviceForInsertToRack(Device device) {
        if (device == null || device.getIn() <= 0 || device.isLazy()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidDeviceForOutputToStream(Device device) {
        return ioService.isValidEntityForOutputToStream(device);
    }

    @Override
    public void outputDevice(Device device, OutputStream outputStream) throws IOException {
        if (!isValidDeviceForOutputToStream(device)) {
            DeviceValidationException e = new DeviceValidationException("DeviceService.outputDevice", device);
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        ioService.outputFillableEntity(device, outputStream);
    }

    @Override
    public Device inputDevice(InputStream inputStream) throws IOException, ClassNotFoundException {
        return (Device) ioService.inputFillableEntity(inputStream);
    }

    @Override
    public Device getDeviceFromDB(Connection dbConnection, DevicePrimaryKey dpk) throws SQLException {
        return DeviceDatabaseFacade.getDeviceFromDB(dbConnection, dpk);
    }

    @Override
    public boolean putDeviceToDB(Connection dbConnection, Device device) throws SQLException {
        return DeviceDatabaseFacade.putDeviceToDB(dbConnection, device);
    }

    @Override
    public boolean removeDeviceFromDB(Connection dbConnection, DevicePrimaryKey dpk) throws SQLException {
        return DeviceDatabaseFacade.removeDeviceFromDB(dbConnection, dpk);
    }

}
