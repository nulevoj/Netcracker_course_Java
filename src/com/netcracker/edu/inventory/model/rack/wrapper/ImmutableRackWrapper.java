package com.netcracker.edu.inventory.model.rack.wrapper;

import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.location.Location;

import java.util.logging.Logger;

public class ImmutableRackWrapper<T extends Device> extends AbstractRackWrapper<T> {

    private static final Logger log = Logger.getLogger(ImmutableRackWrapper.class.getName());

    public ImmutableRackWrapper(Rack<T> rack) {
        super(rack);
    }

    @Override
    public void setLocation(Location location) {
        log.warning("Can not set location for immutable rack");
    }

    @Override
    public boolean insertDevToSlot(T device, int index) {
        log.warning("Can not insert device to immutable rack");
        return false;
    }

    @Override
    public T removeDevFromSlot(int index) {
        log.warning("Can not remove device from immutable rack");
        return null;
    }

}
