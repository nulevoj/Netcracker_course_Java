package com.netcracker.edu.inventory.model.device.entity.wrapper;

import com.netcracker.edu.inventory.model.device.entity.Battery;

public class BatteryWrapper extends AbstractDeviceWrapper implements Battery {

    public BatteryWrapper(AbstractAllDevicesWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public int getChargeVolume() {
        return wrapper.getChargeVolume();
    }

    @Override
    public void setChargeVolume(int chargeVolume) {
        wrapper.setChargeVolume(chargeVolume);
    }

}
