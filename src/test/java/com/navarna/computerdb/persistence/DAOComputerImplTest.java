package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer.ComputerBuilder;

public class DAOComputerImplTest {

    private static Computer computerTest = null;
    private static Computer computerNull = null;
    private static Computer computerAvecId = null;
    private static long id = 0L;

    @BeforeClass
    public static void setComputer () {
        id = 597L;
        Company company = new Company.CompanyBuilder("company").setId(new Long(1)).build() ;
        Company companyNull = new CompanyBuilder("null").build();
        computerTest = new ComputerBuilder("test").setId(new Long(0)).setIntroduced(LocalDate.of(1999,10,11)).setDiscontinued(LocalDate.of(2000, 11, 20)).setCompany(company).build() ;
        computerNull = new ComputerBuilder("null").setCompany(companyNull).build();
        computerAvecId = new ComputerBuilder("nullAvecId").setId(1).setCompany(companyNull).build();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(DAOComputerImpl.getInstance()); 
    }

    @Test
    public void testInsert() {
        boolean result = DAOComputerImpl.getInstance().insert(computerTest);
        assertEquals(true,result);
        boolean resultNull = DAOComputerImpl.getInstance().insert(computerNull);
        assertEquals(true,resultNull);
    }

    @Test
    public void testUpdate() {
        computerTest.setId(600L);
        boolean result = DAOComputerImpl.getInstance().update(computerTest);
        assertEquals(false,result);
        boolean resultAvecId = DAOComputerImpl.getInstance().update(computerAvecId);
        assertEquals(true,resultAvecId);
    }

    @Test
    public void testDelete() {
        boolean result = DAOComputerImpl.getInstance().delete(id);
        assertEquals(true,result);
    }

    @Test
    public void testList() {
        Page<Computer> page = DAOComputerImpl.getInstance().list(0,20,"computer.id","ASC");
        assertEquals(page.estVide(),false);
    }

    @Test
    public void testShowId() {
        Optional<Computer> computer = DAOComputerImpl.getInstance().findById(1L);
        assertEquals(computer.isPresent(),true);
    }

    @Ignore
    public void testShowName() {
        Page<Computer> page = DAOComputerImpl.getInstance().findByName("test",0,20,"computer.id","ASC");
        assertEquals(page.estVide(),false);
    }

    @Ignore
    public void testCountComputer() {
        int result = DAOComputerImpl.getInstance().count();
        assertEquals(result,602);
    }
}
