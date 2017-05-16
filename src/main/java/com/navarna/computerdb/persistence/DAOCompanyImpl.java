package com.navarna.computerdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    private static String SELECT;
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
        try {
        Page<Company> page = jdbcTemplate.query(SELECT, getArguments(nbElement, nbElement * numPage),
                new ResultSetExtractor<Page<Company>>() {
                    @Override
                    public Page<Company> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        return TransformationResultSet.extraireListePartielleCompany(rs, numPage, nbElement);
                    }

                });
            return page;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        try {
        int nbChangement = jdbcTemplate.update(DELETE_COMPANY, getArguments(id), Integer.class);
        return nbChangement != 0;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public ArrayList<Company> listeComplete() {
        LOGGER.info("-------->listeComplete()");
        try {
        ArrayList<Company> liste = jdbcTemplate.query(SELECT_ALL,
                new ResultSetExtractor<ArrayList<Company>>() {
                    @Override
                    public ArrayList<Company> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        return TransformationResultSet.extraireListeCompleteCompany(rs);
                    }

                });
        return liste;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
        
    }

    /**
     * mets les arguments dans un tableau d'objets
     * @param objects : parametre d une requete
     * @return tableau contenant les arguments de la requête
     */
    private Object[] getArguments(Object... objects) {
        return objects;
    }
}
