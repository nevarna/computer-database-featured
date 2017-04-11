package com.navarna.computerdb.persistence;

import com.navarna.computerdb.model.Computer;

public final class DaoComputer extends Dao<Computer> {
	private static final DaoComputer instance = new DaoComputer();
	
	
	private DaoComputer() {
		// TODO Auto-generated constructor stub
	}
	


	public static DaoComputer getInstance() {
		return instance ; 
	}
	@Override
	public boolean insert(String information) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO computer VALUES (" + information  +")";
		return ConnectionDb.getInstance().envoieQuery(query, 3);
	}

	@Override
	public boolean update(int id , String information) {
		// TODO Auto-generated method stub
		String query = "UPDATE computer SET "+information +" where id = "+ id ;
		return ConnectionDb.getInstance().envoieQuery(query, 3);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		String query = "DELETE FROM computer where id = "+ id ; 
		return ConnectionDb.getInstance().envoieQuery(query, 3);
	}

	@Override
	public boolean list() {
		// TODO Auto-generated method stub
		String query = "SELECT id,name from computer LIMIT "+getNbElement()+" OFFSET "+ getPage()*getNbElement() ; 
		return ConnectionDb.getInstance().envoieQuery(query, 1);
	}

	@Override
	public boolean show(int id) {
		// TODO Auto-generated method stub
		String query = "SELECT * from computer where id = "+id  ;
		return ConnectionDb.getInstance().envoieQuery(query, 2);
	}
	
	

}
