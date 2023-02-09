package com.netcracker.edu.inventory.exception;

import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.device.Device;
import com.netcracker.edu.inventory.model.device.entity.impl.Router;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.17.
 */
public class DeviceValidationExceptionTest {

    protected static final String DEFAULT_MESSAGE = "Device is not valid for operation";
    protected static final String OPERATION = "insert";

    DeviceValidationException e;

    @BeforeClass
    public static void initEnvironment() {
        LoggerInitializer.initLogger();
    }

    @After
    public void tearDown() throws Exception {
        e = null;
    }

    @Test
    public void constructor_default() throws Exception {
        e = new DeviceValidationException();

        assertNull(e.getMessage());
    }

    @Test
    public void constructor_oneArgument_null() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE;

        e = new DeviceValidationException(null);

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void constructor_oneArgument_empty() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE + " ";

        e = new DeviceValidationException("");

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void constructor_oneArgument_notEmpty() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE + " " + OPERATION;

        e = new DeviceValidationException(OPERATION);

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void constructor_twoArguments_nulls() throws Exception {

        String expectedMessage = DEFAULT_MESSAGE;

        e = new DeviceValidationException(null, null);
        Device resultDevice = e.getDevice();

        assertEquals(expectedMessage, e.getMessage());
        assertNull(resultDevice);
    }

    @Test
    public void constructor_twoArguments_emptyOperation() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE + " ";

        e = new DeviceValidationException("", null);
        Device resultDevice = e.getDevice();

        assertEquals(expectedMessage, e.getMessage());
        assertNull(resultDevice);
    }

    @Test
    public void constructor_oneArgument_notEmptyOperation() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE + " " + OPERATION;

        e = new DeviceValidationException(OPERATION, null);
        Device resultDevice = e.getDevice();

        assertEquals(expectedMessage, e.getMessage());
        assertNull(resultDevice);
    }

    @Test
    public void getDevice() throws Exception {
        Device device = new Router();
        e = new DeviceValidationException(OPERATION, device);
        String expectedMessage = DEFAULT_MESSAGE + " " + OPERATION;
        Device expectedDevice = device;

        Device resultDevice = e.getDevice();

        assertEquals(expectedMessage, e.getMessage());
        assertEquals(expectedDevice, resultDevice);

    }

}