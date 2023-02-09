package com.netcracker.edu.inventory.model.rack;

import com.netcracker.edu.inventory.model.Unique;
import com.netcracker.edu.location.Location;

/**
 * Created by makovetskyi on 16.11.2016.
 */
public interface RackPrimaryKey extends Unique.PrimaryKey {

    Location getLocation();

}
