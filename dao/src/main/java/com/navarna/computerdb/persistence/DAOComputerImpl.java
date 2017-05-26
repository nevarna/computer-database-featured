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
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

@Repository
public class DAOComputerImpl implements DAOComputer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputerImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
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

    static {
        UPDATE = "UPDATE Computer set name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company where id = :id";
        DELETE = "DELETE Computer where id = :id";
        DELETE_LIST = "DELETE Computer where id in (";
        DELETE_COMPUTERS = "DELETE Computer where company_id = :id";
        SELECT_LIST = "select computer from Computer as computer left join computer.company ORDER BY ";
        FIND_ID = "select computer from Computer as computer left join computer.company where computer.id = :id";
        FIND_NAME = "select computer from Computer as computer left join computer.company where computer.name LIKE :name ORDER BY ";
        FIND_COMPANY = "select computer from Computer as computer left join computer.company where computer.company.name LIKE :name ORDER BY ";
        COUNT = "SELECT count(id) from Computer";
        COUNT_NAME = "SELECT count(id) from Computer where name LIKE :name";
        COUNT_NAME_COMPANY = "SELECT count(computer.id) from Computer as computer left join computer.company where computer.company.name LIKE :name";
    }

    @Override
    public boolean insert(Computer computer) {
        LOGGER.info("-------->insert(computer) args: " + computer);
        try (Session session = sessionFactory.openSession()) {
            Long changement = (Long) session.save(computer);
            return changement != 0;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean update(Computer computer) {
        LOGGER.info("-------->update(computer) args: " + computer);
        try(Session session = sessionFactory.openSession())  {
            session.beginTransaction();
            int changement = session.createQuery(UPDATE)
            .setParameter("name", computer.getName())
            .setParameter("introduced", computer.getIntroduced())
            .setParameter("discontinued", computer.getDiscontinued())
            .setParameter("company", computer.getCompany().getId() != 0? computer.getCompany().getId() : null)
            .setParameter("id", computer.getId())
            .executeUpdate();
            session.getTransaction().commit();
            return changement != 0;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
            
    }

    @Override
    public boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            int changement =  session.createQuery(DELETE).setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
            return changement != 0 ;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean deleteMultiple(long[] id) {
        LOGGER.info("-------->deleteMultiple()");
        String requeteComplete = ecrireRequeteDeleteList(id);
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            int changement =  session.createQuery(requeteComplete).executeUpdate();
            session.getTransaction().commit();
            return changement != 0 ;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public boolean deleteCompany(long idCompany) {
        LOGGER.info("-------->deleteCompany()");
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            int changement =  session.createQuery(DELETE_COMPUTERS).setParameter("id", idCompany).executeUpdate();
            session.getTransaction().commit();
            return changement != 0 ;
        } catch (DataAccessException se) {
            throw new DAOException("Erreur de base de donnée", se);
        }
    }

    @Override
    public Page<Computer> list(int numPage, int nbElement, String typeOrder, String order) {
        LOGGER.info("-------->list(numPage,nbElement,typeOrder,order) args: " + numPage + " - " + nbElement + " - "
                + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(SELECT_LIST, typeOrder, order);
        try (Session session = sessionFactory.openSession()) {
            Query<Computer> query = session.createQuery(requeteComplete,Computer.class);
            query.setMaxResults(nbElement);
            query.setFirstResult(nbElement * numPage);
            Page<Computer> page = new Page<Computer>(numPage, nbElement);
            page.addList(query.list());
            return page;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public Optional<Computer> findById(long id) {
        LOGGER.info("-------->findById(id) args: " + id);
        try (Session session = sessionFactory.openSession()) {
            Query<Computer> query = session.createQuery(FIND_ID,Computer.class);
            query.setParameter("id", id);
            Computer computer = query.uniqueResult();
            if(computer == null) {
                return Optional.empty();
            }
            else {
                return Optional.of(computer);
            }
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public Page<Computer> findByName(String name, int numPage, int nbElement, String typeOrder, String order) {
        LOGGER.info("-------->findByName(name,numPage,nbElement,typeOrder,order) args: " + name + " - " + numPage
                + " - " + nbElement + " - " + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(FIND_NAME, typeOrder, order);
        try (Session session = sessionFactory.openSession()) {
            Query<Computer> query = session.createQuery(requeteComplete,Computer.class);
            query.setParameter("name", name+"%");
            query.setMaxResults(nbElement);
            query.setFirstResult(nbElement * numPage);
            Page<Computer> page = new Page<Computer>(numPage, nbElement);
            page.addList(query.list());
            return page;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public Page<Computer> findByCompany(String nameCompany, int numPage, int nbElement, String typeOrder,
            String order) {
        LOGGER.info("-------->findByNameCompany(nameCompany,numPage,nbElement,typeOrder,order) args: " + nameCompany
                + " - " + numPage + " - " + nbElement + " - " + typeOrder + " - " + order);
        String requeteComplete = ecrireRequeteBasique(FIND_COMPANY, typeOrder, order);
        try (Session session = sessionFactory.openSession()) {
            Query<Computer> query = session.createQuery(requeteComplete,Computer.class);
            query.setParameter("name", nameCompany+"%");
            query.setMaxResults(nbElement);
            query.setFirstResult(nbElement * numPage);
            Page<Computer> page = new Page<Computer>(numPage, nbElement);
            page.addList(query.list());
            return page;
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public int count() {
        LOGGER.info("-------->count()");
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(COUNT,Long.class);
            Long reponse = query.uniqueResult();
            return reponse == null? 0 : reponse.intValue();
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public int countWithName(String name) {
        LOGGER.info("-------->countWithName(name) args: " + name);
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(COUNT_NAME,Long.class);
            query.setParameter("name", name+"%");
            Long reponse = query.uniqueResult();
            return reponse == null? 0 : reponse.intValue();
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
        }
    }

    @Override
    public int countWithNameCompany(String nameCompany) {
        LOGGER.info("-------->countWithNameCompany(nameCompany) args: " + nameCompany);
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(COUNT_NAME_COMPANY,Long.class);
            query.setParameter("name", nameCompany+"%");
            Long reponse = query.uniqueResult();
            return reponse == null? 0 : reponse.intValue();
        } catch (DataAccessException e) {
            throw new DAOException("erreur dans jdbc", e);
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
        StringBuffer requeteComplete = new StringBuffer(debutRequete).append("computer.").append(typeOrder).append(" ").append(order);
        return requeteComplete.toString();
    }

}
