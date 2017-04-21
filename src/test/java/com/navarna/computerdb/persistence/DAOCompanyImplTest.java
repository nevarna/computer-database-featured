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
        DAOCompanyImpl.getInstance().resetPage();
        Page<Company> page = DAOCompanyImpl.getInstance().list();
        assertEquals(0,DAOCompanyImpl.getInstance().getPage());
        assertNotNull(page);
    }

    @Test
    public void testResetPage() {
        DAOCompanyImpl.getInstance().resetPage();
        assertEquals(0,DAOCompanyImpl.getInstance().getPage());
    }

    @Test
    public void testListeSuivante() {
        DAOCompanyImpl.getInstance().resetPage();
        Page<Company> page = DAOCompanyImpl.getInstance().listeSuivante();
        assertEquals(1,DAOCompanyImpl.getInstance().getPage());
        assertNotNull(page);
    }

}
