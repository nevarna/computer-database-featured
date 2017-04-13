package com.navarna.computerdb.persistence;

public interface DAOCompany {
	
	public int getPage() ;
	public int getNbElement () ;
	public void setPage (int pPage) ;
	public void setNbElement (int pNbElement) ;
	public boolean list () ;
	public void resetList() ;
	public boolean suivantList() ;
}
