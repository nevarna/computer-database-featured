package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum ConnectionPoolDB {
    INSTANCE;
    private static HikariDataSource connectionPool = null ;
    private static final ResourceBundle DATABASE_PROPERTIES;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CACHE_PREP;
    private static final String PREP_STMT_CACHE_SIZE;
    private static final String PREP_STMT_CACHE_LIMIT;
    private static final String MAX_POOL_SIZE;
    private static final Boolean AUTO_COMMIT;
    
    static {
        DATABASE_PROPERTIES = ResourceBundle.getBundle("informationDB");
        URL = DATABASE_PROPERTIES.getString("Database.url");
        USER = DATABASE_PROPERTIES.getString("Database.user");
        PASSWORD = DATABASE_PROPERTIES.getString("Database.password");
        CACHE_PREP = DATABASE_PROPERTIES.getString("Database.cachePrepStmt");
        PREP_STMT_CACHE_SIZE = DATABASE_PROPERTIES.getString("Database.prepStmtCacheSize");
        PREP_STMT_CACHE_LIMIT = DATABASE_PROPERTIES.getString("Database.prepStmtCacheSqlLimit");
        MAX_POOL_SIZE = DATABASE_PROPERTIES.getString("Database.maxPoolSize");
        AUTO_COMMIT = Boolean.valueOf(DATABASE_PROPERTIES.getString("Database.autoCommit"));
        if ((URL == null) || (PASSWORD == null) || (USER == null)
                || (CACHE_PREP == null) || (PREP_STMT_CACHE_LIMIT == null) || (PREP_STMT_CACHE_SIZE == null)) {
            throw new DAOException("Erreur lecture fichier properties");
        }
        try {
            initialisation();
        } catch(NumberFormatException ne) {
            throw new DAOException("Erreur attribut max pool size fichier properties", ne);
        }
    }
    
    public static ConnectionPoolDB getInstance() {
        return INSTANCE;
    }
    
    private static void initialisation() {
        HikariConfig configuration = new HikariConfig();
        configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUrl(URL);
        configuration.setUsername(USER);
        configuration.setPassword(PASSWORD);
        configuration.setMaximumPoolSize(Integer.parseInt(MAX_POOL_SIZE));
        configuration.setAutoCommit(AUTO_COMMIT);
        configuration.addDataSourceProperty("cachePrepStmt", CACHE_PREP);
        configuration.addDataSourceProperty("prepStmtCacheSize", PREP_STMT_CACHE_SIZE);
        configuration.addDataSourceProperty("prepStmtCacheSqlLimit", PREP_STMT_CACHE_LIMIT);
        connectionPool = new HikariDataSource(configuration);
    }
    
    public Connection open() {
        try {
            return connectionPool.getConnection();
        } catch (SQLException se) {
            throw new DAOException("Erreur creation connection database", se);
        }
    }
}
