package com.netcracker.edu.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by makovetskyi on 03.11.17.
 */
public class DBConnector {

    public static Connection connectToRDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:RDB_inventory.db");
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException on load \"org.sqlite.JDBC\" driver.\n" + e.toString());
        } catch (SQLException e) {
            System.err.println("SQLException on connect to \"jdbc:sqlite:RDB_inventory.db\" database.\n" + e.toString());
        }
        return null;
    }

}
