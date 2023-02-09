package com.netcracker.edu.inventory.model.connection.entity;

import com.netcracker.edu.inventory.model.connection.AllToAllConnection;
import com.netcracker.edu.inventory.model.device.Device;

/**
 * Created by makovetskyi on 15.11.17.
 */
public interface ThinCoaxial<T extends Device> extends AllToAllConnection<T> {
}
