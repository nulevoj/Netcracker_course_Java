package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.connection.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by makovetskyi on 11.11.16.
 */
public interface ConnectionService {

    /**
     * Method check validity of Connection for output to byte output stream
     *
     * @param connection - validated Connection
     * @return true - if Connection is valid
     *         false - if Connection is not valid
     */
    boolean isValidConnectionForOutputToStream(Connection connection);

    /**
     * Write Connection instance in to binary stream
     *
     * @param connection - source Connection
     * @param outputStream - targeted binary stream
     */
    void outputConnection(Connection connection, OutputStream outputStream) throws IOException;

    /**
     * Read Connection instance from binary stream
     *
     * @param inputStream - source binary stream
     * @return - received Connection instance
     */
    Connection inputConnection(InputStream inputStream) throws IOException, ClassNotFoundException;

}
