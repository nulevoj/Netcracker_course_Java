package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.service.EntityFactory;
import com.netcracker.edu.inventory.service.ServiceFactory;
import com.netcracker.edu.inventory.service.impl.EntityFactoryImpl;
import com.netcracker.edu.inventory.service.impl.ServiceFactoryImpl;

/**
 * Created by makovetskyi on 14.11.17.
 */
public class InventoryFactoryManager {

    private static ServiceFactory serviceFactory;
    private static EntityFactory entityFactory;

    public static ServiceFactory getServiceFactory() {
        return serviceFactory == null ? serviceFactory = new ServiceFactoryImpl() : serviceFactory;
    }

    public static void setServiceFactory(ServiceFactory factory) {
        serviceFactory = factory;
    }

    public static EntityFactory getEntityFactory() {
        return entityFactory == null ? entityFactory = new EntityFactoryImpl() : entityFactory;
    }

    public static void setEntityFactory(EntityFactory factory) {
        entityFactory = factory;
    }

}
