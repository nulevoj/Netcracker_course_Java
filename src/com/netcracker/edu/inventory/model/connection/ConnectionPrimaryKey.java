package com.netcracker.edu.inventory.model.connection;

import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.location.Trunk;

/**
 * Created by makovetskyi on 16.11.2016.
 */
public interface ConnectionPrimaryKey extends Unique.PrimaryKey, Comparable<ConnectionPrimaryKey> {

    Trunk getTrunk();

    int getSerialNumber();

}
