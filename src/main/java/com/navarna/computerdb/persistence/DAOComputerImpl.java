package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.navarna.computerdb.exception.DAOException;
import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

@Repository
public class DAOComputerImpl implements DAOComputer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputerImpl.class);
    @Autowired
    private ConnectionSpringPool springDataSource;
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
        FIND_NAME = "SELECT * from computer left join company on company_id = company.id where computer.name = ?  ORDER BY ";
        FIND_COMPANY = "SELECT * from computer left join company on company_id = company.id where company.name = ? ORDER BY ";
        COUNT = "SELECT count(id) from computer";
        COUNT_NAME = "SELECT count(id) from computer where name = ?";
        COUNT_NAME_COMPANY = "SELECT count(computer.id) from computer left join company on company_id = company.id where company.name = ?";
        LIMIT_OFFSET = " LIMIT ? OFFSET ?";
    }

    @Override
    public boolean insert(Computer computer) {
        LOGGER.info("-------->insert(computer) args: " + computer);
        try (Connection conn = springDataSource.open(); PreparedStatement statement = conn.prepareStatement(INSERT)) {
            int result = 0;
            setStatementInsert(statement, computer);
            result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean update(Computer computer) {
        LOGGER.info("-------->update(computer) args: " + computer);
        try (Connection conn = springDataSource.open(); PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            int result = 0;
            setStatementUpdate(statement, computer);
            result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        try (Connection conn = springDataSource.open(); PreparedStatement statement = conn.prepareStatement(DELETE)) {
            int result = 0;
            statement.setLong(1, id);
            result = statement.executeUpdate();
            return result != 0;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean deleteMultiple(long[] id) {
        LOGGER.info("-------->deleteMultiple()");
        String requeteComplete = ecrireRequeteDeleteList(id);
        try (Connection conn = springDataSource.open(); Statement statement = conn.createStatement()) {
            int result = 0;
            result = statement.executeUpdate(requeteComplete);
            return result != 0;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean deleteCompany(long idCompany) {
        Connection conn = springDataSource.open();
        try (PreparedStatement statement = conn.prepareStatement(DELETE_COMPUTERS)){
            statement.setLong(1, idCompany);
            return statement.executeUpdate() != 0;
            
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }


    @Override
    public Page<Computer> list(int numPage, int nbElement, String typeOrder, String order) {
        LOGGER.info("-------->list(numPage,nbElement,typeOrder,order) args: " + numPage + " - " + nbElement + " - "
                + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(SELECT_LIST, typeOrder, order);
        try (Connection conn = springDataSource.open();
                PreparedStatement statement = conn.prepareStatement(requeteComplete)) {
            ResultSet result = null;
            setStatementListe(statement, numPage, nbElement);
            result = statement.executeQuery();
            Page<Computer> page = TransformationResultSet.extraireDetailsComputers(result, numPage, nbElement);
            return page;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Optional<Computer> findById(long id) {
        LOGGER.info("-------->findById(id) args: " + id);
        try (Connection conn = springDataSource.open(); PreparedStatement statement = conn.prepareStatement(FIND_ID)) {
            ResultSet result = null;
            statement.setLong(1, id);
            result = statement.executeQuery();
            Optional<Computer> computer = TransformationResultSet.extraireDetailsComputer(result);
            return computer;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Page<Computer> findByName(String name, int numPage, int nbElement, String typeOrder, String order) {
        LOGGER.info("-------->findByName(name,numPage,nbElement,typeOrder,order) args: " + name + " - " + numPage
                + " - " + nbElement + " - " + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(FIND_NAME, typeOrder, order);
        try (Connection conn = springDataSource.open();
                PreparedStatement statement = conn.prepareStatement(requeteComplete)) {
            ResultSet result = null;
            setStatementFindByName(statement, name, numPage, nbElement);
            result = statement.executeQuery();
            Page<Computer> page = TransformationResultSet.extraireDetailsComputers(result, numPage, nbElement);
            return page;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Page<Computer> findByCompany(String nameCompany, int numPage, int nbElement, String typeOrder,
            String order) {
        LOGGER.info("-------->findByNameCompany(nameCompany,numPage,nbElement,typeOrder,order) args: " + nameCompany
                + " - " + numPage + " - " + nbElement + " - " + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(FIND_COMPANY, typeOrder, order);
        try (Connection conn = springDataSource.open();
                PreparedStatement statement = conn.prepareStatement(requeteComplete)) {
            ResultSet result = null;
            setStatementFindByName(statement, nameCompany, numPage, nbElement);
            result = statement.executeQuery();
            Page<Computer> page = TransformationResultSet.extraireDetailsComputers(result, numPage, nbElement);
            return page;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int count() {
        LOGGER.info("-------->count()");
        try (Connection conn = springDataSource.open(); Statement statement = conn.createStatement()) {
            ResultSet result = null;
            result = statement.executeQuery(COUNT);
            int retour = TransformationResultSet.extraireNombreElement(result);
            return retour;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int countWithName(String name) {
        LOGGER.info("-------->countWithName(name) args: " + name);
        try (Connection conn = springDataSource.open();
                PreparedStatement statement = conn.prepareStatement(COUNT_NAME)) {
            ResultSet result = null;
            statement.setString(1, name);
            result = statement.executeQuery();
            int retour = TransformationResultSet.extraireNombreElement(result);
            return retour;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int countWithNameCompany(String nameCompany) {
        LOGGER.info("-------->countWithNameCompany(nameCompany) args: " + nameCompany);
        try (Connection conn = springDataSource.open();
                PreparedStatement statement = conn.prepareStatement(COUNT_NAME_COMPANY)) {
            ResultSet result = null;
            statement.setString(1, nameCompany);
            result = statement.executeQuery();
            int retour = TransformationResultSet.extraireNombreElement(result);
            return retour;
        } catch (SQLException se) {
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
     * Introduit les arguments de la fonction dans le statement(fonction list).
     * @param statement : Preparedstatement en cours
     * @param numPage : numero de page
     * @param nbElement : nombre d'élément
     * @throws SQLException : SQL exception possible
     */
    private void setStatementListe(PreparedStatement statement, int numPage, int nbElement) throws SQLException {
        LOGGER.info("-------->setStatementListe(statement,numPage,nbElement) args: " + numPage + " - " + nbElement);
        statement.setInt(1, nbElement);
        statement.setInt(2, numPage * nbElement);
    }

    /**
     * Introduit les arguments de la fonction dans le statement(fonction
     * insert).
     * @param statement : Preparedstatement en cours
     * @param computer : computer à insérer
     * @throws SQLException : SQL exception possible
     */
    private void setStatementInsert(PreparedStatement statement, Computer computer) throws SQLException {
        LOGGER.info("-------->setStatementInsert(statement,computer) args: " + computer);
        if (computer.getId() != 0) {
            statement.setLong(1, computer.getId());
        } else {
            statement.setNull(1, Types.BIGINT);
        }
        statement.setString(2, computer.getName());
        if (computer.getIntroduced() != null) {
            statement.setTimestamp(3, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
        } else {
            statement.setNull(3, Types.TIMESTAMP);
        }
        if (computer.getDiscontinued() != null) {
            statement.setTimestamp(4, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
        } else {
            statement.setNull(4, Types.TIMESTAMP);
        }
        if (computer.getCompany().getId() != 0) {
            statement.setLong(5, computer.getCompany().getId());
        } else {
            statement.setNull(5, Types.BIGINT);
        }
    }

    /**
     * Introduit les arguments de la fonction dans le statement(fonction
     * update).
     * @param statement : Preparedstatement en cours
     * @param computer : computer à insérer
     * @throws SQLException : SQL exception possible
     */
    private void setStatementUpdate(PreparedStatement statement, Computer computer) throws SQLException {
        LOGGER.info("-------->setStatementUpdate(statement,computer) args: " + computer);
        if (computer.getId() != 0) {
            statement.setLong(5, computer.getId());
        } else {
            throw new DAOException("Update n'as pas de id arret de la fonction");
        }
        statement.setString(1, computer.getName());
        if (computer.getIntroduced() != null) {
            statement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
        } else {
            statement.setNull(2, Types.TIMESTAMP);
        }
        if (computer.getDiscontinued() != null) {
            statement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
        } else {
            statement.setNull(3, Types.TIMESTAMP);
        }
        if (computer.getCompany().getId() != 0) {
            statement.setLong(4, computer.getCompany().getId());
        } else {
            statement.setNull(4, Types.BIGINT);
        }

    }

    /**
     * Introduit les arguments de la fonction dans le statement(fonction
     * showName).
     * @param statement : Preparedstatement en cours
     * @param name : nom du computer
     * @param numPage : numero de page
     * @param nbElement : nombre d'élément
     * @throws SQLException : SQL exception possible
     */
    private void setStatementFindByName(PreparedStatement statement, String name, int numPage, int nbElement)
            throws SQLException {
        LOGGER.info("-------->setStatementFindByName(statement,name,numPage,nbElement) args: " + name + " - " + numPage
                + " - " + nbElement);
        statement.setString(1, name);
        statement.setInt(2, nbElement);
        statement.setInt(3, numPage * nbElement);
    }

}
