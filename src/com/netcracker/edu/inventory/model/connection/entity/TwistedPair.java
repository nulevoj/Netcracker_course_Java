package com.netcracker.edu.inventory.model.connection.entity;

import com.netcracker.edu.inventory.model.connection.OneToOneConnection;
import com.netcracker.edu.inventory.model.device.Device;

/**
 * Created by makovetskyi on 15.11.17.
 */
public interface TwistedPair<A extends Device, B extends Device> extends OneToOneConnection<A, B> {

    Type getType();

    int getLength();

    void setLength(int length);

    enum Type {
        need_init, UTP, FTP, STP, SslashFTP, SFTP;
    }

}
