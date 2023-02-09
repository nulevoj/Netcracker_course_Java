package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.NetworkElement;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.entity.OpticFiber;
import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.connection.entity.Wireless;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.Trunk;
import com.netcracker.edu.location.impl.LocationImpl;
import com.netcracker.edu.location.impl.TrunkImpl;

import java.beans.PropertyChangeEvent;
import java.util.*;

public class ModificationUtilities {

    private static final String TYPE = "null->mod";
    private static final String MANUFACTURER = "->mod";
    private static final String MODEL = "Smart-UPS 750 ВА 230 В U2->mod";
    private static final Date PRODUCTION_DATE = new Date(2);

    private static final Trunk OPTIC_FIBER_TRUNK = new TrunkImpl("Test->mod", "test->mod");
    private static final Trunk TWISTED_PAIR_TRUNK = new TrunkImpl("ua.od.onpu.ics.607.east_wall-608.west_wall->mod", "NC TC Internet to 608->mod");
    private static final Trunk THIN_COAXIAL_TRUNK = new TrunkImpl("Test2->mod", "test2->mod");
    private static final Trunk WIRELESS_TRUNK = new TrunkImpl("ua.od.onpu.ics.607.east_wall-area->mod", "NC TC Wifi->mod");
    private static final Location RACK_LOCATION = new LocationImpl("ua.od.onpu.ics.607.east_wall->mod", "NC_TC_Odessa->mod");

    public static void modifyDevice(Device device, int in) {
        device.setIn(in);
        device.setType(TYPE);
        device.setManufacturer(MANUFACTURER);
        device.setModel(MODEL);
        device.setProductionDate(PRODUCTION_DATE);
    }

    public static void modifyBattery(Battery battery, int in) {
        if (in == 0) {
            in = 14;
        }
        modifyDevice(battery, in);
        battery.setChargeVolume(5001);
    }

    public static void modifyRouter(Router router, int in) {
        if (in == 0) {
            in = 12;
        }
        modifyDevice(router, in);
        router.setDataRate(1001);
    }

    public static void modifySwitch(Switch aSwitch, int in) {
        if (in == 0) {
            in = 17;
        }
        modifyRouter(aSwitch, in);
//        aSwitch.setNumberOfPorts(17);
        if (aSwitch.getPortConnection(4) != null) {
            aSwitch.setPortConnection(CreateUtilities.createTwistedPair(20), 4);
        }
    }

    public static void modifyWifiRouter(WifiRouter wifiRouter, int in) {
        if (in == 0) {
            in = 15;
        }
        modifyRouter(wifiRouter, in);
        wifiRouter.setSecurityProtocol(" ->mod");
        if (wifiRouter.getWireConnection() != null) {
            wifiRouter.setWireConnection(CreateUtilities.createTwistedPair(21));
        }
        if (wifiRouter.getWirelessConnection() != null) {
            wifiRouter.setWirelessConnection(CreateUtilities.createWireless(22));
        }
    }

    public static void modifyConnection(Connection connection, Trunk trunk, int serialNumber) {
        connection.setTrunk(trunk);
        connection.setSerialNumber(serialNumber);
        connection.setStatus(Connection.ON_DISASSEMBLING);
    }

    public static void modifyTwistedPair(TwistedPair twistedPair, Trunk trunk, int serialNumber) {
        if (serialNumber == 0) {
            serialNumber = 14;
        }
        if (trunk == null) {
            trunk = TWISTED_PAIR_TRUNK;
        }
        modifyConnection(twistedPair, trunk, serialNumber);
        twistedPair.setLength(101);
        if (twistedPair.getAPoint() != null) {
            twistedPair.setAPoint(CreateUtilities.createWifiRouter(20));
        }
    }

    public static void modifyOpticFiber(OpticFiber opticFiber, Trunk trunk, int serialNumber) {
        if (serialNumber == 0) {
            serialNumber = 15;
        }
        if (trunk == null) {
            trunk = OPTIC_FIBER_TRUNK;
        }
        modifyConnection(opticFiber, trunk, serialNumber);
        opticFiber.setLength(1001);
        if (opticFiber.getBPoint() != null) {
            opticFiber.setBPoint(CreateUtilities.createWifiRouter(21));
        }
    }

    public static void modifyWireless(Wireless wireless, Trunk trunk, int serialNumber) {
        if (serialNumber == 0) {
            serialNumber = 13;
        }
        if (trunk == null) {
            trunk = WIRELESS_TRUNK;
        }
        modifyConnection(wireless, trunk, serialNumber);
        wireless.setProtocol("WPA->mod");
        wireless.setVersion(3);
        if (wireless.getAPoint() != null) {
            wireless.setAPoint(CreateUtilities.createWifiRouter(22));
        }
        if (wireless.getBPoint(0) != null) {
            wireless.setBPoint(CreateUtilities.createWifiRouter(23), 0);
        }
    }

    public static void modifyThinCoaxial(ThinCoaxial thinCoaxial, Trunk trunk, int serialNumber) {
        if (serialNumber == 0) {
            serialNumber = 15;
        }
        if (trunk == null) {
            trunk = THIN_COAXIAL_TRUNK;
        }
        modifyConnection(thinCoaxial, trunk, serialNumber);
        if (!thinCoaxial.getAllDevices().isEmpty()) {
            thinCoaxial.addDevice(CreateUtilities.createWifiRouter(23));
            Device temp = CreateUtilities.createWifiRouter(24);
            thinCoaxial.addDevice(temp);
            thinCoaxial.removeDevice(temp);
        }
    }

    public static void modifyRack(Rack rack) {
        rack.setLocation(RACK_LOCATION);
        rack.removeDevFromSlot(2);
        rack.insertDevToSlot(CreateUtilities.createWifiRouter(),1);
    }

    public static Map<Class, List<PropertyChangeEvent>> getPlanedChanges(Map<Class, NetworkElement> sources) {
        Map<Class, List<PropertyChangeEvent>> result = new HashMap<>();
        List<PropertyChangeEvent> list;
        {
            list = new ArrayList();
            Battery source = (Battery) sources.get(Battery.class);
            addDeviceChanges(list, source, 14);
            list.add(new PropertyChangeEvent(source, "chargeVolume", source.getChargeVolume(), 5001));
            result.put(Battery.class, list);
        }{
            list = new ArrayList();
            Router source = (Router) sources.get(Router.class);
            addDeviceChanges(list, source, 12);
            list.add(new PropertyChangeEvent(source, "dataRate", source.getDataRate(), 1001));
            result.put(Router.class, list);
        }{
            list = new ArrayList();
            Switch source = (Switch) sources.get(Switch.class);
            addDeviceChanges(list, source, 17);
            list.add(new PropertyChangeEvent(source, "dataRate", source.getDataRate(), 1001));
//            list.add(new PropertyChangeEvent(source, "numberOfPorts", source.getNumberOfPorts(), 17));
            if (source.getPortConnection(4) != null) {
                list.add(new PropertyChangeEvent(source, "portConnection", source.getPortConnection(4), CreateUtilities.createTwistedPair(20)));
            }
            result.put(Switch.class, list);
        }{
            list = new ArrayList();
            WifiRouter source = (WifiRouter) sources.get(WifiRouter.class);
            addDeviceChanges(list, source, 15);
            list.add(new PropertyChangeEvent(source, "dataRate", source.getDataRate(), 1001));
            list.add(new PropertyChangeEvent(source, "securityProtocol", source.getSecurityProtocol(), " ->mod"));
            if (source.getWireConnection() != null) {
                list.add(new PropertyChangeEvent(source, "wireConnection", source.getWireConnection(), CreateUtilities.createTwistedPair(21)));
            }
            if (source.getWirelessConnection() != null) {
                list.add(new PropertyChangeEvent(source, "wirelessConnection", source.getWirelessConnection(), CreateUtilities.createWireless(22)));
            }
            result.put(WifiRouter.class, list);
        }
        {
            list = new ArrayList();
            OpticFiber source = (OpticFiber) sources.get(OpticFiber.class);
            addConnectionChanges(list, source, OPTIC_FIBER_TRUNK, 15);
            list.add(new PropertyChangeEvent(source, "length", source.getLength(), 1001));
            if (source.getBPoint() != null) {
                list.add(new PropertyChangeEvent(source, "bPoint", source.getBPoint(), CreateUtilities.createWifiRouter(21)));
            }
            result.put(OpticFiber.class, list);
        }{
            list = new ArrayList();
            TwistedPair source = (TwistedPair) sources.get(TwistedPair.class);
            addConnectionChanges(list, source, TWISTED_PAIR_TRUNK, 14);
            list.add(new PropertyChangeEvent(source, "length", source.getLength(), 101));
            if (source.getAPoint() != null) {
                list.add(new PropertyChangeEvent(source, "aPoint", source.getAPoint(), CreateUtilities.createWifiRouter(20)));
            }
            result.put(TwistedPair.class, list);
        }{
            list = new ArrayList();
            ThinCoaxial source = (ThinCoaxial) sources.get(ThinCoaxial.class);
            addConnectionChanges(list, source, THIN_COAXIAL_TRUNK, 15);
            if (!source.getAllDevices().isEmpty()) {
                list.add(new PropertyChangeEvent(source, "devices", null, CreateUtilities.createWifiRouter(23)));
                list.add(new PropertyChangeEvent(source, "devices", null, CreateUtilities.createWifiRouter(24)));
                list.add(new PropertyChangeEvent(source, "devices", CreateUtilities.createWifiRouter(24), null));
            }
            result.put(ThinCoaxial.class, list);
        }{
            list = new ArrayList();
            Wireless source = (Wireless) sources.get(Wireless.class);
            addConnectionChanges(list, source, WIRELESS_TRUNK, 13);
            list.add(new PropertyChangeEvent(source, "protocol", source.getProtocol(), "WPA->mod"));
            list.add(new PropertyChangeEvent(source, "version", source.getVersion(), 3));
            if (source.getAPoint() != null) {
                list.add(new PropertyChangeEvent(source, "aPoint", source.getAPoint(), CreateUtilities.createWifiRouter(22)));
            }
            if (source.getBPoint(0) != null) {
                list.add(new PropertyChangeEvent(source, "bPoints", source.getBPoint(0), CreateUtilities.createWifiRouter(23)));
            }
            result.put(Wireless.class, list);
        }
        return result;
    }

    public static List<PropertyChangeEvent> getPlanedChanges(Rack source) {
        List<PropertyChangeEvent> result = new ArrayList();
        result.add(new PropertyChangeEvent(source, "location", source.getLocation(), RACK_LOCATION));
        result.add(new PropertyChangeEvent(source, "devices", source.getDevAtSlot(2), null));
        result.add(new PropertyChangeEvent(source, "devices", null, CreateUtilities.createWifiRouter()));
        return result;
    }

    public static Map<Class, List<PropertyChangeEvent>> getNoChangesInNE() {
        Map<Class, List<PropertyChangeEvent>> result = new HashMap<>();
        List<PropertyChangeEvent> list;
        {
            list = new ArrayList();
            result.put(Battery.class, list);
        }{
            list = new ArrayList();
            result.put(Router.class, list);
        }{
            list = new ArrayList();
            result.put(Switch.class, list);
        }{
            list = new ArrayList();
            result.put(WifiRouter.class, list);
        }
        {
            list = new ArrayList();
            result.put(OpticFiber.class, list);
        }{
            list = new ArrayList();
            result.put(TwistedPair.class, list);
        }{
            list = new ArrayList();
            result.put(ThinCoaxial.class, list);
        }{
            list = new ArrayList();
            result.put(Wireless.class, list);
        }
        return result;
    }

    public static List<PropertyChangeEvent> getNoChangesInRack() {
        return new ArrayList();
    }

    private static void addDeviceChanges(List<PropertyChangeEvent> list, Device source, int in) {
        list.add(new PropertyChangeEvent(source, "in", source.getIn(), in));
        list.add(new PropertyChangeEvent(source, "type", source.getType(), TYPE));
        list.add(new PropertyChangeEvent(source, "manufacturer", source.getManufacturer(), MANUFACTURER));
        list.add(new PropertyChangeEvent(source, "model", source.getModel(), MODEL));
        list.add(new PropertyChangeEvent(source, "productionDate", source.getProductionDate(), PRODUCTION_DATE));
    }

    private static void addConnectionChanges(List<PropertyChangeEvent> list, Connection source, Trunk trunk, int serialNumber) {
        list.add(new PropertyChangeEvent(source, "trunk", source.getTrunk(), trunk));
        list.add(new PropertyChangeEvent(source, "serialNumber", source.getSerialNumber(), serialNumber));
        list.add(new PropertyChangeEvent(source, "status", source.getStatus(), Connection.ON_DISASSEMBLING));
    }

}
