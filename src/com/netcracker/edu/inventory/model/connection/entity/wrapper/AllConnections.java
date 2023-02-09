package com.netcracker.edu.inventory.model.connection.entity.wrapper;

import com.netcracker.edu.inventory.model.connection.*;
import com.netcracker.edu.inventory.model.connection.entity.OpticFiber;
import com.netcracker.edu.inventory.model.connection.entity.ThinCoaxial;
import com.netcracker.edu.inventory.model.connection.entity.TwistedPair;
import com.netcracker.edu.inventory.model.connection.entity.Wireless;

interface AllConnections extends Connection,
        OneToOneConnection,
        OneToManyConnection,
        ManyToManyConnection,
        AllToAllConnection,
        OpticFiber,
        ThinCoaxial,
        TwistedPair,
        Wireless {

}
