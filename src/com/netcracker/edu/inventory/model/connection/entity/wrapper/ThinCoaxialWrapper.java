package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;

public class ThinCoaxialWrapper extends AbstractAllToAllWrapper implements ThinCoaxial {

    public ThinCoaxialWrapper(AbstractAllConnectionsWrapper wrapper) {
        super(wrapper);
    }

}
