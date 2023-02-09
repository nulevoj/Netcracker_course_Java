package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.ModificationUtilities;
import com.netcracker.edu.inventory.model.NetworkElement;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.inventory.model.connection.entity.OpticFiber;
import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.connection.entity.Wireless;
import com.netcracker.edu.inventory.model.connection.impl.ConnectionPK;
import com.netcracker.edu.inventory.model.connection.impl.DummyConnection;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.model.device.impl.DevicePK;
import com.netcracker.edu.inventory.model.device.impl.DummyDevice;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.rack.RackPrimaryKey;
import com.netcracker.edu.inventory.model.rack.impl.RackArrayImpl;
import com.netcracker.edu.inventory.model.rack.impl.RackPK;
import com.netcracker.edu.inventory.service.EntityFactory;
import com.netcracker.edu.location.impl.LocationImpl;
import com.netcracker.edu.location.impl.TrunkImpl;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 27.05.17.
 */
public class EntityFactoryImplTest {

    protected static Map<Class, Map<Class, Boolean>> mapForHierarchyTest;

    protected EntityFactory entityFactory;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
        mapForHierarchyTest = getMapForHierarchyTest();
    }
    @Before
    public void setUp() throws Exception {
        entityFactory = new EntityFactoryImpl();
    }

    @Test
    public void createEmptyNetworkElementImpl_String() throws Exception {
        ThinCoaxial emptyThinCoaxial = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
        ThinCoaxial thinCoaxial = (ThinCoaxial) entityFactory.createEmptyNetworkElementImpl(ThinCoaxial.class.getName());
        ThinCoaxial anotherThinCoaxial = (ThinCoaxial) entityFactory.createEmptyNetworkElementImpl(ThinCoaxial.class.getName());

        assertNotNull(thinCoaxial);
        AssertUtilities.assertThinCoaxial(emptyThinCoaxial, thinCoaxial);
        assertFalse(thinCoaxial == anotherThinCoaxial);
    }

    @Test
    public void createEmptyNetworkElementImpl_Class() throws Exception {
        ThinCoaxial emptyThinCoaxial = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial();
        ThinCoaxial thinCoaxial =  entityFactory.createEmptyNetworkElementImpl(ThinCoaxial.class);
        ThinCoaxial anotherThinCoaxial = entityFactory.createEmptyNetworkElementImpl(ThinCoaxial.class);

        assertNotNull(thinCoaxial);
        AssertUtilities.assertThinCoaxial(emptyThinCoaxial, thinCoaxial);
        assertFalse(thinCoaxial == anotherThinCoaxial);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyNetworkElementImpl_StringNull() throws Exception {
        String arg = null;
        ThinCoaxial thinCoaxial = (ThinCoaxial) entityFactory.createEmptyNetworkElementImpl(arg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyNetworkElementImpl_StringEmpty() throws Exception {
        String arg = "";
        ThinCoaxial thinCoaxial = (ThinCoaxial) entityFactory.createEmptyNetworkElementImpl(arg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyNetworkElementImpl_StringNotClass() throws Exception {
        String arg = "ThinCoaxial";
        ThinCoaxial thinCoaxial = (ThinCoaxial) entityFactory.createEmptyNetworkElementImpl(arg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyNetworkElementImpl_StringWrongClass() throws Exception {
        String arg = EntityFactoryImplTest.class.getName();
        ThinCoaxial thinCoaxial = (ThinCoaxial) entityFactory.createEmptyNetworkElementImpl(arg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyNetworkElementImpl_ClassNull() throws Exception {
        Class arg = null;
        ThinCoaxial thinCoaxial = (ThinCoaxial) entityFactory.createEmptyNetworkElementImpl(arg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyNetworkElementImpl_ClassWrongClass() throws Exception {
        Class arg = EntityFactoryImplTest.class;
        ThinCoaxial thinCoaxial = (ThinCoaxial) entityFactory.createEmptyNetworkElementImpl(arg);
    }

    @Test
    public void createNEInstance_childClass() throws Exception {
        Switch expSwitch = new com.netcracker.edu.inventory.model.device.entity.impl.Switch();

        Switch result = entityFactory.createEmptyNetworkElementImpl(
                new com.netcracker.edu.inventory.model.device.entity.impl.Switch() {}.getClass());

        assertNotNull(result);
        assertEquals(expSwitch.getClass(), result.getClass());
        AssertUtilities.assertSwitch(expSwitch, result);
    }

    @Test
    public void createEmptyRackImpl() throws Exception {
        String name = "RackArrayImpl";
        int size = 10;
        Class limiter = Router.class;
        Rack expRack = new RackArrayImpl(size, limiter);
        Rack rack = entityFactory.createEmptyRackImpl(name, size, limiter);
        Rack anotherRack = entityFactory.createEmptyRackImpl(name, size, limiter);

        assertNotNull(rack);
        AssertUtilities.assertRack(expRack, rack);
        assertFalse(rack == anotherRack);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyRackImpl_StringNull() throws Exception {
        String name = null;
        int size = 10;
        Class limiter = Router.class;
        Rack rack = entityFactory.createEmptyRackImpl(name, size, limiter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyRackImpl_StringEmpty() throws Exception {
        String name = "";
        int size = 10;
        Class limiter = Router.class;
        Rack rack = entityFactory.createEmptyRackImpl(name, size, limiter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyRackImpl_StringWrongClass() throws Exception {
        String name = "RackListImpl";
        int size = 10;
        Class limiter = Router.class;
        Rack rack = entityFactory.createEmptyRackImpl(name, size, limiter);
    }

    @Test
    public void createDevicePrimaryKey() throws Exception {
        DevicePrimaryKey expDevicePrimaryKey = new DevicePK(1);

        DevicePrimaryKey result = entityFactory.createDevicePrimaryKey(1);

        AssertUtilities.assertDevicePK(expDevicePrimaryKey, result);
    }

    @Test
    public void createConnectionPrimaryKey() throws Exception {
        ConnectionPrimaryKey expConnectionPrimaryKey = new ConnectionPK(new TrunkImpl("", ""), 1);

        ConnectionPrimaryKey result = entityFactory.createConnectionPrimaryKey(new TrunkImpl("", ""), 1);

        AssertUtilities.assertConnectionPK(expConnectionPrimaryKey, result);
    }

    @Test
    public void createRackPrimaryKey() throws Exception {
        RackPrimaryKey expRackPrimaryKey = new RackPK(new LocationImpl("", ""));

        RackPrimaryKey result = entityFactory.createRackPrimaryKey(new LocationImpl("", ""));

        AssertUtilities.assertRackPK(expRackPrimaryKey, result);
    }

    @Test
    public void createLazyInstance() throws Exception {
        DevicePrimaryKey devicePK = new DevicePK(1);
        DummyDevice expDummyDevice = new DummyDevice(devicePK);
        ConnectionPrimaryKey connectionPK = new ConnectionPK(new TrunkImpl("", ""), 1);
        DummyConnection expDummyConnection = new DummyConnection(connectionPK);

        Device lazyDevice = entityFactory.createLazyInstance(devicePK);
        Connection lazyConnection = entityFactory.createLazyInstance(connectionPK);

        assertNotNull(lazyDevice);
        assertTrue(lazyDevice instanceof DummyDevice);
        AssertUtilities.assertDummyDevice(expDummyDevice, (DummyDevice) lazyDevice);
        assertNotNull(lazyConnection);
        assertTrue(lazyConnection instanceof DummyConnection);
        AssertUtilities.assertDummyConnection(expDummyConnection, (DummyConnection) lazyConnection);
    }

    @Test
    public void createLazyInstance_Null() throws Exception {
        Unique.PrimaryKey arg = null;
        Unique unique = entityFactory.createLazyInstance(arg);

        assertNull(unique);
    }

    @Test
    public void getImmutableNetworkElement() throws Exception {
        Map<Class, NetworkElement> originals = createTestScope();
        Map<Class, NetworkElement> originalCopies = createTestScope();
        Map<Class, NetworkElement> modifiedCopies = createTestScope();
        modifyTestScope(modifiedCopies);
        Map<Class, NetworkElement> results = new HashMap<>();

        Set<Map.Entry<Class, NetworkElement>> originalEntries = originals.entrySet();
        for (Map.Entry<Class, NetworkElement> element : originalEntries) {
            results.put(element.getKey(), entityFactory.getImmutableNetworkElement(element.getValue()));
        }

        for (Map.Entry<Class, NetworkElement> element : originalEntries) {
            checkWrapperability(element.getValue(), results.get(element.getKey()), element.getKey());
        }
        modifyTestScope(results); //modify wrapper
        for (Map.Entry<Class, NetworkElement> element : originalEntries) {
            AssertUtilities.assertSomeNetworkElement(element.getValue(), results.get(element.getKey()));
        }
        Set<Map.Entry<Class, NetworkElement>> originalCopyEntries = originalCopies.entrySet();
        for (Map.Entry<Class, NetworkElement> element : originalCopyEntries) {
            AssertUtilities.assertSomeNetworkElement(element.getValue(), originals.get(element.getKey()));
        }
        for (Map.Entry<Class, NetworkElement> element : originalCopyEntries) {
            AssertUtilities.assertSomeNetworkElement(element.getValue(), results.get(element.getKey()));
        }
        modifyTestScope(originals); //modify original
        for (Map.Entry<Class, NetworkElement> element : originalEntries) {
            AssertUtilities.assertSomeNetworkElement(element.getValue(), results.get(element.getKey()));
        }
        Set<Map.Entry<Class, NetworkElement>> modifiedCopyEntries = modifiedCopies.entrySet();
        for (Map.Entry<Class, NetworkElement> element : modifiedCopyEntries) {
            AssertUtilities.assertSomeNetworkElement(element.getValue(), originals.get(element.getKey()));
        }
        for (Map.Entry<Class, NetworkElement> element : modifiedCopyEntries) {
            AssertUtilities.assertSomeNetworkElement(element.getValue(), results.get(element.getKey()));
        }
    }

    @Test
    public void getImmutableRack() throws Exception {
        Rack original = CreateUtilities.createRack();
        Rack originalCopy = CreateUtilities.createRack();
        Rack modifiedCopy = CreateUtilities.createRack();
        ModificationUtilities.modifyRack(modifiedCopy);

        Rack result = entityFactory.getImmutableRack(original);

        checkWrapperability(original, result);
        ModificationUtilities.modifyRack(result); //modify wrapper
        AssertUtilities.assertRack(original, result);
        AssertUtilities.assertRack(originalCopy, original);
        AssertUtilities.assertRack(originalCopy, result);
        ModificationUtilities.modifyRack(original); //modify original
        AssertUtilities.assertRack(original, result);
        AssertUtilities.assertRack(modifiedCopy, original);
        AssertUtilities.assertRack(modifiedCopy, result);
    }

    @Test
    public void getSynchronizedNetworkElement() throws Exception {
        Map<Class, NetworkElement> originals = createTestScope();
        Map<Class, NetworkElement> results = new HashMap<>();

        Set<Map.Entry<Class, NetworkElement>> originalEntries = originals.entrySet();
        for (Map.Entry<Class, NetworkElement> element : originalEntries) {
            results.put(element.getKey(), entityFactory.getSynchronizedNetworkElement(element.getValue()));
        }

        for (Map.Entry<Class, NetworkElement> element : originalEntries) {
            checkWrapperability(element.getValue(), results.get(element.getKey()), element.getKey());
        }
    }

    @Test
    public void getSynchronizedRack() throws Exception {
        Rack original = CreateUtilities.createRack();

        Rack result = entityFactory.getSynchronizedRack(original);

        checkWrapperability(original, result);
    }

    @Test
    public void subscribeToUnsubscribeFrom_NE() throws Exception {
        Map<Class, NetworkElement> originals = createTestScope();
        Map<Class, List<PropertyChangeEvent>> expChanges = ModificationUtilities.getPlanedChanges(originals);
        Map<Class, List<PropertyChangeEvent>> expNoChanges = ModificationUtilities.getNoChangesInNE();
        Map<Class, ChangeTracker> trackers1 = new HashMap<>();
        Map<Class, ChangeTracker> trackers2 = new HashMap<>();
        Map<Class, ChangeTracker> trackers3 = new HashMap<>();
        Map<Class, NetworkElement> results = new HashMap<>();

        Set<Map.Entry<Class, NetworkElement>> originalEntries = originals.entrySet();
        for (Map.Entry<Class, NetworkElement> element : originalEntries) {
            ChangeTracker tracker = new ChangeTracker();
            NetworkElement wrapper = entityFactory.subscribeTo(element.getValue(), tracker);
            trackers1.put(element.getKey(), tracker);
            tracker = new ChangeTracker();
            entityFactory.subscribeTo(wrapper, tracker);
            trackers2.put(element.getKey(), tracker);
            tracker = new ChangeTracker();
            entityFactory.subscribeTo(wrapper, tracker);
            trackers3.put(element.getKey(), tracker);
            results.put(element.getKey(), wrapper);
        }
        Set<Map.Entry<Class, NetworkElement>> resultEntries = results.entrySet();
        for (Map.Entry<Class, NetworkElement> element : resultEntries) {
            entityFactory.unsubscribeFrom(element.getValue(), trackers2.get(element.getKey()));
        }

        for (Map.Entry<Class, NetworkElement> element : originalEntries) {
            checkWrapperability(element.getValue(), results.get(element.getKey()), element.getKey());
        }
        modifyTestScope(results); //modify wrappers. Generate events
        assertChanges(expChanges, unpackChanges(trackers1));
        assertChanges(expNoChanges, unpackChanges(trackers2));
        assertChanges(expChanges, unpackChanges(trackers3));

        clearLogs(trackers1);
        clearLogs(trackers2);
        clearLogs(trackers3);
        modifyTestScope(originals); //modify originals. No generate events
        assertChanges(expNoChanges, unpackChanges(trackers1));
        assertChanges(expNoChanges, unpackChanges(trackers2));
        assertChanges(expNoChanges, unpackChanges(trackers3));
    }

    @Test
    public void subscribeToUnsubscribeFrom_Rack() throws Exception {
        Rack original = CreateUtilities.createRack();
        List<PropertyChangeEvent> expChanges = ModificationUtilities.getPlanedChanges(original);
        List<PropertyChangeEvent> expNoChanges = ModificationUtilities.getNoChangesInRack();
        ChangeTracker tracker1 = new ChangeTracker();
        ChangeTracker tracker2 = new ChangeTracker();

        Rack result = entityFactory.subscribeTo(original, tracker1);
        entityFactory.subscribeTo(result, tracker2);
        entityFactory.unsubscribeFrom(result, tracker1);

        checkWrapperability(original, result);
        ModificationUtilities.modifyRack(result); //modify wrapper. Generate events
        assertChanges(expNoChanges, tracker1.getLog());
        assertChanges(expChanges, tracker2.getLog());

        tracker1.clearLog();
        tracker2.clearLog();
        ModificationUtilities.modifyRack(original); //modify original. No generate events
        assertChanges(expNoChanges, tracker1.getLog());
        assertChanges(expNoChanges, tracker2.getLog());
    }

    @Test(expected = IllegalArgumentException.class)
    public void subscribeTo_NENull() throws Exception {
        entityFactory.subscribeTo((NetworkElement) null, new ChangeTracker());
    }

    @Test(expected = IllegalArgumentException.class)
    public void subscribeTo_RackNull() throws Exception {
        entityFactory.subscribeTo((Rack) null, new ChangeTracker());
    }

    @Test(expected = IllegalArgumentException.class)
    public void subscribeTo_listenerNull() throws Exception {
        entityFactory.subscribeTo(new com.netcracker.edu.inventory.model.device.entity.impl.Battery(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unsubscribeFrom_NENull() throws Exception {
        entityFactory.unsubscribeFrom((NetworkElement) null, new ChangeTracker());
    }

    @Test(expected = IllegalArgumentException.class)
    public void unsubscribeFrom_RackNull() throws Exception {
        entityFactory.unsubscribeFrom((Rack) null, new ChangeTracker());
    }

    @Test(expected = IllegalArgumentException.class)
    public void unsubscribeFrom_listenerNull() throws Exception {
        entityFactory.unsubscribeFrom(new com.netcracker.edu.inventory.model.device.entity.impl.Battery(), null);
    }

    protected Map<Class, NetworkElement> createTestScope() {
        Map<Class, NetworkElement> result = new HashMap<>();
        result.put(Battery.class, CreateUtilities.createBattery());
        result.put(Router.class, CreateUtilities.createRouter());
        result.put(Switch.class, CreateUtilities.createSwitchWithConnections());
        result.put(WifiRouter.class, CreateUtilities.createWifiRouterWithConnections());
        result.put(OpticFiber.class, CreateUtilities.createOpticFiberWithDevices());
        result.put(TwistedPair.class, CreateUtilities.createTwistedPairWithDevices());
        result.put(ThinCoaxial.class, CreateUtilities.createThinCoaxialWithDevices());
        result.put(Wireless.class, CreateUtilities.createWirelessWithDevices());
        return result;
    }

    protected void modifyTestScope(Map<Class, NetworkElement> scope) {
        for (Map.Entry<Class, NetworkElement> element : scope.entrySet()) {
            if (Device.class.isAssignableFrom(element.getKey())) {
                if (Battery.class == element.getKey()) {
                    ModificationUtilities.modifyBattery((Battery) element.getValue(), 0);
                    continue;
                }
                if (Router.class == element.getKey()) {
                    ModificationUtilities.modifyRouter((Router) element.getValue(), 0);
                    continue;
                }
                if (Switch.class == element.getKey()) {
                    ModificationUtilities.modifySwitch((Switch) element.getValue(), 0);
                    continue;
                }
                if (WifiRouter.class == element.getKey()) {
                    ModificationUtilities.modifyWifiRouter((WifiRouter) element.getValue(), 0);
                    continue;
                }
            }
            if (Connection.class.isAssignableFrom(element.getKey())) {
                if (OpticFiber.class == element.getKey()) {
                    ModificationUtilities.modifyOpticFiber((OpticFiber) element.getValue(), null, 0);
                    continue;
                }
                if (TwistedPair.class == element.getKey()) {
                    ModificationUtilities.modifyTwistedPair((TwistedPair) element.getValue(), null, 0);
                    continue;
                }
                if (ThinCoaxial.class == element.getKey()) {
                    ModificationUtilities.modifyThinCoaxial((ThinCoaxial) element.getValue(), null, 0);
                    continue;
                }
                if (Wireless.class == element.getKey()) {
                    ModificationUtilities.modifyWireless((Wireless) element.getValue(), null, 0);
                    continue;
                }
            }
        }
    }

    protected <T extends NetworkElement> void checkWrapperability(T original, T result, Class<T> clazz) throws Exception {
        assertFalse(original == result);
        AssertUtilities.assertSomeNetworkElement(original, result);
        Set<Map.Entry<Class, Boolean>> tests = mapForHierarchyTest.get(clazz).entrySet();
        for (Map.Entry<Class, Boolean> test : tests) {
            assertTrue(test.getKey().isInstance(result) == test.getValue());
        }
    }

    protected void checkWrapperability(Rack original, Rack result) throws Exception {
        assertFalse(original == result);
        AssertUtilities.assertRack(original, result);
    }

    protected void assertChanges(Map<Class, List<PropertyChangeEvent>> expChanges, Map<Class, List<PropertyChangeEvent>> changes) throws Exception {
        Set<Map.Entry<Class, List<PropertyChangeEvent>>> expChangeEntries = expChanges.entrySet();
        for (Map.Entry<Class, List<PropertyChangeEvent>> expElementChanges : expChangeEntries) {
            assertChanges(expElementChanges.getValue(), changes.get(expElementChanges.getKey()));
        }
    }

    protected void assertChanges(List<PropertyChangeEvent> expEvents, List<PropertyChangeEvent> events) throws Exception {assertEquals(expEvents.size(), events.size());
        Iterator<PropertyChangeEvent> eventIterator = events.iterator();
        for (PropertyChangeEvent expEvent : expEvents) {
            AssertUtilities.assertPropertyChangeEvent(expEvent, eventIterator.next());
        }
    }

    protected Map<Class, List<PropertyChangeEvent>> unpackChanges(Map<Class, ChangeTracker> trackers) {
        Map<Class, List<PropertyChangeEvent>> result = new HashMap<>();
        for (Map.Entry<Class, ChangeTracker> trackerEntry : trackers.entrySet()) {
            result.put(trackerEntry.getKey(), trackerEntry.getValue().getLog());
        }
        return result;
    }

    protected void clearLogs(Map<Class, ChangeTracker> trackers) {
        for (ChangeTracker tracker : trackers.values()) {
            tracker.clearLog();
        }
    }

    protected static Map<Class, Map<Class, Boolean>> getMapForHierarchyTest() {
        Map<Class, Map<Class, Boolean>> result = new HashMap<>();
        Boolean aTrue = Boolean.TRUE;
        Boolean aFalse = Boolean.FALSE;

        Map<Class, Boolean> batteryMap = new HashMap<>();
        batteryMap.put(Device.class, aTrue);
        batteryMap.put(Connection.class, aFalse);
        batteryMap.put(Battery.class, aTrue);
        batteryMap.put(Router.class, aFalse);
        result.put(Battery.class, batteryMap);
        Map<Class, Boolean> routerMap = new HashMap<>();
        routerMap.put(Device.class, aTrue);
        routerMap.put(Connection.class, aFalse);
        routerMap.put(Battery.class, aFalse);
        routerMap.put(Router.class, aTrue);
        routerMap.put(Switch.class, aFalse);
        routerMap.put(WifiRouter.class, aFalse);
        result.put(Router.class, routerMap);
        Map<Class, Boolean> aSwitchMap = new HashMap<>();
        aSwitchMap.put(Device.class, aTrue);
        aSwitchMap.put(Connection.class, aFalse);
        aSwitchMap.put(Battery.class, aFalse);
        aSwitchMap.put(Router.class, aTrue);
        aSwitchMap.put(Switch.class, aTrue);
        aSwitchMap.put(WifiRouter.class, aFalse);
        result.put(Switch.class, aSwitchMap);
        Map<Class, Boolean> wifiRouterMap = new HashMap<>();
        wifiRouterMap.put(Device.class, aTrue);
        wifiRouterMap.put(Connection.class, aFalse);
        wifiRouterMap.put(Battery.class, aFalse);
        wifiRouterMap.put(Router.class, aTrue);
        wifiRouterMap.put(Switch.class, aFalse);
        wifiRouterMap.put(WifiRouter.class, aTrue);
        result.put(WifiRouter.class, wifiRouterMap);

        Map<Class, Boolean> opticFiberMap = new HashMap<>();
        opticFiberMap.put(Device.class, aFalse);
        opticFiberMap.put(Connection.class, aTrue);
        opticFiberMap.put(OpticFiber.class, aTrue);
        opticFiberMap.put(TwistedPair.class, aFalse);
        opticFiberMap.put(ThinCoaxial.class, aFalse);
        opticFiberMap.put(Wireless.class, aFalse);
        result.put(OpticFiber.class, opticFiberMap);
        Map<Class, Boolean> twistedPairMap = new HashMap<>();
        twistedPairMap.put(Device.class, aFalse);
        twistedPairMap.put(Connection.class, aTrue);
        twistedPairMap.put(OpticFiber.class, aFalse);
        twistedPairMap.put(TwistedPair.class, aTrue);
        twistedPairMap.put(ThinCoaxial.class, aFalse);
        twistedPairMap.put(Wireless.class, aFalse);
        result.put(TwistedPair.class, twistedPairMap);
        Map<Class, Boolean> thinCoaxialMap = new HashMap<>();
        thinCoaxialMap.put(Device.class, aFalse);
        thinCoaxialMap.put(Connection.class, aTrue);
        thinCoaxialMap.put(OpticFiber.class, aFalse);
        thinCoaxialMap.put(TwistedPair.class, aFalse);
        thinCoaxialMap.put(ThinCoaxial.class, aTrue);
        thinCoaxialMap.put(Wireless.class, aFalse);
        result.put(ThinCoaxial.class, thinCoaxialMap);
        Map<Class, Boolean> wirelessMap = new HashMap<>();
        wirelessMap.put(Device.class, aFalse);
        wirelessMap.put(Connection.class, aTrue);
        wirelessMap.put(OpticFiber.class, aFalse);
        wirelessMap.put(TwistedPair.class, aFalse);
        wirelessMap.put(ThinCoaxial.class, aFalse);
        wirelessMap.put(Wireless.class, aTrue);
        result.put(Wireless.class, wirelessMap);

        return result;
    }

    private class ChangeTracker implements PropertyChangeListener {

        private List<PropertyChangeEvent> log = new ArrayList<>();

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            log.add(evt);
        }

        public List<PropertyChangeEvent> getLog() {
            return log;
        }

        public void clearLog() {
            log = new ArrayList<>();
        }
    }

}