package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.navarna.computerdb.exception.DAOException;
import com.zaxxer.hikari.HikariDataSource;

@Repository("springDataSource")
@Scope("singleton")
public class ConnectionSpringPool {
    @Autowired
    private HikariDataSource hikariDataSource;

    public Connection open() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException se) {
            throw new DAOException("Erreur creation connection database", se);
        }
    }
}
