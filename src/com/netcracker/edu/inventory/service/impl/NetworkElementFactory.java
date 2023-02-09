package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.connection.*;
import com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber;
import com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair;
import com.netcracker.edu.inventory.model.connection.entity.impl.Wireless;
import com.netcracker.edu.inventory.model.connection.entity.wrapper.*;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.entity.impl.Battery;
import com.netcracker.edu.inventory.model.device.entity.impl.Router;
import com.netcracker.edu.inventory.model.device.entity.impl.Switch;
import com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter;
import com.netcracker.edu.inventory.model.device.entity.wrapper.*;

import java.util.logging.Level;
import java.util.logging.Logger;


class NetworkElementFactory {

    private static final Logger log = Logger.getLogger(NetworkElementFactory.class.getName());

    public static <T extends NetworkElement> T createNEInstance(Class<T> clazz) {
        if (Entities.NetworkElement.getClazz().isAssignableFrom(clazz)) {
            if (Entities.Device.getClazz().isAssignableFrom(clazz)) {
                return createDeviceInstance(clazz);
            }
            if (Entities.Connection.getClazz().isAssignableFrom(clazz)) {
                return createConnectionInstance(clazz);
            }
            IllegalArgumentException e = new IllegalArgumentException("Unknown type of NetworkElement");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        IllegalArgumentException e = new IllegalArgumentException("clazz can not be non-NetworkElement");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private static <T extends NetworkElement> T createDeviceInstance(Class<T> clazz) {
        if (Entities.Battery.getClazz().isAssignableFrom(clazz)) {
            return (T) new Battery();
        }
        if (Entities.Router.getClazz().isAssignableFrom(clazz)) {
            if (Entities.Switch.getClazz().isAssignableFrom(clazz)) {
                return (T) new Switch();
            }
            if (Entities.WifiRouter.getClazz().isAssignableFrom(clazz)) {
                return (T) new WifiRouter();
            }
            return (T) new Router();
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of Device");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private static <T extends NetworkElement> T createConnectionInstance(Class<T> clazz) {
        if (OneToOneConnection.class.isAssignableFrom(clazz)) {
            if (Entities.OpticFiber.getClazz().isAssignableFrom(clazz)) {
                return (T) new OpticFiber();
            }
            if (Entities.TwistedPair.getClazz().isAssignableFrom(clazz)) {
                return (T) new TwistedPair();
            }
            IllegalArgumentException e = new IllegalArgumentException("Unknown type of OneToOneConnection");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (OneToManyConnection.class.isAssignableFrom(clazz)) {
            if (Entities.Wireless.getClazz().isAssignableFrom(clazz)) {
                return (T) new Wireless();
            }
            IllegalArgumentException e = new IllegalArgumentException("Unknown type of OneToManyConnection");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (ManyToManyConnection.class.isAssignableFrom(clazz)) {
            IllegalArgumentException e = new IllegalArgumentException("Unknown type of ManyToManyConnection");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        if (AllToAllConnection.class.isAssignableFrom(clazz)) {
            if (Entities.ThinCoaxial.getClazz().isAssignableFrom(clazz)) {
                return (T) new ThinCoaxial();
            }
            IllegalArgumentException e = new IllegalArgumentException("Unknown type of AllToAllConnection");
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of Connection");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    public static <T extends NetworkElement> T getImmutableNetworkElement(T original) throws IllegalArgumentException {
        if (Entities.Device.getClazz().isAssignableFrom(original.getClass())) {
            return getImmutableDevice(original);
        }
        if (Entities.Connection.getClazz().isAssignableFrom(original.getClass())) {
            return getImmutableConnection(original);
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of NetworkElement");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private static <T extends NetworkElement> T getImmutableDevice(T original) throws IllegalArgumentException {
        if (Entities.Battery.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new BatteryWrapper(new ImmutableDeviceWrapper((Device) original));
        }
        if (Entities.Router.getClazz().isAssignableFrom(original.getClass())) {
            if (Entities.WifiRouter.getClazz().isAssignableFrom(original.getClass())) {
                return (T) new WifiRouterWrapper(new ImmutableDeviceWrapper((Device) original));
            }
            if (Entities.Switch.getClazz().isAssignableFrom(original.getClass())) {
                return (T) new SwitchWrapper(new ImmutableDeviceWrapper((Device) original));
            }
            return (T) new RouterWrapper(new ImmutableDeviceWrapper((Device) original));
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of Device");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private static <T extends NetworkElement> T getImmutableConnection(T original) throws IllegalArgumentException {
        if (Entities.Wireless.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new WirelessWrapper(new ImmutableConnectionWrapper((Connection) original));
        }
        if (Entities.OpticFiber.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new OpticFiberWrapper(new ImmutableConnectionWrapper((Connection) original));
        }
        if (Entities.ThinCoaxial.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new ThinCoaxialWrapper(new ImmutableConnectionWrapper((Connection) original));
        }
        if (Entities.TwistedPair.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new TwistedPairWrapper(new ImmutableConnectionWrapper((Connection) original));
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of Connection");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    public static <T extends NetworkElement> T getObservableNetworkElement(T original) {
        if (Entities.Device.getClazz().isAssignableFrom(original.getClass())) {
            return getObservableDevice(original);
        }
        if (Entities.Connection.getClazz().isAssignableFrom(original.getClass())) {
            return getObservableConnection(original);
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of NetworkElement");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private static <T extends NetworkElement> T getObservableDevice(T original) throws IllegalArgumentException {
        if (Entities.Battery.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new BatteryWrapper(new ObservableDeviceWrapper((Device) original));
        }
        if (Entities.Router.getClazz().isAssignableFrom(original.getClass())) {
            if (Entities.WifiRouter.getClazz().isAssignableFrom(original.getClass())) {
                return (T) new WifiRouterWrapper(new ObservableDeviceWrapper((Device) original));
            }
            if (Entities.Switch.getClazz().isAssignableFrom(original.getClass())) {
                return (T) new SwitchWrapper(new ObservableDeviceWrapper((Device) original));
            }
            return (T) new RouterWrapper(new ObservableDeviceWrapper((Device) original));
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of Device");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private static <T extends NetworkElement> T getObservableConnection(T original) throws IllegalArgumentException {
        if (Entities.Wireless.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new WirelessWrapper(new ObservableConnectionWrapper((Connection) original));
        }
        if (Entities.OpticFiber.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new OpticFiberWrapper(new ObservableConnectionWrapper((Connection) original));
        }
        if (Entities.ThinCoaxial.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new ThinCoaxialWrapper(new ObservableConnectionWrapper((Connection) original));
        }
        if (Entities.TwistedPair.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new TwistedPairWrapper(new ObservableConnectionWrapper((Connection) original));
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of Connection");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    public static <T extends NetworkElement> T getSynchronizedNetworkElement(T original) {
        if (Entities.Device.getClazz().isAssignableFrom(original.getClass())) {
            return getObservableDevice(original);
        }
        if (Entities.Connection.getClazz().isAssignableFrom(original.getClass())) {
            return getObservableConnection(original);
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of NetworkElement");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private static <T extends NetworkElement> T getSynchronizedDevice(T original) throws IllegalArgumentException {
        if (Entities.Battery.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new BatteryWrapper(new SynchronizedDeviceWrapper((Device) original));
        }
        if (Entities.Router.getClazz().isAssignableFrom(original.getClass())) {
            if (Entities.WifiRouter.getClazz().isAssignableFrom(original.getClass())) {
                return (T) new WifiRouterWrapper(new SynchronizedDeviceWrapper((Device) original));
            }
            if (Entities.Switch.getClazz().isAssignableFrom(original.getClass())) {
                return (T) new SwitchWrapper(new SynchronizedDeviceWrapper((Device) original));
            }
            return (T) new RouterWrapper(new SynchronizedDeviceWrapper((Device) original));
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of Device");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

    private static <T extends NetworkElement> T getSynchronizedConnection(T original) throws IllegalArgumentException {
        if (Entities.Wireless.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new WirelessWrapper(new SynchronizedConnectionWrapper((Connection) original));
        }
        if (Entities.OpticFiber.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new OpticFiberWrapper(new SynchronizedConnectionWrapper((Connection) original));
        }
        if (Entities.ThinCoaxial.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new ThinCoaxialWrapper(new SynchronizedConnectionWrapper((Connection) original));
        }
        if (Entities.TwistedPair.getClazz().isAssignableFrom(original.getClass())) {
            return (T) new TwistedPairWrapper(new SynchronizedConnectionWrapper((Connection) original));
        }
        IllegalArgumentException e = new IllegalArgumentException("Unknown type of Connection");
        log.log(Level.SEVERE, e.getMessage(), e);
        throw e;
    }

}
