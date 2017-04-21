package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import org.junit.Test;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public class DAOCompanyImplTest {

    @Test
    public void testGetInstance() {
        assertNotNull(DAOCompanyImpl.getInstance()); 
    }

    @Test
    public void testList() {
        Page<Company> page = DAOCompanyImpl.getInstance().list(0,20);
        assertEquals(page.estVide(),false);
    }

}
