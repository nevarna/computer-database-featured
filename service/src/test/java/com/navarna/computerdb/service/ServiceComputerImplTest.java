package com.navarna.computerdb.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.persistence.ConnectionSpringConfig;
import com.navarna.computerdb.persistence.DAOComputerImpl;

public class ServiceComputerImplTest {
    private static AnnotationConfigApplicationContext ctx ;
    private static ServiceComputerImpl serviceComputerImpl;
    private static Computer computerTest = null;
    private static Computer computerNull = null;
    @BeforeClass
    public static void setComputer () {
        Company company = new Company.CompanyBuilder("company").setId(new Long(1)).build() ;
        Company companyNull = new CompanyBuilder("null").build();
        computerTest = new ComputerBuilder("test").setId(new Long(0)).setIntroduced(LocalDate.of(1999,10,11)).setDiscontinued(LocalDate.of(2000, 11, 20)).setCompany(company).build() ;
        computerNull = new ComputerBuilder(null).setCompany(companyNull).build();
        ctx = new AnnotationConfigApplicationContext(
                ServiceComputerImpl.class,
                DAOComputerImpl.class,
                ConnectionSpringConfig.class);
        serviceComputerImpl = (ServiceComputerImpl) ctx.getBean(ServiceComputerImpl.class);
    }
    @Test
    public void testInsert() {
        boolean correct = serviceComputerImpl.insert(computerTest);
        computerNull.setName(null);
        boolean faux = serviceComputerImpl.insert(computerNull);
        assertEquals(correct, true);
        assertEquals(faux, false);
    }

    @Test
    public void testUpdate() {
        computerTest.setId(113L);
        boolean correct = serviceComputerImpl.update(computerTest);
        boolean faux = serviceComputerImpl.update(computerNull);
        assertEquals(correct, true);
        assertEquals(faux, false);
    }

    @Test
    public void testListe() {
        Page<Computer> page = serviceComputerImpl.liste(0, 10, "id", "ASC");
        assertEquals(false,page.estVide());
    }

    @Test
    public void testFindById() {
        Optional<Computer> rien = serviceComputerImpl.findById(-1L);
        Optional<Computer> computer = serviceComputerImpl.findById(112L);
        assertEquals(true, computer.isPresent());
        assertEquals(false, rien.isPresent());
    }

    @AfterClass
    public static void fermer() {
        ctx.close();
    }
}
