package com.navarna.computerdb.persistence;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.navarna.computerdb.exception.DAOException;
import com.navarna.computerdb.model.User;

@Repository
public class DAOUserImpl implements DAOUser {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOUserImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    private static final String DELETE;
    private static final String FIND_NAME;

    static {
        DELETE = "DELETE User where name = :name";
        FIND_NAME = "from User where name = :name";
    }

    @Override
    public boolean insert(User user) {
        LOGGER.info("-------->insert(computer) args: " + user);
        try (Session session = sessionFactory.openSession()) {
            Long changement = (Long) session.save(user);
            return changement != 0;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean delete(String name) {
        LOGGER.info("-------->delete(name) args: " + name);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            int changement = session.createQuery(DELETE).setParameter("name", name).executeUpdate();
            session.getTransaction().commit();
            return changement != 0;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Optional<User> findByName(String name) {
        LOGGER.info("-------->findById(name) args: " + name);
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(FIND_NAME, User.class);
            query.setParameter("name", name);
            User user = query.uniqueResult();
            if (user == null) {
                return Optional.empty();
            } else {
                return Optional.of(user);
            }
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

}
