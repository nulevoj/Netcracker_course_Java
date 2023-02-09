package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.device.Device;

import java.util.Comparator;

class DeviceByProductionDateComparator implements Comparator<Device> {

    @Override
    public int compare(Device d1, Device d2) {
        if (d1 == null && d2 == null) {
            return 0;
        }
        if (d1 == null) {
            return 1;
        }
        if (d2 == null) {
            return -1;
        }

        if (d1.getProductionDate() == null && d2.getProductionDate() == null) {
            return 0;
        }
        if (d1.getProductionDate() == null) {
            return 1;
        }
        if (d2.getProductionDate() == null) {
            return -1;
        }

        return (d1.getProductionDate().compareTo(d2.getProductionDate()));
    }

}
