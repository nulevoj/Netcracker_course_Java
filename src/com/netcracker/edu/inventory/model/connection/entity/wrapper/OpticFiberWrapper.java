package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.entity.OpticFiber;

public class OpticFiberWrapper extends AbstractOneToOneWrapper implements OpticFiber {

    public OpticFiberWrapper(AbstractAllConnectionsWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public Mode getMode() {
        return wrapper.getMode();
    }

    @Override
    public int getLength() {
        return wrapper.getLength();
    }

    @Override
    public void setLength(int length) {
        wrapper.setLength(length);
    }

}
