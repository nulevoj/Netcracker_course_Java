package com.netcracker.edu.inventory.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonUtility {

    private static final Logger log = Logger.getLogger(CommonUtility.class.getName());

    public static void checkIndexOutOfRange(int index, int length) {
        if (index < 0 || index >= length) {
            IndexOutOfBoundsException e = new IndexOutOfBoundsException(String.format
                    ("Your index was %d. Correct bound range: 0 - %d", index, length));
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    public static void checkObjectIsNull(Object object, String message) throws IllegalArgumentException {
        if (object == null) {
            IllegalArgumentException e = new IllegalArgumentException(message);
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    public static void checkClazzIsNull(Class clazz) {
        checkObjectIsNull(clazz, "clazz can not be null");
    }

}
