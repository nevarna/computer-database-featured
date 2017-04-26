package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.navarna.computerdb.exception.DAOException;
import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public enum DAOCompanyImpl implements DAOCompany {
    INSTANCE;

    private static final String SELECT;
    private static final String SELECT_ALL;
    private static final String DELETE_COMPUTERS;
    private static final String DELETE_COMPANY;

    static {
        SELECT = "SELECT id,name from company LIMIT ? OFFSET ?";
        SELECT_ALL = "SELECT id,name from company";
        DELETE_COMPUTERS = "DELETE from computer where company_id = ?";
        DELETE_COMPANY = "DELETE from company where id = ?";
    }

    public static DAOCompanyImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Page<Company> list(int numPage, int nbElement) {
        try (Connection conn = ConnectionPoolDB.getInstance().open();
                PreparedStatement statement = conn.prepareStatement(SELECT);) {
            ResultSet result = null;
            setStatementListe(statement, numPage, nbElement);
            result = statement.executeQuery();
            Page<Company> page = TransformationResultSet.extraireListePartielleCompany(result, numPage, nbElement);
            return page;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int delete(long id) {
        Connection conn = null;
        try {
            conn = ConnectionPoolDB.getInstance().open();
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(DELETE_COMPUTERS);
            statement.setLong(1, id);
            int nbChangement = statement.executeUpdate();
            statement.close();
            statement = conn.prepareStatement(DELETE_COMPANY);
            statement.setLong(1, id);
            nbChangement += statement.executeUpdate();
            conn.commit();
            conn.close();
            return nbChangement;
        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new DAOException("Erreur de base de donnée, et de connection.rollback()", se);
            }
            throw new DAOException("Erreur de base de donnée", se);
        }
    }


    @Override
    public ArrayList<Company> listeComplete() {
        try (Connection conn = ConnectionPoolDB.getInstance().open();
                Statement statement = conn.createStatement()) {
            ResultSet result = null;
            result = statement.executeQuery(SELECT_ALL);
            ArrayList<Company> list = TransformationResultSet.extraireListeCompleteCompany(result);
            return list;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    /**
     * Introduit les arguments dans le preparedstatement (fonction list).
     * @param statement :PreparedStatement en cours
     * @param numPage : nombre de page
     * @param nbElement : nombre d'élément par page
     * @throws SQLException : SQL exception possible
     */
    private void setStatementListe(PreparedStatement statement, int numPage, int nbElement) throws SQLException {
        statement.setInt(1, nbElement);
        statement.setInt(2, numPage * nbElement);
    }

}
