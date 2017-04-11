package com.navarna.computerbd.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.navarna.computerdb.service.ConstruireRequete;

public class ConstruireRequeteTest {

	@Test
	public void testStringEnInt() {
		String reussir = "1" ; 
		int resultat = ConstruireRequete.StringEnInt(reussir);
		assertEquals(resultat,1);
		reussir = "01";
		resultat = ConstruireRequete.StringEnInt(reussir);
		assertEquals(resultat,1);
		String echec = "a";
		resultat = ConstruireRequete.StringEnInt(echec);
		assertEquals(resultat,-1);
		echec = "-2";
		assertEquals(resultat,-1);
	}

	@Test
	public void testCommand() {
		String [] uCommand = new String [1];
		boolean resultat = ConstruireRequete.command(uCommand);
		assertEquals(resultat, false);
		ConstruireRequete.resetList("computers");
		String []command = {"List","computers"};
		resultat = ConstruireRequete.command(command);
		assertEquals(resultat,true);
		command[1] = "companies";
		ConstruireRequete.resetList("companies");
		resultat = ConstruireRequete.command(command);
		assertEquals(resultat,true);
	}


}
