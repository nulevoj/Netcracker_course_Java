package com.netcracker.edu.inventory.exception;

import com.netcracker.edu.inventory.LoggerInitializer;
import com.netcracker.edu.inventory.model.connection.Connection;
import com.netcracker.edu.inventory.model.connection.entity.impl.TwistedPair;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by makovetskyi on 24.10.17.
 */
public class ConnectionValidationExceptionTest {

    protected static final String DEFAULT_MESSAGE = "Connection is not valid for operation";
    protected static final String OPERATION = "insert";

    ConnectionValidationException e;

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
        e = new ConnectionValidationException();

        assertNull(e.getMessage());
    }

    @Test
    public void constructor_oneArgument_null() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE;

        e = new ConnectionValidationException(null);

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void constructor_oneArgument_empty() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE + " ";

        e = new ConnectionValidationException("");

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void constructor_oneArgument_notEmpty() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE + " " + OPERATION;

        e = new ConnectionValidationException(OPERATION);

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void constructor_twoArguments_nulls() throws Exception {

        String expectedMessage = DEFAULT_MESSAGE;

        e = new ConnectionValidationException(null, null);
        Connection resultConnection = e.getConnection();

        assertEquals(expectedMessage, e.getMessage());
        assertNull(resultConnection);
    }

    @Test
    public void constructor_twoArguments_emptyOperation() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE + " ";

        e = new ConnectionValidationException("", null);
        Connection resultConnection = e.getConnection();

        assertEquals(expectedMessage, e.getMessage());
        assertNull(resultConnection);
    }

    @Test
    public void constructor_oneArgument_notEmptyOperation() throws Exception {
        String expectedMessage = DEFAULT_MESSAGE + " " + OPERATION;

        e = new ConnectionValidationException(OPERATION, null);
        Connection resultConnection = e.getConnection();

        assertEquals(expectedMessage, e.getMessage());
        assertNull(resultConnection);
    }

    @Test
    public void getDevice() throws Exception {
        Connection connection = new TwistedPair();
        e = new ConnectionValidationException(OPERATION, connection);
        String expectedMessage = DEFAULT_MESSAGE + " " + OPERATION;
        Connection expectedConnection = connection;

        Connection resultConnection = e.getConnection();

        assertEquals(expectedMessage, e.getMessage());
        assertEquals(expectedConnection, resultConnection);

    }

}