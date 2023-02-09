package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;

public class TwistedPairWrapper extends AbstractOneToOneWrapper implements TwistedPair {

    public TwistedPairWrapper(AbstractAllConnectionsWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public Type getType() {
        return wrapper.getType();
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
