package com.netcracker.edu.inventory.model.connection.entity;

import com.netcracker.edu.inventory.model.connection.OneToManyConnection;
import com.netcracker.edu.inventory.model.device.Device;

/**
 * Created by makovetskyi on 15.11.17.
 */
public interface Wireless<A extends Device, B extends Device> extends OneToManyConnection<A, B> {

    String getTechnology();

    String getProtocol();

    void setProtocol(String protocol);

    int getVersion();

    void setVersion(int version);

}
