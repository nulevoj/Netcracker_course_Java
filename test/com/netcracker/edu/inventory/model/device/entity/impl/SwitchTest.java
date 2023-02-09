package com.netcracker.edu.inventory.model.device.entity.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;
import com.netcracker.edu.inventory.model.device.entity.Switch;
import com.netcracker.edu.inventory.model.device.impl.DevicePK;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class SwitchTest {

    DeviceMethodsTestSupplier deviceMethodsTestSupplier;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    Switch defaultSwitch;
    Switch aSwitch;
    Switch switchWithConnections;

    int numberOfPorts = 0;

    @Before
    public void before() throws Exception {
        deviceMethodsTestSupplier = new DeviceMethodsTestSupplier();
        defaultSwitch = new com.netcracker.edu.inventory.model.device.entity.impl.Switch();
        aSwitch = CreateUtilities.createSwitch();
        switchWithConnections = CreateUtilities.createSwitchWithConnections();
    }

    @Test
    public void setGetIn() throws Exception {
        deviceMethodsTestSupplier.setGetIn(defaultSwitch);
    }

    @Test
    public void setIn_PrevNot0_newNot0() throws Exception {
        deviceMethodsTestSupplier.setIn_PrevNot0_newNot0(defaultSwitch);
    }

    @Test
    public void setIn_PrevNot0_new0() throws Exception {
        deviceMethodsTestSupplier.setIn_PrevNot0_new0(defaultSwitch);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIn_newLess0() throws Exception {
        deviceMethodsTestSupplier.setIn_newLess0(defaultSwitch);
    }

    @Test
    public void setGetType() throws Exception {
        deviceMethodsTestSupplier.setGetType(defaultSwitch);
    }

    @Test
    public void setGetManufacturer() throws Exception {
        deviceMethodsTestSupplier.setGetManufacturer(defaultSwitch);
    }

    @Test
    public void setGetModel() throws Exception {
        deviceMethodsTestSupplier.setGetModel(defaultSwitch);
    }

    @Test
    public void setGetProductionDate() throws Exception {
        deviceMethodsTestSupplier.setGetProductionDate(defaultSwitch);
    }

    @Test
    public void compareTo() throws Exception {
        deviceMethodsTestSupplier.compareTo(
                new com.netcracker.edu.inventory.model.device.entity.impl.Switch(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Switch(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Switch(),
                new com.netcracker.edu.inventory.model.device.entity.impl.Switch());
    }

    @Test
    public void getPortsType() throws Exception {
        assertEquals(ConnectorType.need_init, defaultSwitch.getPortsType());
        assertEquals(ConnectorType.RJ45, aSwitch.getPortsType());
    }

    @Test
    public void setGetNumberOfPorts() throws Exception {
        defaultSwitch.setNumberOfPorts(numberOfPorts);
        int result = defaultSwitch.getNumberOfPorts();

        assertEquals(numberOfPorts, result);
    }

    @Test
    public void setGetPortConnection() throws Exception {
        TwistedPair twistedPair = CreateUtilities.createTwistedPair();
        twistedPair.setLength(10);

        aSwitch.setPortConnection(twistedPair, 1);
        TwistedPair result = (TwistedPair) aSwitch.getPortConnection(1);

        AssertUtilities.assertTwistedPair(twistedPair, result);
    }

    @Test
    public void setGetPortConnection_IndexOutOfBounds() throws Exception {
        int[] indexesFor1 = new int[] {-20, -1, 0, 20};
        int[] indexesFor3 = new int[] {-20, -1, 16, 20};
        int[][] allIndexes = new int[][] {indexesFor1, indexesFor3};
        Switch[] switches = new Switch[] {defaultSwitch, aSwitch};
        TwistedPair twistedPair = CreateUtilities.createTwistedPair();
        int setCount = 0;
        int getCount = 0;
        int expCount = indexesFor1.length + indexesFor3.length;

        for (int i = 0; i < switches.length; i++) {
            for (int j = 0; j < allIndexes[i].length; j++) {
                try {
                    switches[i].setPortConnection(twistedPair, allIndexes[i][j]);
                } catch (IndexOutOfBoundsException e) {
                    if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                        setCount++;
                    }
                }
                try {
                    switches[i].getPortConnection(allIndexes[i][j]);
                } catch (IndexOutOfBoundsException e) {
                    if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                        getCount++;
                    }
                }
            }
        }

        assertEquals(expCount, setCount);
        assertEquals(expCount, getCount);
    }

    @Test
    public void defaultAllPortConnections() throws Exception {
        List<Connection> result = defaultSwitch.getAllPortConnections();

        assertEquals(new ArrayList<Connection>(), result);
    }

    @Test
    public void getAllPortConnections() throws Exception {
        List<Connection> expList = new ArrayList<>(6);
        TwistedPair twistedPair1 = CreateUtilities.createTwistedPair();
        TwistedPair twistedPair2 = CreateUtilities.createTwistedPair();
        TwistedPair twistedPair3 = CreateUtilities.createTwistedPair();
        expList.add(twistedPair1);
        expList.add(null);
        expList.add(null);
        expList.add(twistedPair2);
        expList.add(null);
        expList.add(twistedPair3);
        aSwitch.setNumberOfPorts(6);
        aSwitch.setPortConnection(twistedPair1, 0);
        aSwitch.setPortConnection(twistedPair2, 3);
        aSwitch.setPortConnection(twistedPair1, 0);
        aSwitch.setPortConnection(twistedPair3, 5);

        List<Connection> result = aSwitch.getAllPortConnections();

        assertEquals(expList, result);
    }

    @Test
    public void testGetAndFillAllFields() throws Exception {
        Switch result = defaultSwitch;
        result.fillAllFields(switchWithConnections.getAllFields());

        AssertUtilities.assertSwitch(switchWithConnections, result);
    }

    @Test
    public void testGetAndFillAllFields_EmptyDevice() throws Exception {
        Switch result = new com.netcracker.edu.inventory.model.device.entity.impl.Switch();
        result.fillAllFields(defaultSwitch.getAllFields());

        AssertUtilities.assertSwitch(defaultSwitch, result);
    }

    @Test
    public void isLazy() throws Exception {
        assertFalse(defaultSwitch.isLazy());
    }

    @Test
    public void getPrimaryKey() throws Exception {
        DevicePrimaryKey expDevicePK = new DevicePK(aSwitch.getIn());

        Unique.PrimaryKey primaryKey = aSwitch.getPrimaryKey();

        AssertUtilities.assertSomePK(expDevicePK, primaryKey);
    }

    @Test
    public void getPrimaryKey_PK_NULL() throws Exception {
        Unique.PrimaryKey primaryKey = defaultSwitch.getPrimaryKey();

        assertNull(primaryKey);
    }

}