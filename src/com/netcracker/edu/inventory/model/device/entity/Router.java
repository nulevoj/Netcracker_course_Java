package com.netcracker.edu.inventory.model.device.entity;

import com.netcracker.edu.inventory.model.device.Device;

/**
 * Created by makovetskyi on 15.11.17.
 */
public interface Router extends Device {

    int getDataRate();

    void setDataRate(int dataRate);

}
