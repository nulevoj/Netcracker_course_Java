package com.netcracker.edu.inventory;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Created by makovetskyi on 05.10.17.
 */
public class LoggerInitializer {

    public static void initLogger() {
        try {
            LogManager.getLogManager().readConfiguration(
                    InventoryFactoryManager.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
    }

}
