package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public class DAOCompanyImplTest {

    private static AnnotationConfigApplicationContext ctx;

    @BeforeClass
    public static void initialiser() {
        ctx = new AnnotationConfigApplicationContext(DAOCompanyImpl.class, ConnectionSpringConfig.class);
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
