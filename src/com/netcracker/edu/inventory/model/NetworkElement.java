package com.netcracker.edu.inventory.model;

/**
 * Created by makovetskyi on 13.01.17.
 */
public interface NetworkElement<T, K extends Unique.PrimaryKey> extends FillableEntity, Unique<K>, Comparable<T> {
}
