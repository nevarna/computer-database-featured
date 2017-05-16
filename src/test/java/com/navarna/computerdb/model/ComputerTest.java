package com.navarna.computerdb.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer.ComputerBuilder;

public class ComputerTest {

	@Test
	public void testComputer() {
		CompanyBuilder companyBuilder = new CompanyBuilder("company").setId(new Long(1)) ;
		Company company = companyBuilder.build() ;
		ComputerBuilder computerBuilder = new ComputerBuilder("test").setId(new Long(0)).setIntroduced(LocalDate.of(1999,10,11)).setDiscontinued(LocalDate.of(2000, 11, 20)).setCompany(company) ;
		Computer computer = computerBuilder.build() ;
		Long idTest = computer.getId() ;
		String nameTest = computer.getName() ;
		LocalDate introducedTest = computer.getIntroduced() ;
		LocalDate discontinuedTest = computer.getDiscontinued() ;
		Company companyTest = computer.getCompany() ;
		assertEquals(idTest,new Long(0)) ;
		assertEquals(nameTest,"test") ;
		assertEquals(introducedTest,LocalDate.of(1999,10,11)) ;
		assertEquals(discontinuedTest,LocalDate.of(2000, 11, 20)) ;
		assertEquals(company,companyTest) ;
	}
	
	@Test
	public void test2() {
	    Computer computer = new Computer.ComputerBuilder(null).build();
	    System.out.println("computer  "+computer.getName());
	}

}
