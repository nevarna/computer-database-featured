package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class ConnectionPoolDBTest {

    @Test
    public void testOpen() {
        Connection conn = ConnectionPoolDB.INSTANCE.open();
        try {
            assertEquals(conn.isValid(0),true);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
