package com.navarna.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.persistence.DAOCompanyImpl;
import com.navarna.computerdb.persistence.DAOComputerImpl;

public class TransformationResultSet {
	
	public static Page<Computer> extraireListeComputer (ResultSet result){
		try {
			Page<Computer> page = new Page<Computer>(DAOComputerImpl.getInstance().getPage(), DAOComputerImpl.getInstance().getNbElement()); 
			int compteur = 0 ; 
			while(result.next()) {
				compteur ++ ; 
				 Long id = result.getLong("id");
				 String name = result.getString("name");
				 Computer computer = new ComputerBuilder(name).setId(id).build();
				 page.addElement(computer);
			}
			if(compteur != 0) {
				return page ;
			}
			else 
				return null ;
		}
		catch(SQLException se) {
			throw new MapperException("Erreur de result.next (fonction extraireListeCompany())");
		}
	}
	
	public static Page<Company> extraireListeCompany (ResultSet result) {
		try {
			Page<Company> page = new Page<Company>(DAOCompanyImpl.getInstance().getPage(),DAOCompanyImpl.getInstance().getNbElement()); 
			int compteur = 0 ; 
			while(result.next()) {
				compteur ++;
				Long id = result.getLong("id");
				String name = result.getString("name");
				Company company = new CompanyBuilder(name).setId(id).build();
				page.addElement(company);
			}
			if(compteur != 0) {
				return page ;
			}
			else 
				return null ;
		}
		catch(SQLException se) {
			throw new MapperException("Erreur de result.next (fonction extraireListeCompany())");
		}
	}
	
	public static LocalDate recupererDate (String dateEnString) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss.S");
			LocalDate date = LocalDate.parse(dateEnString,formatter);
			return date ;
		}
		catch (DateTimeParseException pe) {
			return null ;
		}
		
	}
	
	public static Computer extraireDetailsComputer (ResultSet result) {
		try {
			if(result.next()) { 
				Long id = result.getLong("id");
				String nameComputer = result.getString("computer.name");
				String tIntroduced = result.getString("introduced");
				LocalDate introduced = null ; 
				if(tIntroduced != null) {
					introduced = recupererDate(tIntroduced);
				}
				String tDiscontinued = result.getString("discontinued");
				LocalDate discontinued = null ;
				if(tDiscontinued != null) {
					discontinued = recupererDate(tDiscontinued);
				}
				Long companyId  = result.getLong("company_id");
				String nameCompany = result.getString("company.name");
				Company company = new CompanyBuilder(nameCompany).setId(companyId).build() ;
				Computer computer = new ComputerBuilder(nameComputer).setId(id).setIntroduced(introduced).setDiscontinued(discontinued).setCompany(company).build();
				return computer ; 
			}
			else {
				return null ;
			}
		}
		catch(SQLException se) {
			throw new MapperException("Erreur de result.next()",se);
		}
	}
	
	public static Page<Computer> extraireDetailsComputers (ResultSet result) {
		try {
			Page<Computer> page = new Page<Computer>(DAOComputerImpl.getInstance().getPage(), DAOComputerImpl.getInstance().getNbElement()); 
			int compteur = 0 ;
			while(result.next()) {
				compteur ++ ;
				Long id = result.getLong("id");
				String nameComputer = result.getString("computer.name");
				Timestamp tIntroduced = result.getTimestamp("introduced");
				LocalDate introduced = null ; 
				if(tIntroduced != null) {
					introduced = tIntroduced.toLocalDateTime().toLocalDate();
				}
				Timestamp tDiscontinued = result.getTimestamp("discontinued");
				LocalDate discontinued = null ;
				if(tDiscontinued != null) {
					discontinued = tDiscontinued.toLocalDateTime().toLocalDate();
				}
				Long companyId  = result.getLong("company_id");
				String nameCompany = result.getString("company.name");
				Company company = new CompanyBuilder(nameCompany).setId(companyId).build() ;
				Computer computer = new ComputerBuilder(nameComputer).setId(id).setIntroduced(introduced).setDiscontinued(discontinued).setCompany(company).build();
				page.addElement(computer); 
			}
			if(compteur == 0) {
				return null ;
			}
			else {
				return page ; 
			}
		}
		catch(SQLException se) {
			throw new MapperException("Erreur de result.next()",se);
		}
	}

}
