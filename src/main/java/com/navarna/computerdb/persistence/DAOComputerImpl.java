package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Optional;

import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

/**
 * @author excilys
 *
 */
/**
 * @author excilys
 *
 */
public final class DAOComputerImpl implements DAOComputer {
    private static final DAOComputerImpl INSTANCE;

    public static final String INSERT;
    public static final String UPDATE;
    public static final String DELETE;
    public static final String SELECT_LIST;
    public static final String SHOW_ID;
    public static final String SHOW_NAME;

    static {
        INSERT = "INSERT INTO computer VALUES ( ?, ?, ?, ?, ? )";
        UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
        DELETE = "DELETE FROM computer where id = ?";
        SELECT_LIST = "SELECT * from computer left join company on company_id = company.id LIMIT ? OFFSET ?";
        SHOW_ID = "SELECT * from computer left join company on company_id = company.id where computer.id = ?";
        SHOW_NAME = "SELECT * from computer left join company on company_id = company.id where computer.name = ? LIMIT ? OFFSET ?";
        INSTANCE = new DAOComputerImpl();
    }

    /**
     * Constructeur privé de la classe Singleton.
     */
    private DAOComputerImpl() {
    }

    public static DAOComputerImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public int insert(Computer computer) {
        try {
            Connection conn = ConnectionDb.getInstance().open();
            int result = 0;
            PreparedStatement statement = conn.prepareStatement(INSERT);
            setStatementInsert(statement, computer);
            result = statement.executeUpdate();
            statement.close();
            ConnectionDb.getInstance().close();
            return result;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int update(Computer computer) {
        try {
            Connection conn = ConnectionDb.getInstance().open();
            int result = 0;
            PreparedStatement statement = conn.prepareStatement(UPDATE);
            setStatementUpdate(statement, computer);
            result = statement.executeUpdate();
            statement.close();
            ConnectionDb.getInstance().close();
            return result;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public int delete(long id) {
        try {
            Connection conn = ConnectionDb.getInstance().open();
            int result = 0;
            PreparedStatement statement = conn.prepareStatement(DELETE);
            statement.setLong(1, id);
            result = statement.executeUpdate();
            statement.close();
            ConnectionDb.getInstance().close();
            return result;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Page<Computer> list(int numPage, int nbElement) {
        try {
            Connection conn = ConnectionDb.getInstance().open();
            ResultSet result = null;
            PreparedStatement statement = conn.prepareStatement(SELECT_LIST);
            setStatementListe(statement, numPage, nbElement);
            result = statement.executeQuery();
            Page<Computer> page = TransformationResultSet.extraireDetailsComputers(result, numPage, nbElement);
            statement.close();
            ConnectionDb.getInstance().close();
            return page;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Optional<Computer> showId(long id) {
        try {
            Connection conn = ConnectionDb.getInstance().open();
            ResultSet result = null;
            PreparedStatement statement = conn.prepareStatement(SHOW_ID);
            statement.setLong(1, id);
            result = statement.executeQuery();
            Optional<Computer> computer = TransformationResultSet.extraireDetailsComputer(result);
            statement.close();
            ConnectionDb.getInstance().close();
            return computer;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Page<Computer> showName(String name, int numPage, int nbElement) {
        try {
            Connection conn = ConnectionDb.getInstance().open();
            ResultSet result = null;
            PreparedStatement statement = conn.prepareStatement(SHOW_NAME);
            setStatementShowName(statement, name, numPage, nbElement);
            result = statement.executeQuery();
            Page<Computer> page = TransformationResultSet.extraireDetailsComputers(result, numPage, nbElement);
            statement.close();
            ConnectionDb.getInstance().close();
            return page;
        } catch (SQLException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    private void setStatementListe(PreparedStatement statement, int numPage, int nbElement) throws SQLException {
        statement.setInt(1, nbElement);
        statement.setInt(2, numPage * nbElement);
    }

    private void setStatementInsert(PreparedStatement statement, Computer computer) throws SQLException {
        if (computer.getId() != null) {
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
        if (computer.getCompany().getId() != null) {
            statement.setLong(5, computer.getCompany().getId());
        } else {
            statement.setNull(5, Types.BIGINT);
        }
    }

    private void setStatementUpdate(PreparedStatement statement, Computer computer) throws SQLException {
        if (computer.getId() != null) {
            statement.setLong(5, computer.getId());
        } else {
            statement.close();
            ConnectionDb.getInstance().close();
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
        if (computer.getCompany().getId() != null) {
            statement.setLong(4, computer.getCompany().getId());
        } else {
            statement.setNull(4, Types.BIGINT);
        }

    }

    private void setStatementShowName(PreparedStatement statement, String name, int numPage, int nbElement)
            throws SQLException {
        statement.setString(1, name);
        statement.setInt(2, nbElement);
        statement.setInt(3, numPage * nbElement);
    }
}
