package com.netcracker.edu.inventory.exception;

import com.netcracker.edu.inventory.model.device.Device;

public class DeviceValidationException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Device is not valid for operation";

    protected Device device;

    public DeviceValidationException() {
        super();
    }

    public DeviceValidationException(String operation) {
        super(DEFAULT_MESSAGE + (operation == null ? "" : " " + operation));
    }

    public DeviceValidationException(String operation, Device device) {
        this(operation);
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

}
