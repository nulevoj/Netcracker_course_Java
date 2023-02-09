package com.netcracker.edu.inventory.model.device.entity.wrapper;

import com.netcracker.edu.inventory.model.device.entity.Router;

public class RouterWrapper extends AbstractDeviceWrapper implements Router {

    public RouterWrapper(AbstractAllDevicesWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public int getDataRate() {
        return wrapper.getDataRate();
    }

    @Override
    public void setDataRate(int dataRate) {
        wrapper.setDataRate(dataRate);
    }

}
