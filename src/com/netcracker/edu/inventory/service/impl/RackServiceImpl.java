package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.io.impl.IOServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class RackServiceImpl implements RackService {

    private static final IOServiceImpl ioService = new IOServiceImpl();

    @Override
    public boolean isValidRackForOutputToStream(Rack rack) {
        return ioService.isValidRackForOutputToStream(rack);
    }

    @Override
    public void outputRack(Rack rack, OutputStream outputStream) throws IOException {
        ioService.outputRack(rack, outputStream);
    }

    @Override
    public Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException {
        return ioService.inputRack(inputStream);
    }

}
