package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public enum ConnectionDb {
	INSTANCE ;
	private final static String DRIVER ;
	private final static ResourceBundle DATABASE_PROPERTIES ;
	private static String url ;
	private static String user ;
	private static String password ;
	private static Connection conn ; 
	static {
		DRIVER = "com.mysql.cj.jdbc.Driver" ;
		DATABASE_PROPERTIES = ResourceBundle.getBundle("informationDB"); 
		url = DATABASE_PROPERTIES.getString("Database.url");
		user = DATABASE_PROPERTIES.getString("Database.user");
		password = DATABASE_PROPERTIES.getString("Database.password");
		if((url == null)||(password == null)||(user == null)){
			throw new DAOException("Erreur lecture fichier properties") ;
		}
	}
	
	private ConnectionDb() { 
	}
	
	public static ConnectionDb getInstance () {
		return INSTANCE;
	}
	
	public Connection open () {
		try {
			Class.forName(DRIVER) ;
			conn = DriverManager.getConnection(url,user,password) ;
			return conn ;
		}
		catch (SQLException |ClassNotFoundException de) {
			throw new DAOException("Echec de la connexion à la base de donnée",de) ;
		}
	}
	
	public void close () {
		try {
			conn.close(); 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur lors de la fermeture de la base de donnée",se);
		}
	}
}
