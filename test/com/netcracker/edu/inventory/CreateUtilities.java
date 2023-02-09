package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.entity.OpticFiber;
import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.connection.entity.Wireless;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.rack.impl.RackArrayImpl;
import com.netcracker.edu.location.impl.LocationImpl;
import com.netcracker.edu.location.impl.TrunkImpl;

import java.util.Date;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class CreateUtilities {

    public static Battery createBattery() {
        Battery battery = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery.setIn(4);
        battery.setManufacturer("");
        battery.setModel("Smart-UPS 750 ВА 230 В U2");
        battery.setProductionDate(new Date(1));
        battery.setChargeVolume(5000);
        return battery;
    }

    public static Router createRouter() {
        Router router = new com.netcracker.edu.inventory.model.device.entity.impl.Router();
        router.setIn(2);
        router.setManufacturer("Cisco");
        router.setDataRate(1000);
        return router;
    }

    public static Switch createSwitch() {
        return createSwitch(7);
    }

    public static Switch createSwitch(int in) {
        Switch aSwitch = new com.netcracker.edu.inventory.model.device.entity.impl.Switch(ConnectorType.RJ45);
        aSwitch.setIn(in);
        aSwitch.setModel("null");
        aSwitch.setManufacturer("D-Link");
        aSwitch.setDataRate(1000000);
        aSwitch.setNumberOfPorts(16);
        return aSwitch;
    }

    public static WifiRouter createWifiRouter() {
        return createWifiRouter(5);
    }

    public static WifiRouter createWifiRouter(int in) {
        WifiRouter wifiRouter = new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter("802.11g", ConnectorType.RJ45);
        wifiRouter.setIn(in);
        wifiRouter.setModel(null);
        wifiRouter.setManufacturer("D-link");
        wifiRouter.setSecurityProtocol(" ");
        return wifiRouter;
    }

    public static Switch createSwitchWithConnections() {
        Switch aSwitch = createSwitch();
        aSwitch.setPortConnection(createTwistedPair(), 4);
        return aSwitch;
    }

    public static WifiRouter createWifiRouterWithConnections() {
        WifiRouter wifiRouter = createWifiRouter();
        wifiRouter.setWireConnection(createOpticFiber());
        wifiRouter.setWirelessConnection(createWireless());
        return wifiRouter;
    }

    public static TwistedPair createTwistedPair() {
        return createTwistedPair(4);
    }

    public static TwistedPair createTwistedPair(int serialNumber) {
        TwistedPair twistedPair = new com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair(TwistedPair.Type.UTP, 100);
        twistedPair.setTrunk(new TrunkImpl("ua.od.onpu.ics.607.east_wall-608.west_wall", "NC TC Internet to 608"));
        twistedPair.setSerialNumber(serialNumber);
        twistedPair.setStatus(Connection.READY);
        return twistedPair;
    }

    public static OpticFiber createOpticFiber() {
        OpticFiber opticFiber = new com.netcracker.edu.inventory.model.connection.entity.impl.OpticFiber(OpticFiber.Mode.single, 1000);
        opticFiber.setStatus(Connection.ON_BUILD);
        opticFiber.setSerialNumber(5);
        opticFiber.setTrunk(new TrunkImpl("Test", "test"));
        return opticFiber;
    }

    public static Wireless createWireless() {
        return createWireless(3);
    }

    public static Wireless createWireless(int serialNumber) {
        Wireless wireless = new com.netcracker.edu.inventory.model.connection.entity.impl.Wireless(3, "802.11g");
        wireless.setSerialNumber(serialNumber);
        wireless.setTrunk(new TrunkImpl("ua.od.onpu.ics.607.east_wall-area", "NC TC Wifi"));
        wireless.setStatus(Connection.USED);
        wireless.setProtocol("WPA");
        wireless.setVersion(2);
        return wireless;
    }

    public static ThinCoaxial createThinCoaxial() {
        return createThinCoaxial(5);
    }

    public static ThinCoaxial createThinCoaxial(int serialNumber) {
        ThinCoaxial thinCoaxial = new com.netcracker.edu.inventory.model.connection.entity.impl.ThinCoaxial(5);
        thinCoaxial.setStatus(Connection.USED);
        thinCoaxial.setSerialNumber(serialNumber);
        thinCoaxial.setTrunk(new TrunkImpl("Test2", "test2"));
        return thinCoaxial;
    }

    public static TwistedPair createTwistedPairWithDevices() {
        TwistedPair twistedPair = createTwistedPair();
        twistedPair.setAPoint(createRouter());
        return twistedPair;
    }

    public static OpticFiber createOpticFiberWithDevices() {
        OpticFiber opticFiber = createOpticFiber();
        opticFiber.setBPoint(createWifiRouter());
        return opticFiber;
    }

    public static Wireless createWirelessWithDevices() {
        Wireless wireless = createWireless();
        wireless.setAPoint(createWifiRouter());
        wireless.setBPoint(createWifiRouter(), 0);
        wireless.setBPoint(createWifiRouter(), 2);
        return wireless;
    }

    public static ThinCoaxial createThinCoaxialWithDevices() {
        ThinCoaxial thinCoaxial = createThinCoaxial();
        thinCoaxial.addDevice(createRouter());
        thinCoaxial.addDevice(createSwitch());
        return thinCoaxial;
    }

    public static Rack createRack() {
        Rack<Router> partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);
        return partlyRack;
    }

}