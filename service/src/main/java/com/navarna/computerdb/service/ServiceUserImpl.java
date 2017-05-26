package com.navarna.computerdb.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navarna.computerdb.model.User;
import com.navarna.computerdb.persistence.DAOUserImpl;

@Service
public class ServiceUserImpl implements ServiceUser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUserImpl.class);
    @Autowired
    private DAOUserImpl dUserImpl;
 
    @Override
    public boolean insert(User user) {
        LOGGER.info("-------->insert(computer) args: " + user);
        return dUserImpl.insert(user);
    }

    @Override
    public boolean delete(String name) {
        LOGGER.info("-------->delete(name) args: " + name);
        return dUserImpl.delete(name);
    }

    @Override
    public Optional<User> findByName(String name) {
        LOGGER.info("-------->findById(name) args: " + name);
        return dUserImpl.findByName(name);
    }
}
