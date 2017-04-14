package com.navarna.computerdb.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionDb {
	private final static ConnectionDb INSTANCE ;
	private final static String DRIVER ;
	private final static String FICHIER_PROPERTIES ;
	private static String url ;
	private static String user ;
	private static String password ;
	private static Connection conn ; 
	
	static {
		DRIVER = "com.mysql.jdbc.Driver" ;
		FICHIER_PROPERTIES = "src/main/com/navarna/computerdb/persistence/informationDB.properties";
		INSTANCE = new ConnectionDb() ; 	
	}
	
	private ConnectionDb() {
		if(initialisationParametreDB() );
		else throw new DAOException("Erreur lecture fichier properties"); 
	}
	
	public boolean initialisationParametreDB () {
		try (BufferedReader br = new BufferedReader( new FileReader (FICHIER_PROPERTIES))){
			String ligne = "" ; 	
			while((ligne = br.readLine()) != null) {
				String [] decoupEspace = ligne.split(" ") ;
				if(decoupEspace.length == 3) {
					if(decoupEspace[0].equals("\t<url>")) {
						url = decoupEspace[1].replaceAll("\"","") ;
					}
					else if(decoupEspace[0].equals("\t<user>")) {
						user = decoupEspace[1].replaceAll("\"","") ;
					}
					else if(decoupEspace[0].equals("\t<password>")) {
						password = decoupEspace[1].replaceAll("\"","") ;
					}
				}
			}
			if((url == null)||(password == null)||(user == null)){
				return false ;
			}
		}
		catch(IOException io) {
			throw new DAOException("Erreur IO lecture fichier properties",io); 
		}
		return true ; 
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
