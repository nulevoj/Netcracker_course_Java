package com.netcracker.edu.inventory.model.device.entity;

import com.netcracker.edu.inventory.model.device.Device;

/**
 * Created by makovetskyi on 15.11.17.
 */
public interface Battery extends Device {

    int getChargeVolume();

    void setChargeVolume(int chargeVolume);

}
