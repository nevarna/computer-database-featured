package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.navarna.computerdb.mapper.Page;
import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Computer;

public final class DAOComputerImpl implements DAOComputer {
	private static final DAOComputerImpl INSTANCE = new DAOComputerImpl();
	
	public static int page = 0 ; 
	public static int nbElement = 20 ;
	
	public final static String INSERT = "INSERT INTO computer VALUES ( ?, ?, ?, ?, ? )";
	public final static String UPDATE = "UPDATE computer SET name = ?, introduce = ?, discontinued = ?, id_company = ? where id = ?" ; 
	public final static String DELETE = "DELETE FROM computer where id = ?";
	public final static String SELECT_LIST = "SELECT id,name from computer LIMIT ? OFFSET ?";
	public final static String SHOW_ID = "SELECT * from computer where id = ?";
	public final static String SHOW_NAME = "SELECT * from computer where name = ? LIMIT ? OFFSET ?";
			
	private DAOComputerImpl() {
	}
	
	@Override
	public int getPage() {
		return page ; 
	}

	@Override
	public int getNbElement() {
		return nbElement ;
	}


	public static DAOComputerImpl getInstance() {
		return INSTANCE ; 
	}
	@Override
	public boolean insert(Computer computer) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			int result = 0 ; 
			PreparedStatement statement = conn.prepareStatement(INSERT);
			statement.setLong(0, computer.getId());
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
			statement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
			statement.setLong(4, computer.getCompany().getId());
			result = statement.executeUpdate();
			if(result == 0) {
				// func service 
			}
			else {
				// func service 
			}
			statement.close();
			ConnectionDb.getInstance().close();
			return true; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}

	@Override
	public boolean update(Computer computer) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			int result = 0 ; 
			PreparedStatement statement = conn.prepareStatement(UPDATE);
			statement.setString(0, computer.getName());
			statement.setTimestamp(1, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
			statement.setTimestamp(2, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
			statement.setLong(3, computer.getCompany().getId());
			statement.setLong(4, computer.getId());
			result = statement.executeUpdate();
			if(result == 0) {
				// func service 
			}
			else {
				// func service 
			}
			statement.close();
			ConnectionDb.getInstance().close();
			return true; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}

	@Override
	public boolean delete(long id) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			int result = 0 ; 
			PreparedStatement statement = conn.prepareStatement(DELETE);
			statement.setLong(0, id);
			result = statement.executeUpdate();
			if(result == 0) {
				// func service 
			}
			else {
				// func service 
			}
			statement.close();
			ConnectionDb.getInstance().close();
			return true; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}

	@Override
	public boolean list() {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null; 
			PreparedStatement statement = conn.prepareStatement(SELECT_LIST);
			statement.setInt(0, nbElement);
			statement.setInt(1, page * nbElement);
			result = statement.executeQuery();
			if(result != null) {
				 Page<Computer> page = 	TransformationResultSet.extraireListeComputer(result);
				 if(page != null) {
					 //func service 
				 }
				 else {
					 // func service 
				 }
			}
			else {
				// func service 
			}
			statement.close();
			ConnectionDb.getInstance().close();
			return true; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}

	@Override
	public boolean show(long id) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null; 
			PreparedStatement statement = conn.prepareStatement(SHOW_ID);
			statement.setLong(0, id);
			result = statement.executeQuery();
			if(result != null) {
				 Computer computer = TransformationResultSet.extraireDetailsComputer(result);
				 if(computer != null) {
					 //func service 
				 }
				 else {
					 // func service 
				 }
			}
			else {
				// func service 
			}
			statement.close();
			ConnectionDb.getInstance().close();
			return true; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}
	
	@Override
	public boolean show(String name) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null; 
			PreparedStatement statement = conn.prepareStatement(SHOW_NAME);
			statement.setString(0, name);
			statement.setInt(1, nbElement);
			statement.setInt(2, page * nbElement);
			result = statement.executeQuery();
			if(result != null) {
				 Page<Computer> page = TransformationResultSet.extraireDetailsComputers(result);
				 if(page != null) {
					 //func service 
				 }
				 else {
					 // func service 
				 }
			}
			else {
				// func service 
			}
			statement.close();
			ConnectionDb.getInstance().close();
			return true; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
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

	@Override
	public boolean suivantShow(String name) {
		page ++ ; 
		return show(name);
	}
}
