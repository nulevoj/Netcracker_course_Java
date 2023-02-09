package com.netcracker.edu.inventory.model.device;

import com.netcracker.edu.inventory.model.Unique;

/**
 * Created by makovetskyi on 16.11.2016.
 */
public interface DevicePrimaryKey extends Unique.PrimaryKey, Comparable<DevicePrimaryKey>  {

    int getIn();

}
