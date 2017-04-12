package com.navarna.computerdb.persistence;

public interface DAOCompany {
	public static int page = 0 ; 
	public static int nbElement = 20 ;
	
	public int getPage() ;
	public int getNbElement () ;
	public boolean list () ;
	public void resetList() ;
	public boolean suivantList() ;
}
