package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.navarna.computerdb.model.Company;

public class ConnectionHibernateTest {
    private static AnnotationConfigApplicationContext ctx;

    @BeforeClass
    public static void initialiser() {
        ctx = new AnnotationConfigApplicationContext(ConnectionSpringConfig.class);
    }
    
    @Test
    public void testSpringHibernate() {
        SessionFactory factory = (SessionFactory) ctx.getBean("sessionFactory");
        Session session = factory.openSession();
        Query<Company> company = session.createQuery("from Company where id = 10", Company.class);
        Company test = company.uniqueResult();
        assertNotNull(test);
        System.out.println(" spring : "+test);
        session.close();
    }
    @AfterClass
    public static void fermer() {
        ctx.close();
    }
}
