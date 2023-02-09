package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.NetworkElement;
import com.netcracker.edu.inventory.model.rack.Rack;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.Future;

/**
 * Created by makovetskyi on 29.11.2016.
 */
public interface ConcurrentService {

    Future parallelOutputElements(Collection<NetworkElement> elements, OutputStream outputStream);

    Future<Collection<NetworkElement>> parallelInputElements(int number, InputStream inputStream);

    Future parallelOutputRacks(Collection<Rack> racks, OutputStream outputStream);

    Future<Collection<Rack>> parallelInputRacks(int number, InputStream inputStream);

}
