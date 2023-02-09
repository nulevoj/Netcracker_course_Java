package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.NetworkElement;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.connection.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.connection.entity.wrapper.AbstractConnectionWrapper;
import com.netcracker.edu.inventory.model.connection.entity.wrapper.ObservableConnectionWrapper;
import com.netcracker.edu.inventory.model.connection.impl.DummyConnection;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.impl.DevicePK;
import com.netcracker.edu.inventory.model.device.entity.wrapper.AbstractDeviceWrapper;
import com.netcracker.edu.inventory.model.device.entity.wrapper.ObservableDeviceWrapper;
import com.netcracker.edu.inventory.model.device.impl.DummyDevice;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.rack.RackPrimaryKey;
import com.netcracker.edu.inventory.model.rack.impl.RackArrayImpl;
import com.netcracker.edu.inventory.model.rack.impl.RackPK;
import com.netcracker.edu.inventory.model.rack.wrapper.ImmutableRackWrapper;
import com.netcracker.edu.inventory.model.rack.wrapper.ObservableRackWrapper;
import com.netcracker.edu.inventory.model.rack.wrapper.SynchronizedRackWrapper;
import com.netcracker.edu.inventory.service.EntityFactory;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.Trunk;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityFactoryImpl implements EntityFactory {

    private static final Logger log = Logger.getLogger(EntityFactoryImpl.class.getName());

    @Override
    public NetworkElement createEmptyNetworkElementImpl(String className) throws IllegalArgumentException {
        checkNameIsNullOrEmpty(className);
        return createEmptyNetworkElementImpl(getClassForName(className));
    }

    @Override
    public <T extends NetworkElement> T createEmptyNetworkElementImpl(Class<T> clazz) throws IllegalArgumentException {
        CommonUtility.checkClazzIsNull(clazz);
        return NetworkElementFactory.createNEInstance(clazz);
    }

    @Override
    public <T extends Device> Rack<T> createEmptyRackImpl(String name, int size, Class<T> limitation) throws IllegalArgumentException {
        final String RACK_PATH = "com.netcracker.edu.inventory.model.rack.impl.";
        checkNameIsNullOrEmpty(name);
        CommonUtility.checkClazzIsNull(limitation);
        name = RACK_PATH + name;
        Class clazz = getClassForName(name);
        if (Rack.class.isAssignableFrom(clazz)) {
            if (RackArrayImpl.class.isAssignableFrom(clazz)) {
                return new RackArrayImpl<>(size, limitation);
            }
            IllegalArgumentException e = new IllegalArgumentException("Unknown type of Rack");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        IllegalArgumentException e = new IllegalArgumentException("clazz can not be non-Rack");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    protected void checkNameIsNullOrEmpty(String name) {
        checkObjectIsNull(name, "name of class can not be null");
        if (name.isEmpty()) {
            IllegalArgumentException e = new IllegalArgumentException("name of class can not be empty");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    protected Class getClassForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException exception) {
            IllegalArgumentException e = new IllegalArgumentException("Class not found", exception);
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public DevicePrimaryKey createDevicePrimaryKey(int inventoryNumber) throws IllegalArgumentException {
        return new DevicePK(inventoryNumber);
    }

    @Override
    public ConnectionPrimaryKey createConnectionPrimaryKey(Trunk trunk, int serialNumber) throws IllegalArgumentException {
        return new ConnectionPK(trunk, serialNumber);
    }

    @Override
    public RackPrimaryKey createRackPrimaryKey(Location location) throws IllegalArgumentException {
        return new RackPK(location);
    }

    @Override
    public <K extends Unique.PrimaryKey, T extends Unique<K>> T createLazyInstance(K primaryKey) {
        if (primaryKey == null) {
            log.warning("primaryKey is null");
            return null;
        }
        if (primaryKey instanceof DevicePrimaryKey) {
            return (T) new DummyDevice((DevicePrimaryKey) primaryKey);
        }
        if (primaryKey instanceof ConnectionPrimaryKey) {
            return (T) new DummyConnection((ConnectionPrimaryKey) primaryKey);
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown primaryKey");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    @Override
    public <T extends NetworkElement> T getImmutableNetworkElement(T original) throws IllegalArgumentException {
        checkObjectIsNull(original, "original network element can not be null");
        return NetworkElementFactory.getImmutableNetworkElement(original);
    }

    @Override
    public <D extends Device> Rack<D> getImmutableRack(Rack<D> original) throws IllegalArgumentException {
        checkObjectIsNull(original, "original rack can not be null");
        return new ImmutableRackWrapper<>(original);
    }

    @Override
    public <T extends NetworkElement> T getSynchronizedNetworkElement(T original) throws IllegalArgumentException {
        checkObjectIsNull(original, "original network element can not be null");
        return NetworkElementFactory.getSynchronizedNetworkElement(original);
    }

    @Override
    public <D extends Device> Rack<D> getSynchronizedRack(Rack<D> original) throws IllegalArgumentException {
        checkObjectIsNull(original, "original rack can not be null");
        return new SynchronizedRackWrapper<>(original);
    }

    @Override
    public <T extends NetworkElement> T subscribeTo(T original, PropertyChangeListener listener) throws IllegalArgumentException {
        checkObjectIsNull(original, "original network element can not be null");
        checkObjectIsNull(listener, "listener can not be null");
        if (!checkNetworkElementIsWrapped(original)) {
            original = NetworkElementFactory.getObservableNetworkElement(original);
        }

        if (AbstractDeviceWrapper.class.isAssignableFrom(original.getClass())) {
            ((ObservableDeviceWrapper) (((AbstractDeviceWrapper) original).getWrapper())).addListener(listener);
        }
        if (AbstractConnectionWrapper.class.isAssignableFrom(original.getClass())) {
            ((ObservableConnectionWrapper) (((AbstractConnectionWrapper) original).getWrapper())).addListener(listener);
        }

        return original;
    }

    @Override
    public <D extends Device> Rack<D> subscribeTo(Rack<D> original, PropertyChangeListener listener) throws IllegalArgumentException {
        checkObjectIsNull(original, "original rack can not be null");
        checkObjectIsNull(listener, "listener can not be null");
        if (!ObservableRackWrapper.class.isAssignableFrom(original.getClass())) {
            original = new ObservableRackWrapper<>(original);
        }
        ((ObservableRackWrapper<D>) original).addListener(listener);
        return original;
    }

    @Override
    public boolean unsubscribeFrom(NetworkElement publisher, PropertyChangeListener listener) throws IllegalArgumentException {
        checkObjectIsNull(publisher, "original publisher can not be null");
        checkObjectIsNull(listener, "listener can not be null");
        if (!checkNetworkElementIsWrapped(publisher)) {
            return false;
        }

        if (AbstractDeviceWrapper.class.isAssignableFrom(publisher.getClass())) {
            ((ObservableDeviceWrapper) (((AbstractDeviceWrapper) publisher).getWrapper())).removeListener(listener);
        }
        if (AbstractConnectionWrapper.class.isAssignableFrom(publisher.getClass())) {
            ((ObservableConnectionWrapper) (((AbstractConnectionWrapper) publisher).getWrapper())).removeListener(listener);
        }

        return false;
    }

    @Override
    public boolean unsubscribeFrom(Rack publisher, PropertyChangeListener listener) throws IllegalArgumentException {
        checkObjectIsNull(publisher, "original publisher can not be null");
        checkObjectIsNull(listener, "listener can not be null");
        if (ObservableRackWrapper.class.isAssignableFrom(publisher.getClass())) {
            return ((ObservableRackWrapper) publisher).removeListener(listener);
        }
        return false;
    }

    protected boolean checkNetworkElementIsWrapped(NetworkElement networkElement) {
        if (AbstractDeviceWrapper.class.isAssignableFrom(networkElement.getClass())) {
            return true;
        }
        if (AbstractConnectionWrapper.class.isAssignableFrom(networkElement.getClass())) {
            return true;
        }
        return false;
    }

    protected void checkObjectIsNull(Object object, String message) throws IllegalArgumentException {
        CommonUtility.checkObjectIsNull(object, message);
    }

}
