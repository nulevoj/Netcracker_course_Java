package com.netcracker.edu.inventory.model.device.entity.impl;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Router extends AbstractDevice implements com.netcracker.edu.inventory.model.device.entity.Router {

    private static final Logger log = Logger.getLogger(Router.class.getName());

    protected int dataRate;

    @Override
    public int getDataRate() {
        return dataRate;
    }

    @Override
    public void setDataRate(int dataRate) {
        this.dataRate = dataRate;
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setDataRate((int) fields.remove().getValue());
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(Integer.class, getDataRate()));
        return fields;
    }

}
