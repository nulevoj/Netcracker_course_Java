package com.netcracker.edu.inventory.model.connection.impl;

import com.netcracker.edu.inventory.model.connection.ConnectionPrimaryKey;
import com.netcracker.edu.location.Trunk;

public class ConnectionPK implements ConnectionPrimaryKey {

    protected final int serialNumber;
    protected final Trunk trunk;

    public ConnectionPK(Trunk trunk, int serialNumber) {
        this.serialNumber = serialNumber;
        this.trunk = trunk;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public Trunk getTrunk() {
        return trunk;
    }

    @Override
    public int hashCode() {
        return (31 * serialNumber) + trunk.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ConnectionPK) {
            if (this.serialNumber != ((ConnectionPK) obj).serialNumber) {
                return false;
            }
            if (!this.trunk.equals(((ConnectionPK) obj).getTrunk())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(ConnectionPrimaryKey o) {
        return Integer.compare(this.getSerialNumber(), o.getSerialNumber());
    }

}
