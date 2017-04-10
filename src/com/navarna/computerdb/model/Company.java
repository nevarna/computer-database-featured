package com.navarna.computerdb.model;

public class Company {
	private int id ; 
	private String name ; 

	public Company() {
		// TODO Auto-generated constructor stub
		this.id = -1 ; 
		this.name = "undefined";
	}
	
	public Company(int pId , String pName){
		this.id = pId ; 
		this.name = pName ; 
	}
	
	public int getId() {
		return this.id ; 
	}
	
	public String getName() {
		return this.name ; 
	}

}
