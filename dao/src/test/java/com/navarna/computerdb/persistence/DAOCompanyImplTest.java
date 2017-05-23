package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public class DAOCompanyImplTest {

    private static AnnotationConfigApplicationContext ctx;
    private static final int NOMBRE_INSERT = 574;
    
    public static void miseAZero() {
        JdbcTemplate j = (JdbcTemplate) ctx.getBean("jdbcTemplate");
         try {
             BufferedReader br = new BufferedReader(new FileReader("./src/test/resources/insert.sql"));
             String [] ligne = new String [NOMBRE_INSERT]; 
             int pointeur = 0 ;
             while(pointeur < NOMBRE_INSERT) {
                 ligne[pointeur] = br.readLine();
                 ++ pointeur;
                 
             }
             br.close();
             j.batchUpdate(ligne);
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     }
    @BeforeClass
    public static void initialiser() {
        ctx = new AnnotationConfigApplicationContext(DAOCompanyImpl.class, ConnectionSpringConfig.class);
        miseAZero();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(ctx.getBean(DAOCompanyImpl.class));
    }

    @Test
    public void testList() {
        Page<Company> page = ((DAOCompanyImpl) ctx.getBean(DAOCompanyImpl.class)).list(0, 20);
        assertEquals(page.estVide(), false);
    }
    

    @AfterClass
    public static void fermer() {
        ctx.close();
    }

}
