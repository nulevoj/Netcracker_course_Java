package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.NetworkElement;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.inventory.service.ConcurrentService;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.Future;

public class ConcurrentServiceImpl implements ConcurrentService {

    ConcurrentIOService cioService = new ConcurrentIOService();

    @Override
    public Future parallelOutputElements(Collection<NetworkElement> elements, OutputStream outputStream) {
        return cioService.parallelOutputElements(elements, outputStream);
    }

    @Override
    public Future<Collection<NetworkElement>> parallelInputElements(int number, InputStream inputStream) {
        return cioService.parallelInputElements(number, inputStream);
    }

    @Override
    public Future parallelOutputRacks(Collection<Rack> racks, OutputStream outputStream) {
        return cioService.parallelOutputRacks(racks, outputStream);
    }

    @Override
    public Future<Collection<Rack>> parallelInputRacks(int number, InputStream inputStream) {
        return cioService.parallelInputRacks(number, inputStream);
    }

}
