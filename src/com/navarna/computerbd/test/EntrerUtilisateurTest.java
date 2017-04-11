package com.navarna.computerbd.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.navarna.computerdb.ui.EntrerUtilisateur;

public class EntrerUtilisateurTest {

	@Test
	public void testVerifeFormatTimestamp() {
		String reussir = "2010-11-06 13:42:51";
		boolean resulta = EntrerUtilisateur.verifeFormatTimestamp(reussir);
		assertEquals(resulta,true);
		reussir = "2010-12-27"; 
		resulta = EntrerUtilisateur.verifeFormatTimestamp(reussir);
		assertEquals(resulta,true);
		String faux = "2010-11-06 13:42:70";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
		faux = "2010-11-06 13:62:51";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
		faux = "2010-11-06 25:42:51";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
		faux = "2010-11-32 13:42:51";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
		faux = "2010-15-06 13:42:50";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
		faux = "1000-11-06 13:62:50";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
		faux = "2010-11 13:62:50";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
		faux = "2010-11-06 13::50";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
		faux = "2010-11-0613:62:50";
		resulta = EntrerUtilisateur.verifeFormatTimestamp(faux);
		assertEquals(resulta,false);
	}

}
