package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConnectionPoolDBTest {

    @Test
    public void testSpringConnection() {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConnectionSpringPool.class,
                ConnectionSpringConfig.class)) {
            Connection conn = ((ConnectionSpringPool) ctx.getBean(ConnectionSpringPool.class)).open();
            Statement st = conn.createStatement();
            ResultSet r = st.executeQuery("select * from computer");
            while( r.next()) {
                System.out.println(" id : "+ r.getLong("id")+ " name : "+r.getString("name"));
            }
            assertNotNull(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testTransformation() {
        try  {
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DAOCompanyImpl.class, ConnectionSpringPool.class, ConnectionSpringConfig.class);
            ((DAOCompanyImpl) ctx.getBean(DAOCompanyImpl.class)).list(0, 10);
            ctx.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
