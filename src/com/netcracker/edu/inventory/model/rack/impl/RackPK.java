package com.netcracker.edu.inventory.model.rack.impl;

import com.netcracker.edu.inventory.model.rack.RackPrimaryKey;
import com.netcracker.edu.location.Location;

public class RackPK implements RackPrimaryKey {

    protected final Location location;

    public RackPK(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RackPK) {
            return (this.getLocation().equals(((RackPK) obj).getLocation()));
        }
        return false;
    }

}
