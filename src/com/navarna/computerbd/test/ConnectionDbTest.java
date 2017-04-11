package com.navarna.computerbd.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.navarna.computerdb.persistence.ConnectionDb;

public class ConnectionDbTest {

	@Test
	public void testEnvoieQuery() {
		String query = "SELECT * from computer LIMIT 20"; 
		int mode = 1 ;
		boolean resultat = ConnectionDb.getInstance().envoieQuery(query, mode);
		assertEquals(resultat , true);
		query = "SELECT * from computers LIMIT 20";
		resultat = ConnectionDb.getInstance().envoieQuery(query, mode);
		assertEquals(resultat , false);
		mode = 3 ; 
		resultat = ConnectionDb.getInstance().envoieQuery(query, mode);
		assertEquals(resultat , false);
	}

}
