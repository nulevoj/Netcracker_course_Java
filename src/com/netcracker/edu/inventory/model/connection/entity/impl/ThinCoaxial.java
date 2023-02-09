package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.logging.Logger;

public class ThinCoaxial<T extends Device>
        extends AbstractAllToAllConnection<T>
        implements com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial<T> {

    private static final Logger log = Logger.getLogger(ThinCoaxial.class.getName());

    public ThinCoaxial() {
        connectorType = ConnectorType.TConnector;
    }

    public ThinCoaxial(int size) {
        this();
        maxSize = size;
    }

}
