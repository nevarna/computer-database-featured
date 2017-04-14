package com.navarna.computerdb.service;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.persistence.DAOCompanyImpl;

public class ServiceCompanyImpl implements ServiceCompany {
	private DAOCompanyImpl dCompanyImpl = DAOCompanyImpl.getInstance() ;

	@Override
	public Page<Company> liste() {
		return this.dCompanyImpl.list();
	}

	@Override
	public Page<Company> listeSuivante() {
		return this.dCompanyImpl.listeSuivante();
	}

	@Override
	public void choisirPage(int page) {
		this.dCompanyImpl.setPage(page);
	}

	@Override
	public void choisirNbElement(int nbElement) {
		this.dCompanyImpl.setNbElement(nbElement);
	}

	@Override
	public void resetPage() {
		this.dCompanyImpl.resetPage();
	}

}
