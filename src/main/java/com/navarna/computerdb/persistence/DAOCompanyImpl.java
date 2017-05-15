package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.navarna.computerdb.exception.DAOException;
import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

@Repository
@Scope("singleton")
public class DAOCompanyImpl implements DAOCompany {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOCompanyImpl.class);
    @Autowired
    private ConnectionSpringPool springDataSource;
    private static final String SELECT;
    private static final String SELECT_ALL;
    private static final String DELETE_COMPANY;

    static {
        SELECT = "SELECT id,name from company LIMIT ? OFFSET ?";
        SELECT_ALL = "SELECT id,name from company";
        DELETE_COMPANY = "DELETE from company where id = ?";
    }

    @Override
    public Page<Company> list(int numPage, int nbElement) {
        LOGGER.info("-------->list(numPage,nbElement) args: " + numPage + " - " + nbElement);
        try (Connection conn = springDataSource.open(); PreparedStatement statement = conn.prepareStatement(SELECT);) {
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
    public boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        try {
            Connection conn = springDataSource.open();
            PreparedStatement statement = conn.prepareStatement(DELETE_COMPANY);
            statement.setLong(1, id);
            int nbChangement = statement.executeUpdate();
            statement.close();
            conn.close();
            return nbChangement != 0;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public ArrayList<Company> listeComplete() {
        LOGGER.info("-------->listeComplete()");
        try (Connection conn = springDataSource.open(); Statement statement = conn.createStatement()) {
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
        LOGGER.info("-------->setStatementListe(statement,numPage,nbElement)args: " + numPage + " - " + nbElement);
        statement.setInt(1, nbElement);
        statement.setInt(2, numPage * nbElement);
    }

}
