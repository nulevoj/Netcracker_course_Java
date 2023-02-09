package com.netcracker.edu.inventory.model.device.entity.impl;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Battery extends AbstractDevice implements com.netcracker.edu.inventory.model.device.entity.Battery {

    private static final Logger log = Logger.getLogger(Battery.class.getName());

    protected int chargeVolume;

    @Override
    public int getChargeVolume() {
        return chargeVolume;
    }

    @Override
    public void setChargeVolume(int chargeVolume) {
        this.chargeVolume = chargeVolume;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setChargeVolume((int) fields.remove().getValue());
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(Integer.class, getChargeVolume()));
        return fields;
    }

}
