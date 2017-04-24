package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public final class DAOCompanyImpl implements DAOCompany {
    private static final DAOCompanyImpl INSTANCE;

    private static final String SELECT;

    static {
        SELECT  = "SELECT id,name from company LIMIT ? OFFSET ?";
        INSTANCE = new DAOCompanyImpl();
    }

    /**
     * Constructeur privé de l'instance.
     */
    private DAOCompanyImpl() {
    }

    public static DAOCompanyImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Page<Company> list(int numPage , int nbElement) {
        try (Connection conn = ConnectionPoolDB.getInstance().open()) {
            ResultSet result = null;
            PreparedStatement statement = conn.prepareStatement(SELECT);
            setStatementListe(statement, numPage, nbElement);
            result = statement.executeQuery();
            Page<Company> page = TransformationResultSet.extraireListeCompany(result, numPage , nbElement);
            statement.close();
            return page;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }
    
    public static void setStatementListe(PreparedStatement statement,int numPage, int nbElement) {
        try {
            statement.setInt(1, nbElement);
            statement.setInt(2, numPage * nbElement);
        } catch (SQLException e) {
            throw new DAOException("Erreur affectation des arguments", e);
        }
        
    }
}
