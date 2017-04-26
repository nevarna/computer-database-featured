package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.navarna.computerdb.exception.DAOException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum ConnectionPoolDB {
    INSTANCE;
    private static HikariDataSource connectionPool = null;
    private static final ResourceBundle DATABASE_PROPERTIES;

    static {
        DATABASE_PROPERTIES = ResourceBundle.getBundle("informationDB");
        String url = DATABASE_PROPERTIES.getString("Database.url");
        String user = DATABASE_PROPERTIES.getString("Database.user");
        String password = DATABASE_PROPERTIES.getString("Database.password");
        String cachePrep = DATABASE_PROPERTIES.getString("Database.cachePrepStmt");
        String prepStatementCacheSize = DATABASE_PROPERTIES.getString("Database.prepStmtCacheSize");
        String prepStatementCacheLimit = DATABASE_PROPERTIES.getString("Database.prepStmtCacheSqlLimit");
        String maxPoolLimit = DATABASE_PROPERTIES.getString("Database.maxPoolSize");
        String autoCommit = DATABASE_PROPERTIES.getString("Database.autoCommit");
        if ((url == null) || (password == null) || (user == null) || (cachePrep == null)
                || (prepStatementCacheLimit == null) || (prepStatementCacheSize == null) || (maxPoolLimit == null)
                || (autoCommit == null)) {
            throw new DAOException("Erreur lecture fichier properties");
        }
        try {
            initialisation(url, user, password, cachePrep, prepStatementCacheSize, prepStatementCacheLimit,
                    maxPoolLimit, autoCommit);
        } catch (NumberFormatException ne) {
            throw new DAOException("Erreur attribut max pool size fichier properties", ne);
        }
    }

    /**
     * getter de INSTANCE de connectionBD.
     * @return INSTANCE : la seul instance de l'énumeration
     */
    public static ConnectionPoolDB getInstance() {
        return INSTANCE;
    }

    /**
     * Initialise la configuration de HikariPool et iniitialise le dataSource.
     * @param url : url de la base de données
     * @param user : user de la base de données
     * @param password : mot de passe de la base de données
     * @param cachePrep : indique si on a un cache de prepared statement
     * @param prepStatementCacheSize : taille de chaque prepared statement
     * @param prepStatementCacheLimit : nombre maximum de prepared statement
     * @param maxPoolLimit : nombre de connection maximum
     * @param autoCommit : indique si les connection sont en autoCommit
     */
    private static void initialisation(String url, String user, String password, String cachePrep,
            String prepStatementCacheSize, String prepStatementCacheLimit, String maxPoolLimit, String autoCommit) {
        HikariConfig configuration = new HikariConfig();
        configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUrl(url);
        configuration.setUsername(user);
        configuration.setPassword(password);
        configuration.setMaximumPoolSize(Integer.parseInt(maxPoolLimit));
        configuration.setAutoCommit(Boolean.parseBoolean(autoCommit));
        configuration.addDataSourceProperty("cachePrepStmt", cachePrep);
        configuration.addDataSourceProperty("prepStmtCacheSize", prepStatementCacheSize);
        configuration.addDataSourceProperty("prepStmtCacheSqlLimit", prepStatementCacheLimit);
        connectionPool = new HikariDataSource(configuration);
    }

    /**
     * Permet de récupérer une connection.
     * @return Connection : une connection de hikari dataSource
     */
    public Connection open() {
        try {
            return connectionPool.getConnection();
        } catch (SQLException se) {
            throw new DAOException("Erreur creation connection database", se);
        }
    }
}
