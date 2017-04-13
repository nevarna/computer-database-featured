package com.navarna.computerdb.service;

import com.navarna.computerdb.mapper.Page;
import com.navarna.computerdb.model.Company;

public interface ServiceCompany {

	public Page<Company> liste () ;
	public Page<Company> listeSuivante () ;
	public void choisirPage (int page) ;
	public void choisirNbElement (int nbElement) ;
	public void resetPage () ;
}
