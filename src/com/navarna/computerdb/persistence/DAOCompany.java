package com.navarna.computerdb.persistence;

public interface DAOCompany {
	
	public int getPage() ;
	public int getNbElement () ;
	public boolean list () ;
	public void resetList() ;
	public boolean suivantList() ;
}
