package com.navarna.computerdb.model;

import java.sql.Timestamp;

public class Computer {
	private int id  ; 
	private String name ; 
	private Timestamp introduced ; 
	private Timestamp discontinued ; 
	private long company_id ; 
	public Computer() {
		// TODO Auto-generated constructor stub
		this.id = -1 ; 
		this.name = "undefined" ; 
		this.introduced = null  ;
		this.discontinued = null;
		this.company_id = -1 ;
	}
	
	public Computer (int pId , String pName , Timestamp pIntroduced , Timestamp pDiscontinued , long pCompany_id) {
		this.id = pId ; 
		this.name = pName ; 
		this.introduced = pIntroduced ;
		this.discontinued = pDiscontinued ;
		this.company_id = pCompany_id ;
	}
	
	public Computer (int pId , String pName) {
		this.id = pId ; 
		this.name = pName ; 
		this.introduced = null  ;
		this.discontinued = null;
		this.company_id = -1 ;
	}
	
	public String getName () {
		return this.name ; 
	}
	
	public Timestamp getIntroduced () {
		return this.introduced ; 
	}
	
	public Timestamp getDiscontinued () {
		return this.discontinued ;
	}
	
	public long getCompany_id() {
		return this.company_id ; 
	}
	
	public int getId() {
		return this.id ;
	}
	
	
	

}
