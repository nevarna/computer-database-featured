package com.navarna.computerdb.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.persistence.ConnectionSpringConfig;
import com.navarna.computerdb.persistence.DAOCompanyImpl;

public class ServiceCompanyImplTest {

    @Test
    public void testListeComplete() {
        try  {
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                    ServiceCompanyImpl.class,
                    DAOCompanyImpl.class,  
                    ConnectionSpringConfig.class);
           ArrayList<Company> al = ((ServiceCompany) ctx.getBean(ServiceCompany.class)).listeComplete();
            ctx.close();
            assertNotNull(al);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
