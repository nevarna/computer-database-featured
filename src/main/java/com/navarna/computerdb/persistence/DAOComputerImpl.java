package com.navarna.computerdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.navarna.computerdb.exception.DAOException;
import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

@Repository
public class DAOComputerImpl implements DAOComputer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputerImpl.class);
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    private static final String INSERT;
    private static final String UPDATE;
    private static final String DELETE;
    private static final String DELETE_LIST;
    private static final String DELETE_COMPUTERS;
    private static final String SELECT_LIST;
    private static final String FIND_ID;
    private static final String FIND_NAME;
    private static final String FIND_COMPANY;
    private static final String COUNT;
    private static final String COUNT_NAME;
    private static final String COUNT_NAME_COMPANY;
    private static final String LIMIT_OFFSET;

    static {
        INSERT = "INSERT INTO computer VALUES ( ?, ?, ?, ?, ? )";
        UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
        DELETE = "DELETE FROM computer where id = ?";
        DELETE_LIST = "DELETE FROM computer where id in (";
        DELETE_COMPUTERS = "DELETE from computer where company_id = ?";
        SELECT_LIST = "SELECT * from computer left join company on company_id = company.id ORDER BY ";
        FIND_ID = "SELECT * from computer left join company on company_id = company.id where computer.id = ?";
        FIND_NAME = "SELECT * from computer left join company on company_id = company.id where computer.name LIKE ? ORDER BY ";
        FIND_COMPANY = "SELECT * from computer left join company on company_id = company.id where company.name = ? ORDER BY ";
        COUNT = "SELECT count(id) from computer";
        COUNT_NAME = "SELECT count(id) from computer where name = ?";
        COUNT_NAME_COMPANY = "SELECT count(computer.id) from computer left join company on company_id = company.id where company.name = ?";
        LIMIT_OFFSET = " LIMIT ? OFFSET ?";
    }

    @Override
    public boolean insert(Computer computer) {
        LOGGER.info("-------->insert(computer) args: " + computer);
        try {
            return jdbcTemplate.update(INSERT, setStatementUpdate(computer)) != 0;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean update(Computer computer) {
        LOGGER.info("-------->update(computer) args: " + computer);
        try {
            Object[] args = setStatementUpdate(computer);
            if ((Long) args[0] > 0) {
                return jdbcTemplate.update(UPDATE, args[1], args[2], args[3], args[4], args[0]) != 0;
            } else {
                throw new DAOException("Id n'est pas correct.");
            }
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        try {
            return jdbcTemplate.update(DELETE, id) != 0;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean deleteMultiple(long[] id) {
        LOGGER.info("-------->deleteMultiple()");
        String requeteComplete = ecrireRequeteDeleteList(id);
        try {
            return jdbcTemplate.update(requeteComplete) != 0;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean deleteCompany(long idCompany) {
        LOGGER.info("-------->deleteCompany()");
        try {
            return jdbcTemplate.update(DELETE_COMPUTERS, idCompany) != 0;

        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Page<Computer> list(int numPage, int nbElement, String typeOrder, String order) {
        LOGGER.info("-------->list(numPage,nbElement,typeOrder,order) args: " + numPage + " - " + nbElement + " - "
                + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(SELECT_LIST, typeOrder, order);
        try {
            return jdbcTemplate.query(requeteComplete, getArguments(nbElement, numPage * nbElement),
                    new ResultSetExtractor<Page<Computer>>() {
                        @Override
                        public Page<Computer> extractData(ResultSet arg0) throws SQLException, DataAccessException {
                            return TransformationResultSet.extraireDetailsComputers(arg0, numPage, nbElement);
                        }
                    });
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Optional<Computer> findById(long id) {
        LOGGER.info("-------->findById(id) args: " + id);
        try {
            return jdbcTemplate.query(FIND_ID, getArguments(id), new ResultSetExtractor<Optional<Computer>>() {
                @Override
                public Optional<Computer> extractData(ResultSet arg0) throws SQLException, DataAccessException {
                    return TransformationResultSet.extraireDetailsComputer(arg0);
                }
            });

        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Page<Computer> findByName(String name, int numPage, int nbElement, String typeOrder, String order) {
        LOGGER.info("-------->findByName(name,numPage,nbElement,typeOrder,order) args: " + name + " - " + numPage
                + " - " + nbElement + " - " + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(FIND_NAME, typeOrder, order);
        try {
            return jdbcTemplate.query(requeteComplete, getArguments(name+"%", nbElement, numPage * nbElement),
                    new ResultSetExtractor<Page<Computer>>() {
                        @Override
                        public Page<Computer> extractData(ResultSet arg0) throws SQLException, DataAccessException {
                            return TransformationResultSet.extraireDetailsComputers(arg0, numPage, nbElement);
                        }
                    });
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Page<Computer> findByCompany(String nameCompany, int numPage, int nbElement, String typeOrder,
            String order) {
        LOGGER.info("-------->findByNameCompany(nameCompany,numPage,nbElement,typeOrder,order) args: " + nameCompany
                + " - " + numPage + " - " + nbElement + " - " + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(FIND_COMPANY, typeOrder, order);
        try {
            return jdbcTemplate.query(requeteComplete, getArguments(nameCompany+"%", nbElement, numPage * nbElement),
                    new ResultSetExtractor<Page<Computer>>() {
                        @Override
                        public Page<Computer> extractData(ResultSet arg0) throws SQLException, DataAccessException {
                            return TransformationResultSet.extraireDetailsComputers(arg0, numPage, nbElement);
                        }
                    });
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int count() {
        LOGGER.info("-------->count()");
        try {
            return jdbcTemplate.queryForObject(COUNT, Integer.class);
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int countWithName(String name) {
        LOGGER.info("-------->countWithName(name) args: " + name);
        try {
            return jdbcTemplate.queryForObject(COUNT_NAME, Integer.class, name);
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int countWithNameCompany(String nameCompany) {
        LOGGER.info("-------->countWithNameCompany(nameCompany) args: " + nameCompany);
        try {
            return jdbcTemplate.queryForObject(COUNT_NAME_COMPANY, Integer.class, nameCompany);
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    /**
     * Ecrit la requête complete permettant de supprimer les computers.
     * @param id : tableau d'id à supprimer
     * @return String : requete contenant les id à suprimer.
     */
    private String ecrireRequeteDeleteList(long[] id) {
        LOGGER.info("-------->ecrireRequeteDeleteList(id)");
        String requeteDeleteMultiple = DELETE_LIST;
        for (int i = 0; i < id.length; i++) {
            requeteDeleteMultiple += id[i];
            if (i != id.length - 1) {
                requeteDeleteMultiple += ",";
            }
        }
        requeteDeleteMultiple += ")";
        return requeteDeleteMultiple;
    }

    /**
     * Ecris la requete complete selon le type d'ordre et l'arguments d'ordre.
     * @param debutRequete : debut de la requete
     * @param typeOrder : colone de order
     * @param order : type de colone order ASC ou DESC
     * @return String : la requete complete
     */
    private String ecrireRequeteBasique(String debutRequete, String typeOrder, String order) {
        LOGGER.info("-------->ecrireRequeteBasique(debutRequete,typeOrder,order) args: " + debutRequete + " - "
                + typeOrder + " - " + order);
        StringBuffer requeteComplete = new StringBuffer(debutRequete).append(typeOrder).append(" ").append(order)
                .append(LIMIT_OFFSET);
        return requeteComplete.toString();
    }

    /**
     * Introduit les arguments de la fonction dans le statement(fonction
     * update).
     * @param statement : Preparedstatement en cours
     * @param computer : computer à insérer
     * @throws SQLException : SQL exception possible
     */
    private Object[] setStatementUpdate(Computer computer) {
        LOGGER.info("-------->setStatementUpdate(statement,computer) args: " + computer);
        Object[] arguments = new Object[5];
        arguments[0] = computer.getId();
        arguments[1] = computer.getName();
        arguments[2] = computer.getIntroduced() != null ? Timestamp.valueOf(computer.getIntroduced().atStartOfDay())
                : (Timestamp) null;
        arguments[3] = computer.getDiscontinued() != null ? Timestamp.valueOf(computer.getDiscontinued().atStartOfDay())
                : (Timestamp) null;
        arguments[4] = (Long) (computer.getCompany().getId() != 0 ? computer.getCompany().getId() : null);
        return arguments;
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
