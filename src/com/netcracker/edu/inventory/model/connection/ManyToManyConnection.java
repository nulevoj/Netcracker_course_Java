package com.netcracker.edu.inventory.model.connection;

import com.netcracker.edu.inventory.model.device.Device;

/**
 * Created by makovetskyi on 10.11.16.
 */
public interface ManyToManyConnection<A extends Device, B extends Device> extends Connection<A, B>, Connection.MultipleAPoint<A, B>, Connection.MultipleBPoint<A, B> {
}
