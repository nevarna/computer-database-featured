package com.navarna.computerdb.persistence;

import com.navarna.computerdb.mapper.Page;
import com.navarna.computerdb.model.Company;

public interface DAOCompany {
	
	public int getPage() ;
	public int getNbElement () ;
	public void setPage (int pPage) ;
	public void setNbElement (int pNbElement) ;
	public Page<Company> list () ;
	public void resetList() ;
	public Page<Company> suivantList() ;
}
