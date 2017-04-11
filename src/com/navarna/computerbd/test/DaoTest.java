package com.navarna.computerbd.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.navarna.computerdb.persistence.DaoComputer;

public class DaoTest {

	@Test
	public void testResetList() {
		DaoComputer.getInstance().resetList();
		assertEquals(0,DaoComputer.getInstance().getPage());
	}

	@Test
	public void testSuivantList() {
		DaoComputer.getInstance().resetList();
		DaoComputer.getInstance().suivantList();
		assertEquals(1,DaoComputer.getInstance().getPage());
	}

}
