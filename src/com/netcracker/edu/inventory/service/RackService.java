package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.rack.Rack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**The interface RackService describe list of services of Inventory component, witch working with Rack
 *
 * Created by makovetskyi on 05.10.2016.
 */
public interface RackService {

    /**
     * Method check validity of rack for output to byte output stream
     *
     * @param rack - validated entity
     * @return true - if rack is valid
     *         false - if rack is not valid
     */
    boolean isValidRackForOutputToStream(Rack rack);

    /**
     * Write Rack instance in to binary stream
     *
     * @param rack - source Rack
     * @param outputStream - targeted binary stream
     */
    void outputRack(Rack rack, OutputStream outputStream) throws IOException;

    /**
     * Read Rack instance from binary stream
     *
     * @param inputStream - source binary stream
     * @return - received Rack instance
     */
    Rack inputRack(InputStream inputStream) throws IOException, ClassNotFoundException;

}
