package com.navarna.computerdb.persistence;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectionDbTest {

	@Test
	public void testGetInstance() {
		ConnectionDb test = ConnectionDb.getInstance() ;
		ConnectionDb test2 = ConnectionDb.INSTANCE ;
		assertEquals(test,test2);
	}

}
