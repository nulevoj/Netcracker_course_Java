package com.netcracker.edu.inventory.model.rack.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.InventoryFactoryManager;
import com.netcracker.edu.inventory.exception.DeviceValidationException;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.entity.Battery;
import com.netcracker.edu.inventory.model.device.entity.Router;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.entity.WifiRouter;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.model.rack.RackPrimaryKey;
import com.netcracker.edu.location.Location;
import com.netcracker.edu.location.impl.LocationImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class RackArrayImplTest {

    RackArrayImpl rackSize1;
    RackArrayImpl rackSize3;
    RackArrayImpl rackEmpty;
    RackArrayImpl rackPartlyFilled;
    RackArrayImpl rackFullFilled;

    Battery battery;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @Before
    public void before() throws Exception {
        rackSize1 = new RackArrayImpl(1, Battery.class);
        rackSize3 = new RackArrayImpl(3, Battery.class);
        rackEmpty = new RackArrayImpl(10, Battery.class);
        rackPartlyFilled = new RackArrayImpl(10, Battery.class);
        rackFullFilled = new RackArrayImpl(10, Battery.class);

        battery = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery.setIn(5);

        for (int i = 0; i < 10; i++) {
            rackFullFilled.insertDevToSlot(battery, i);
            if ((i % 2) == 0) {
                rackPartlyFilled.insertDevToSlot(battery, i);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_1argument_sizeNegative() throws Exception {
        Rack rack = new RackArrayImpl(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_1argument_size0() throws Exception {
        Rack rack = new RackArrayImpl(0);
    }

    @Test
    public void constructor_1argument_size1() throws Exception {
        int expSize = 1;

        Rack rack = new RackArrayImpl(1);

        assertEquals(expSize, rack.getSize());
    }

    @Test
    public void constructor_1argument_size3() throws Exception {
        int expSize = 3;

        Rack rack = new RackArrayImpl(3);

        assertEquals(expSize, rack.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_2arguments_sizeNegative() throws Exception {
        Rack rack = new RackArrayImpl(-1, Device.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_2arguments_size0() throws Exception {
        Rack rack = new RackArrayImpl(0, Device.class);
    }

    @Test
    public void constructor_2arguments_size1() throws Exception {
        int expSize = 1;

        Rack rack = new RackArrayImpl(1, Device.class);

        assertEquals(expSize, rack.getSize());
    }

    @Test
    public void constructor_2arguments_size3() throws Exception {
        int expSize = 3;

        Rack rack = new RackArrayImpl(3, Device.class);

        assertEquals(expSize, rack.getSize());
    }

    @Test
    public void constructor_defaultType() throws Exception {
        RackArrayImpl rack = new RackArrayImpl(5);
        rack.insertDevToSlot(battery, 0);
        Device result1 = rack.getDevAtSlot(0);

        Router router = new com.netcracker.edu.inventory.model.device.entity.impl.Router();
        router.setIn(1);
        rack.insertDevToSlot(router, 1);
        Device result2 = rack.getDevAtSlot(1);

        WifiRouter wifiRouter = new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter();
        wifiRouter.setIn(1);
        rack.insertDevToSlot(wifiRouter, 2);
        Device result3 = rack.getDevAtSlot(2);

        Switch aSwitch = new com.netcracker.edu.inventory.model.device.entity.impl.Switch();
        aSwitch.setIn(1);
        rack.insertDevToSlot(aSwitch, 3);
        Device result4 = rack.getDevAtSlot(3);

        assertEquals(battery, result1);
        assertEquals(router, result2);
        assertEquals(wifiRouter, result3);
        assertEquals(aSwitch, result4);
    }

    @Test
    public void constructor_typeDevice() throws Exception {
        RackArrayImpl rack = new RackArrayImpl(5, Device.class);
        rack.insertDevToSlot(battery, 0);
        Device result1 = rack.getDevAtSlot(0);

        Router router = new com.netcracker.edu.inventory.model.device.entity.impl.Router();
        router.setIn(1);
        rack.insertDevToSlot(router, 1);
        Device result2 = rack.getDevAtSlot(1);

        WifiRouter wifiRouter = new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter();
        wifiRouter.setIn(1);
        rack.insertDevToSlot(wifiRouter, 2);
        Device result3 = rack.getDevAtSlot(2);

        Switch aSwitch = new com.netcracker.edu.inventory.model.device.entity.impl.Switch();
        aSwitch.setIn(1);
        rack.insertDevToSlot(aSwitch, 3);
        Device result4 = rack.getDevAtSlot(3);

        assertEquals(battery, result1);
        assertEquals(router, result2);
        assertEquals(wifiRouter, result3);
        assertEquals(aSwitch, result4);
    }

    @Test
    public void constructor_goodType() throws Exception {
        Router router = new com.netcracker.edu.inventory.model.device.entity.impl.Router();
        router.setIn(1);
        WifiRouter wifiRouter = new com.netcracker.edu.inventory.model.device.entity.impl.WifiRouter();
        wifiRouter.setIn(1);
        Switch aSwitch = new com.netcracker.edu.inventory.model.device.entity.impl.Switch();;
        aSwitch.setIn(1);

        RackArrayImpl rack = new RackArrayImpl(5, Router.class);
        rack.insertDevToSlot(router, 0);
        rack.insertDevToSlot(wifiRouter, 1);
        rack.insertDevToSlot(aSwitch, 2);

        Device result1 = rack.getDevAtSlot(0);
        Device result2 = rack.getDevAtSlot(1);
        Device result3 = rack.getDevAtSlot(2);
        assertEquals(router, result1);
        assertEquals(wifiRouter, result2);
        assertEquals(aSwitch, result3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_TypeNull() throws Exception {
        RackArrayImpl rack = new RackArrayImpl(5, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_TypeObject() throws Exception {
        RackArrayImpl rack = new RackArrayImpl(5, Object.class);
    }

    @Test
    public void setGetLocation() throws Exception {
        Location location = new LocationImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa");

        rackPartlyFilled.setLocation(location);
        Location result = rackPartlyFilled.getLocation();

        assertEquals(location, result);
    }

    @Test
    public void getSize1() throws Exception {
        int expResult = 1;

        int result = rackSize1.getSize();

        assertEquals(expResult, result);
    }

    @Test
    public void getSize3() throws Exception {
        int expResult = 3;

        int result = rackSize3.getSize();

        assertEquals(expResult, result);
    }

    @Test
    public void getTypeOfDevices() throws Exception {
        Rack rackDevice = new RackArrayImpl(2, Device.class);
        Rack rackBattery = new RackArrayImpl(2, Battery.class);
        Rack rackRouter = new RackArrayImpl(2, Router.class);
        Rack rackWifiRouter = new RackArrayImpl(2, WifiRouter.class);
        Rack rackSwitch = new RackArrayImpl(2, Switch.class);

        Class result1 = rackDevice.getTypeOfDevices();
        Class result2 = rackBattery.getTypeOfDevices();
        Class result3 = rackRouter.getTypeOfDevices();
        Class result4 = rackWifiRouter.getTypeOfDevices();
        Class result5 = rackSwitch.getTypeOfDevices();

        assertEquals(Device.class, result1);
        assertEquals(Battery.class, result2);
        assertEquals(Router.class, result3);
        assertEquals(WifiRouter.class, result4);
        assertEquals(Switch.class, result5);
    }

    @Test
    public void getFreeSize() throws Exception {
        int rackSize = 10;
        RackArrayImpl rackForWoodpecker = new RackArrayImpl(rackSize, Battery.class);
        for (int i = 0; i < rackSize; i++) {
            rackForWoodpecker.insertDevToSlot(battery, 1);
        }
        int expResult1 = 0;
        int expResult2 = 5;
        int expResult3 = 10;
        int expResult4 = 9;

        int result1 = rackFullFilled.getFreeSize();
        int result2 = rackPartlyFilled.getFreeSize();
        int result3 = rackEmpty.getFreeSize();
        int result4 = rackForWoodpecker.getFreeSize();

        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
    }

    @Test
    public void getDevAtSlot() throws Exception {
        Device expResultDev = battery;

        Device result1 = rackPartlyFilled.getDevAtSlot(0);
        Device result2 = rackPartlyFilled.getDevAtSlot(2);
        Device result3 = rackPartlyFilled.getDevAtSlot(8);
        Device result4 = rackPartlyFilled.getDevAtSlot(9);
        Device result5 = rackEmpty.getDevAtSlot(0);
        Device result6 = rackEmpty.getDevAtSlot(2);
        Device result7 = rackEmpty.getDevAtSlot(8);

        assertEquals(expResultDev, result1);
        assertEquals(expResultDev, result2);
        assertEquals(expResultDev, result3);
        assertNull(result4);
        assertNull(result5);
        assertNull(result6);
        assertNull(result7);

    }

    @Test
    public void getDevAtSlot_IndexOutOfBounds() throws Exception {
        int[] indexesFor1 = new int[] {-5, -1, 1, 5};
        int[] indexesFor3 = new int[] {-5, -1, 3, 5};
        int[][] allIndexes = new int[][] {indexesFor1, indexesFor3};
        Rack[] racks = new Rack[] {rackSize1, rackSize3};
        int count = 0;
        int expCount = indexesFor1.length + indexesFor3.length;

        for (int i = 0; i < racks.length; i++) {
            for (int j = 0; j < allIndexes[i].length; j++) {
                try {
                    racks[i].getDevAtSlot(allIndexes[i][j]);
                } catch (IndexOutOfBoundsException e) {
                    if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                        count++;
                    }
                }
            }
        }

        assertEquals(expCount, count);
    }

    @Test
    public void insertDevToSlot() throws Exception {
        Battery anotherBattery = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        anotherBattery.setIn(5);

        Device expResultDev = anotherBattery;

        boolean result1 = rackPartlyFilled.insertDevToSlot(anotherBattery, 0);
        boolean result2 = rackPartlyFilled.insertDevToSlot(anotherBattery, 2);
        boolean result3 = rackPartlyFilled.insertDevToSlot(anotherBattery, 8);
        boolean result4 = rackEmpty.insertDevToSlot(anotherBattery, 0);
        Device result4a = rackEmpty.getDevAtSlot(0);
        boolean result5 = rackEmpty.insertDevToSlot(anotherBattery, 3);
        Device result5a = rackEmpty.getDevAtSlot(3);
        boolean result6 = rackEmpty.insertDevToSlot(anotherBattery, 9);
        Device result6a = rackEmpty.getDevAtSlot(9);

        assertFalse(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertTrue(result4);
        assertEquals(expResultDev, result4a);
        assertTrue(result5);
        assertEquals(expResultDev, result5a);
        assertTrue(result6);
        assertEquals(expResultDev, result6a);
    }

    @Test
    public void insertDevToSlot_IndexOutOfBounds() throws Exception {
        int[] indexesFor1 = new int[] {-5, -1, 1, 5};
        int[] indexesFor3 = new int[] {-5, -1, 3, 5};
        int[][] allIndexes = new int[][] {indexesFor1, indexesFor3};
        Rack[] racks = new Rack[] {rackSize1, rackSize3};
        int count = 0;
        int expCount = indexesFor1.length + indexesFor3.length;

        for (int i = 0; i < racks.length; i++) {
            for (int j = 0; j < allIndexes[i].length; j++) {
                try {
                    racks[i].insertDevToSlot(battery, allIndexes[i][j]);
                } catch (IndexOutOfBoundsException e) {
                    if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                        count++;
                    }
                }
            }
        }

        assertEquals(expCount, count);
    }

    @Test(expected = DeviceValidationException.class)
    public void insertDevToSlot_DeviceValidation_DeviceNull_ToFilled() throws Exception {
        rackPartlyFilled.insertDevToSlot(null, 4);
    }

    @Test(expected = DeviceValidationException.class)
    public void insertDevToSlot_DeviceValidation_DeviceNull_ToNotFilled() throws Exception {
        rackPartlyFilled.insertDevToSlot(null, 5);
    }

    @Test(expected = DeviceValidationException.class)
    public void insertDevToSlot_DeviceValidation_DeviceWithDefaultIN() throws Exception {
        Battery batteryIN0 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();

        rackEmpty.insertDevToSlot(batteryIN0, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertDevToSlot_badType() throws Exception {
        RackArrayImpl rack = new RackArrayImpl(5, Router.class);

        rack.insertDevToSlot(battery, 0);
    }

    @Test
    public void insertDevToSlot_childDeviceType() throws Exception {
        Battery child = new com.netcracker.edu.inventory.model.device.entity.impl.Battery() {};
        child.setIn(5);

        rackEmpty.insertDevToSlot(child, 7);
    }

    @Test
    public void removeDevFromSlot() throws Exception {
        Device expResultDev = battery;

        Device result1 = rackPartlyFilled.removeDevFromSlot(0);
        Device result1a = rackPartlyFilled.getDevAtSlot(0);
        Device result2 = rackPartlyFilled.removeDevFromSlot(2);
        Device result2a = rackPartlyFilled.getDevAtSlot(2);
        Device result3 = rackPartlyFilled.removeDevFromSlot(8);
        Device result3a = rackPartlyFilled.getDevAtSlot(8);
        Device result4 = rackEmpty.removeDevFromSlot(0);
        Device result5 = rackEmpty.removeDevFromSlot(3);
        Device result6 = rackEmpty.removeDevFromSlot(9);

        assertEquals(expResultDev, result1);
        assertNull(result1a);
        assertEquals(expResultDev, result2);
        assertNull(result2a);
        assertEquals(expResultDev, result3);
        assertNull(result3a);
        assertNull(result4);
        assertNull(result5);
        assertNull(result6);
    }

    @Test
    public void removeDevFromSlot_IndexOutOfBounds() throws Exception {
        int[] indexesFor1 = new int[] {-5, -1, 1, 5};
        int[] indexesFor3 = new int[] {-5, -1, 3, 5};
        int[][] allIndexes = new int[][] {indexesFor1, indexesFor3};
        Rack[] racks = new Rack[] {rackSize1, rackSize3};
        int count = 0;
        int expCount = indexesFor1.length + indexesFor3.length;

        for (int i = 0; i < racks.length; i++) {
            for (int j = 0; j < allIndexes[i].length; j++) {
                try {
                    racks[i].removeDevFromSlot(allIndexes[i][j]);
                } catch (IndexOutOfBoundsException e) {
                    if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                        count++;
                    }
                }
            }
        }

        assertEquals(expCount, count);
    }

    @Test
    public void getDevByIN() throws Exception {
        RackArrayImpl rack1Full = new RackArrayImpl(1, Battery.class);
        RackArrayImpl rack1Empty = new RackArrayImpl(1, Battery.class);
        RackArrayImpl rackEmpty = new RackArrayImpl(5, Battery.class);
        RackArrayImpl rackPartly = new RackArrayImpl(6, Battery.class);
        RackArrayImpl rackFull = new RackArrayImpl(5, Battery.class);
        Battery battery1 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery1.setIn(1);
        Battery battery2 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery2.setIn(2);
        Battery battery3 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery3.setIn(3);
        Battery battery4 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery4.setIn(4);
        Device[] devicesForRackPartly = new Device[] {battery1, battery4, battery2, battery4, null, battery3};
        Device[] devicesForRackFull = new Device[] {battery3, battery1, battery2, battery3, battery4};
        rack1Full.insertDevToSlot(battery1, 0);
        int counter = 0;
        for (Device device: devicesForRackPartly ) {
            if (device != null) {
                rackPartly.insertDevToSlot(device, counter++);
            }
        }
        counter = 0;
        for (Device device: devicesForRackFull ) {
            rackFull.insertDevToSlot(device, counter++);
        }

        Device expResultDev1 = battery1;
        Device expResultDev2 = battery2;
        Device expResultDev3 = battery3;
        Device expResultDev4 = battery4;

        Device result2 = rack1Full.getDevByIN(16);
        Device result3 = rack1Full.getDevByIN(1);
        Device result4 = rack1Empty.getDevByIN(16);
        Device result5 = rackEmpty.getDevByIN(16);
        Device result6 = rackPartly.getDevByIN(16);
        Device result7 = rackPartly.getDevByIN(1);
        Device result8 = rackPartly.getDevByIN(2);
        Device result9 = rackPartly.getDevByIN(3);
        Device result10 = rackPartly.getDevByIN(4);
        Device result11 = rackFull.getDevByIN(16);
        Device result12 = rackFull.getDevByIN(1);
        Device result13 = rackFull.getDevByIN(2);

        assertNull(result2);
        assertEquals(expResultDev1, result3);
        assertNull(result4);
        assertNull(result5);
        assertNull(result6);
        assertEquals(expResultDev1, result7);
        assertEquals(expResultDev2, result8);
        assertEquals(expResultDev3, result9);
        assertEquals(expResultDev4, result10);
        assertNull(result11);
        assertEquals(expResultDev1, result12);
        assertEquals(expResultDev2, result13);
    }

    @Test
    public void getAllDeviceAsArray() throws Exception {
        RackArrayImpl rackPartly = new RackArrayImpl(6, Battery.class);
        Battery battery1 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery1.setIn(1);
        Battery battery2 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery2.setIn(2);
        Battery battery3 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery3.setIn(3);
        Battery battery4 = new com.netcracker.edu.inventory.model.device.entity.impl.Battery();
        battery4.setIn(4);
        Device[] devicesForRackPartly = new Device[] {battery1, battery4, battery2, battery4, null, battery3};
        int counter = 0;
        for (Device device: devicesForRackPartly ) {
            if (device != null) {
                rackPartly.insertDevToSlot(device, counter);
            }
            counter++;
        }
        Device[] expResult = new Device[] {battery1, battery2, battery3, battery4, battery4};
        int expSize = 5;

        Device[] result = rackPartly.getAllDeviceAsArray();

        InventoryFactoryManager.getServiceFactory().createDeviceServiceImpl().sortByIN(result);
        assertEquals(expSize, result.length);
        assertArrayEquals(expResult, result);
    }



    @Test
    public void isLazy() throws Exception {
        assertFalse(rackPartlyFilled.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        rackPartlyFilled.setLocation(new LocationImpl("Test", "test"));
        RackPrimaryKey expRackPK = new RackPK(rackPartlyFilled.getLocation());

        Unique.PrimaryKey primaryKey = rackPartlyFilled.getPrimaryKey();

        AssertUtilities.assertSomePK(expRackPK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = rackPartlyFilled.getPrimaryKey();

        assertNull(primaryKey);
    }
}