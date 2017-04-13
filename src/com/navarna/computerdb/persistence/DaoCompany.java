package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DaoCompany implements DAOCompany {
	private static final DaoCompany instance = new DaoCompany();
	
	private static int page = 0 ; 
	private static int nbElement = 20 ;
	
	private static final String SELECT  = "SELECT id,name from company LIMIT " ;
	private static final String OFFSET = " OFFSET " ;

	public static DaoCompany getInstance() {
		return instance ;
	}
	
	@Override
	public int getPage() {
		return page ; 
	}

	@Override
	public int getNbElement() {
		return nbElement ;
	}
	
	@Override
	public void setPage(int pPage) {
		page = pPage ;	
	}

	@Override
	public void setNbElement(int pNbElement) {
		nbElement = pNbElement ;
	}

	@Override
	public boolean list() {
		String query = SELECT+getNbElement()+OFFSET+ getPage()*getNbElement() ; 
		return executeQuery(query);
	}
	
	@Override
	public void resetList() {
		page = 0 ;
	}
	
	@Override
	public boolean suivantList() {
		page ++ ; 
		return list();
	}
	
	public boolean executeQuery (String query) {
		boolean retour = false ; 
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null ;
			Statement statement = conn.createStatement();
			result = statement.executeQuery(query);
			//retour = 
			if(result != null)
				result.close();
			statement.close();
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
		return retour; 
	}

}
