package com.netcracker.edu.inventory.model.rack.wrapper;

import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.location.Location;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

public class ObservableRackWrapper<T extends Device> extends AbstractRackWrapper<T> {

    protected Set<PropertyChangeListener> listeners = new HashSet<>();

    public ObservableRackWrapper(Rack<T> rack) {
        super(rack);
    }

    public boolean addListener(PropertyChangeListener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(PropertyChangeListener listener) {
        return listeners.remove(listener);
    }

    protected void notifyListeners(Object source, String propertyName, Object oldValue, Object newValue) {
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(new PropertyChangeEvent(source, propertyName, oldValue, newValue));
        }
    }

    @Override
    public void setLocation(Location location) {
        notifyListeners(this, "location", getLocation(), location);
        super.setLocation(location);
    }

    @Override
    public boolean insertDevToSlot(T device, int index) {
        notifyListeners(this, "devices", getDevAtSlot(index), device);
        return super.insertDevToSlot(device, index);
    }

    @Override
    public T removeDevFromSlot(int index) {
        notifyListeners(this, "devices", getDevAtSlot(index), null);
        return super.removeDevFromSlot(index);
    }

}
