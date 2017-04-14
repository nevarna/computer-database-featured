package com.navarna.computerdb.persistence;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public interface DAOCompany {
	
	public int getPage() ;
	public int getNbElement () ;
	public void setPage (int pPage) ;
	public void setNbElement (int pNbElement) ;
	public Page<Company> list () ;
	public void resetPage() ;
	public Page<Company> listeSuivante() ;
}
