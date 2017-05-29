package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.zaxxer.hikari.HikariDataSource;

public class DAOComputerImplTest {

    private static AnnotationConfigApplicationContext ctx ;
    private static DAOComputerImpl daoComputerImpl;
    private static Computer computerTest = null;
    private static Computer computerNull = null;
    private static Computer computerAvecId = null;
    private static long id = 0L;
    private static final int NOMBRE_INSERT = 574;

    public static void miseAZero() {
        JdbcTemplate j =new JdbcTemplate((HikariDataSource) ctx.getBean("dataSource"));
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
    public static void setComputer () {
        id = 500L;
        Company company = new Company.CompanyBuilder("company").setId(new Long(1)).build() ;
        Company companyNull = new CompanyBuilder("null").build();
        computerTest = new ComputerBuilder("test").setId(new Long(0)).setIntroduced(LocalDate.of(1999,10,11)).setDiscontinued(LocalDate.of(2000, 11, 20)).setCompany(company).build() ;
        computerNull = new ComputerBuilder("null").setCompany(companyNull).build();
        computerAvecId = new ComputerBuilder("nullAvecId").setId(1).setCompany(companyNull).build();
        ctx = new AnnotationConfigApplicationContext(
                DAOComputerImpl.class,
                ConnectionSpringConfig.class);
        miseAZero();
        daoComputerImpl = (DAOComputerImpl) ctx.getBean(DAOComputerImpl.class);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(daoComputerImpl); 
    }

    @Test
    public void testInsert() {
        boolean result = daoComputerImpl.insert(computerTest);
        assertEquals(true,result);
        boolean resultNull = daoComputerImpl.insert(computerNull);
        assertEquals(true,resultNull);
    }

    @Test
    public void testUpdate() {
        computerTest.setId(600L);
        boolean result = daoComputerImpl.update(computerTest);
        assertEquals(false,result);
        boolean resultAvecId = daoComputerImpl.update(computerAvecId);
        assertEquals(true,resultAvecId);
    }

    @Test
    public void testDelete() {
        boolean result = daoComputerImpl.delete(id);
        assertEquals(true,result);
    }

    @Test
    public void testList() {
        Page<Computer> page = daoComputerImpl.list(0,20,"id","ASC");
        assertEquals(page.estVide(),false);
    }

    @Test
    public void testShowId() {
        Optional<Computer> computer = daoComputerImpl.findById(1L);
        assertEquals(computer.isPresent(),true);
    }

    @Test
    public void testShowName() {
        Page<Computer> page = daoComputerImpl.findByName("test",0,20,"id","ASC");
        assertEquals(page.estVide(),false);
    }
    
    @Test
    public void testShowNameCompany() {
        Page<Computer> page = daoComputerImpl.findByCompany("Apple Inc.",0,20,"id","ASC");
        assertEquals(page.estVide(),false);
    }

    @Test
    public void testCountComputer() {
        int result = daoComputerImpl.count();
        assertEquals(result,529);
    }

    @AfterClass
    public static void fermer() {
        ctx.close();
    }
}
