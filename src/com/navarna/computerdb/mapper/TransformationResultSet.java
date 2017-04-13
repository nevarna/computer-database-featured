package com.navarna.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.persistence.DAOCompanyImpl;
import com.navarna.computerdb.persistence.DaoComputer;

public class TransformationResultSet {
	
	public static Page<Computer> extraireListeComputer (ResultSet result){
		try {
			Page<Computer> page = new Page<Computer>(DaoComputer.getInstance().getPage(), DaoComputer.getInstance().getNbElement()); 
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
	
	public static Computer extraireDetailsComputer (ResultSet result) {
		try {
			if(result.next()) { 
				Long id = result.getLong("id");
				String nameComputer = result.getString("computer.name");
				LocalDate introduced = result.getTimestamp("introduced").toLocalDateTime().toLocalDate();
				LocalDate discontinued =  result.getTimestamp("discontinued").toLocalDateTime().toLocalDate();
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
			Page<Computer> page = new Page<Computer>(DaoComputer.getInstance().getPage(), DaoComputer.getInstance().getNbElement()); 
			int compteur = 0 ;
			while(result.next()) {
				compteur ++ ;
				Long id = result.getLong("id");
				String nameComputer = result.getString("computer.name");
				LocalDate introduced = result.getTimestamp("introduced").toLocalDateTime().toLocalDate();
				LocalDate discontinued =  result.getTimestamp("discontinued").toLocalDateTime().toLocalDate();
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
