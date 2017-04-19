package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.BeforeClass;
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
        CompanyBuilder companyBuilder = new CompanyBuilder("company").setId(new Long(1)) ;
        Company company = companyBuilder.build() ;
        CompanyBuilder companyBuilderNull = new CompanyBuilder("null");
        Company companyNull = companyBuilderNull.build();
        ComputerBuilder computerBuilder = new ComputerBuilder("test").setId(new Long(0)).setIntroduced(LocalDate.of(1999,10,11)).setDiscontinued(LocalDate.of(2000, 11, 20)).setCompany(company) ;
        computerTest = computerBuilder.build();
        ComputerBuilder builderNull = new ComputerBuilder("null").setCompany(companyNull);
        computerNull = builderNull.build();
        ComputerBuilder builderAvecId = new ComputerBuilder("nullAvecId").setId(577L).setCompany(companyNull);
        computerAvecId = builderAvecId.build();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(DAOComputerImpl.getInstance()); 
    }

    @Test
    public void testInsert() {
        int result = DAOComputerImpl.getInstance().insert(computerTest);
        assertEquals(1,result);
        int resultNull = DAOComputerImpl.getInstance().insert(computerNull);
        assertEquals(1,resultNull);
    }

    @Test
    public void testUpdate() {
        int result = DAOComputerImpl.getInstance().update(computerTest);
        assertEquals(0,result);
        int resultNull = DAOComputerImpl.getInstance().update(computerNull);
        assertEquals(0,resultNull);
        int resultAvecId = DAOComputerImpl.getInstance().update(computerAvecId);
        assertEquals(1,resultAvecId);
    }

    @Test
    public void testDelete() {
        int result = DAOComputerImpl.getInstance().delete(id);
        assertEquals(1,result);
    }

    @Test
    public void testList() {
        DAOComputerImpl.getInstance().resetPage();
        Page<Computer> page = DAOComputerImpl.getInstance().list();
        assertEquals(0,DAOComputerImpl.getInstance().getPage());
        assertNotNull(page);
    }

    @Test
    public void testShowId() {
        Computer computer = DAOComputerImpl.getInstance().showId(1L);
        assertNotNull(computer);
    }

    @Test
    public void testShowName() {
        DAOComputerImpl.getInstance().resetPage();
        Page<Computer> page = DAOComputerImpl.getInstance().showName("test");
        assertNotNull(page);
    }

    @Test
    public void testResetPage() {
        DAOComputerImpl.getInstance().resetPage();
        assertEquals(0,DAOComputerImpl.getInstance().getPage());
    }

    @Test
    public void testListeSuivante() {
        DAOComputerImpl.getInstance().resetPage();
        Page<Computer> page = DAOComputerImpl.getInstance().listeSuivante();
        assertEquals(1,DAOComputerImpl.getInstance().getPage());
        assertNotNull(page);
    }

}
