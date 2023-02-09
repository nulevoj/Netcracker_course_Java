package com.netcracker.edu.inventory.model.connection.entity.impl;

import com.netcracker.edu.inventory.model.connection.ConnectorType;
import com.netcracker.edu.inventory.model.device.Device;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpticFiber<A extends Device, B extends Device>
        extends AbstractOneToOneConnection<A, B>
        implements com.netcracker.edu.inventory.model.connection.entity.OpticFiber<A, B> {

    private static final Logger log = Logger.getLogger(OpticFiber.class.getName());

    protected int length;
    protected Mode mode = Mode.need_init;

    public OpticFiber() {
        APointConnectorType = ConnectorType.FiberConnector_FC;
        BPointConnectorType = ConnectorType.FiberConnector_FC;
    }

    public OpticFiber(Mode mode, int length) {
        this();
        this.mode = mode;
        this.length = length;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public Mode getMode() {
        return mode;
    }

    protected void setMode(Mode mode) {
        if (this.mode == Mode.need_init) {
            this.mode = mode;
            return;
        }
        log.warning("Can not change not default mode");
    }

    @Override
    public void fillAllFields(Queue<Field> fields) {
        super.fillAllFields(fields);
        try {
            setLength((int) fields.remove().getValue());
            setMode((Mode.valueOf((String) fields.remove().getValue())));
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Not enough elements in Queue", e);
            throw e;
        }
    }

    @Override
    public Queue<Field> getAllFields() {
        Queue<Field> fields = super.getAllFields();
        fields.add(new Field(Integer.class, getLength()));
        fields.add(new Field(String.class, getMode().toString()));
        return fields;
    }

}
