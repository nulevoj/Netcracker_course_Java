package com.netcracker.edu.inventory.model;

/**
 * Created by makovetskyi on 16.11.2016.
 */
public interface Unique<K extends Unique.PrimaryKey> {

    boolean isLazy();

    K getPrimaryKey();

    interface PrimaryKey {
    }

}
