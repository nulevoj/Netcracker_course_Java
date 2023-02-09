package com.netcracker.edu.inventory.model.device.impl;

import com.netcracker.edu.inventory.model.device.DevicePrimaryKey;

public class DevicePK implements DevicePrimaryKey {

    protected final int in;

    public DevicePK(int in) {
        this.in = in;
    }

    @Override
    public int getIn() {
        return in;
    }

    @Override
    public int hashCode() {
        return 31 * in;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DevicePK) {
            return (this.getIn() == ((DevicePK) obj).getIn());
        }
        return false;
    }

    @Override
    public int compareTo(DevicePrimaryKey o) {
        return Integer.compare(this.getIn(), o.getIn());
    }

}
