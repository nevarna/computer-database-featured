package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectionPoolDBTest {
    private ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    @Value("#{db_config[Database.url]}")
    private String url ;
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
    
    @Test
    public void testSpringDataSource() {
          // open/read the application context file
          
          System.out.println("url :"+ url);

    }

}
