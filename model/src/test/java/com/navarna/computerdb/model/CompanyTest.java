package com.navarna.computerdb.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.navarna.computerdb.model.Company.CompanyBuilder;

public class CompanyTest {

	@Test
	public void testCompany() {
		CompanyBuilder builder = new CompanyBuilder("test").setId(new Long(0)) ;
		Company company = builder.build() ;
		Long idTest = company.getId() ;
		String nameTest = company.getName() ;
		assertEquals(new Long(0),idTest) ;
		assertEquals("test",nameTest) ;
	}

}
