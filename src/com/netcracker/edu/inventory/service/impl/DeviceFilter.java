package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.device.Device;

import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

class DeviceFilter {

    private static final Logger log = Logger.getLogger(DeviceFilter.class.getName());

    public static void filtrateByType(Device[] devices, String type) {
        filtrateByString(devices, type, Device::getType);
    }

    public static void filtrateByManufacturer(Device[] devices, String manufacturer) {
        filtrateByString(devices, manufacturer, Device::getManufacturer);
    }

    public static void filtrateByModel(Device[] devices, String model) {
        filtrateByString(devices, model, Device::getModel);
    }

    private static void filtrateByString(Device[] devices, String string, Function<Device, String> function) {
        if (devices == null) {
            NullPointerException e = new NullPointerException("Device[] 'devices' is null");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        for (int i = 0; i < devices.length; i++) {
            if (devices[i] == null) {
                continue;
            }
            if (Objects.equals(function.apply(devices[i]), string)) {
                continue;
            }
            devices[i] = null;
        }
    }

}
