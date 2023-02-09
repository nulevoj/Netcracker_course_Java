package com.netcracker.edu.inventory.model.rack.wrapper;

import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.location.Location;

import java.beans.PropertyChangeListener;

public class SynchronizedRackWrapper<T extends Device> extends ObservableRackWrapper<T> {

    public SynchronizedRackWrapper(Rack<T> rack) {
        super(rack);
    }

    @Override
    public synchronized boolean addListener(PropertyChangeListener listener) {
        return super.addListener(listener);
    }

    @Override
    public synchronized boolean removeListener(PropertyChangeListener listener) {
        return super.removeListener(listener);
    }

    @Override
    protected synchronized void notifyListeners(Object source, String propertyName, Object oldValue, Object newValue) {
        super.notifyListeners(source, propertyName, oldValue, newValue);
    }

    @Override
    public synchronized void setLocation(Location location) {
        super.setLocation(location);
    }

    @Override
    public synchronized boolean insertDevToSlot(T device, int index) {
        return super.insertDevToSlot(device, index);
    }

    @Override
    public synchronized T removeDevFromSlot(int index) {
        return super.removeDevFromSlot(index);
    }

}
