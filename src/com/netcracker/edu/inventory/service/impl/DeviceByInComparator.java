package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.device.Device;

import java.util.Comparator;

class DeviceByInComparator implements Comparator<Device> {

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

        if (d1.getIn() == 0 && d2.getIn() != 0) {
            return 1;
        }
        if (d1.getIn() != 0 && d2.getIn() == 0) {
            return -1;
        }

        if (d1.getIn() < d2.getIn()) {
            return -1;
        }
        return 1;
    }

}
