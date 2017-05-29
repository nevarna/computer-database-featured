package com.navarna.computerdb.persistence;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.navarna.computerdb.exception.DAOException;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

@Repository
@Scope("singleton")
public class DAOCompanyImpl implements DAOCompany {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOCompanyImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    private static final String SELECT;
    private static final String DELETE_COMPANY;

    static {
        SELECT = "from Company";
        DELETE_COMPANY = "DELETE Company where id = :id";
    }

    @Override
    public Page<Company> list(int numPage, int nbElement) {
        LOGGER.info("-------->list(numPage,nbElement) args: " + numPage + " - " + nbElement);
        try (Session session = sessionFactory.openSession()) {
            Query<Company> query = session.createQuery(SELECT, Company.class);
            query.setMaxResults(nbElement);
            query.setFirstResult(nbElement * numPage);
            Page<Company> page = new Page<Company>(numPage, nbElement);
            page.addList(query.list());
            return page;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            int nbChangement = session.createQuery(DELETE_COMPANY).setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
            return nbChangement != 0;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public ArrayList<Company> listeComplete() {
        LOGGER.info("-------->listeComplete()");
        try (Session session = sessionFactory.openSession()) {
            Query<Company> query = session.createQuery(SELECT, Company.class);
            ArrayList<Company> liste = new ArrayList<Company>();
            liste.addAll(query.list());
            return liste;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }
}
