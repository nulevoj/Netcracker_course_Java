package com.netcracker.edu.inventory.exception;

import com.netcracker.edu.inventory.model.connection.Connection;

public class ConnectionValidationException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Connection is not valid for operation";

    protected Connection connection;

    public ConnectionValidationException() {
        super();
    }

    public ConnectionValidationException(String operation) {
        super(DEFAULT_MESSAGE + (operation == null ? "" : " " + operation));
    }

    public ConnectionValidationException(String operation, Connection connection) {
        this(operation);
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

}
