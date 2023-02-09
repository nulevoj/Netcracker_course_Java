package com.netcracker.edu.inventory.model.connection;

import com.netcracker.edu.inventory.model.device.Device;

/**
 * Created by makovetskyi on 10.11.16.
 */
public interface OneToManyConnection<A extends Device, B extends Device> extends Connection<A, B>, Connection.OneAPoint<A, B>, Connection.MultipleBPoint<A, B> {
}
