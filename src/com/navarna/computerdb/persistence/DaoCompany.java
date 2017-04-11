package com.navarna.computerdb.persistence;

import com.navarna.computerdb.model.Company;

public final class DaoCompany extends Dao<Company> {
	private static final DaoCompany instance = new DaoCompany();
	
	private DaoCompany() {
		// TODO Auto-generated constructor stub
	}
	
	public static DaoCompany getInstance() {
		return instance ;
	}

	@Override
	public boolean insert(String information) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int id, String information) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean show(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean list() {
		// TODO Auto-generated method stub
		String query = "SELECT id,name from company LIMIT "+getNbElement()+" OFFSET "+ getPage()*getNbElement() ; 
		return ConnectionDb.getInstance().envoieQuery(query, 0);
	}
}
