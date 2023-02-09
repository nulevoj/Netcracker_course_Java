package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.NetworkElement;
import com.netcracker.edu.inventory.model.rack.Rack;
import com.netcracker.edu.io.impl.IOServiceImpl;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConcurrentIOService {

    private static final Logger log = Logger.getLogger(ConcurrentIOService.class.getName());
    private static final IOServiceImpl ioService = new IOServiceImpl();

    public Future parallelOutputElements(Collection<NetworkElement> elements, OutputStream outputStream) {
        Future result;
        ExecutorService service = Executors.newCachedThreadPool();
        result = service.submit(
                () -> {
                    try {
                        for (NetworkElement element : elements) {
                            ioService.outputFillableEntity(element, outputStream);
                        }
                    } catch (IOException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                }
        );
        return result;
    }

    public Future<Collection<NetworkElement>> parallelInputElements(int number, InputStream inputStream) {
        Future<Collection<NetworkElement>> result;
        ExecutorService service = Executors.newFixedThreadPool(number);
        Collection<NetworkElement> elements = new ArrayList<>();
        result = service.submit(
                () -> {
                    try {
                        for (int i = 0; i < number; i++) {
                            elements.add((NetworkElement) ioService.inputFillableEntity(inputStream));
                        }
                    } catch (EOFException e) {
                        log.log(Level.WARNING, "Not enough elements in inputStream", e);
                    } catch (IOException | ClassNotFoundException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                    return elements;
                }
        );
        return result;
    }

    public Future parallelOutputRacks(Collection<Rack> racks, OutputStream outputStream) {
        Future result;
        ExecutorService service = Executors.newCachedThreadPool();
        result = service.submit(
                () -> {
                    try {
                        for (Rack rack : racks) {
                            ioService.outputRack(rack, outputStream);
                        }
                    } catch (IOException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                }
        );
        return result;
    }

    public Future<Collection<Rack>> parallelInputRacks(int number, InputStream inputStream) {
        Future<Collection<Rack>> result;
        ExecutorService service = Executors.newFixedThreadPool(number);
        Collection<Rack> elements = new ArrayList<>();
        result = service.submit(
                () -> {
                    try {
                        for (int i = 0; i < number; i++) {
                            elements.add(ioService.inputRack(inputStream));
                        }
                    } catch (EOFException e) {
                        log.log(Level.WARNING, "Not enough elements in inputStream", e);
                    } catch (IOException | ClassNotFoundException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                    return elements;
                }
        );
        return result;
    }

}
