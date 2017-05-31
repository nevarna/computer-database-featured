package com.navarna.computerdb.controller.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.navarna.computerdb.model.User;
import com.navarna.computerdb.service.ServiceUserImpl;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private ServiceUserImpl servUser;
    private ArrayList<String> demandeDeconnection;

    /**
     * Constructeur simple initilisant un paramêtre.
     */
    public UserDetailsServiceImpl() {
        demandeDeconnection = new ArrayList<String>();
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        LOGGER.info("-------->loadByUsername(name) args : " + name);
        if (demandeDeconnection.contains(name)) {
            demandeDeconnection.remove(name);
            throw new UsernameNotFoundException("deconnection");
        }
        Optional<User> user = servUser.findByName(name);
        if (user.isPresent()) {
            return authentificationUser(user.get());
        }
        throw new UsernameNotFoundException("Aucun utilisateur");
    }

    /**
     * Transforme un com.navarna.computerdb.model.User en
     * org.springframework.security.core.userdetails.User.
     * @param user : user a transformé
     * @return org.springframework.security.core.userdetails.User : user
     *         transformé
     */
    private org.springframework.security.core.userdetails.User authentificationUser(User user) {
        LOGGER.info("-------->authentificationUser(user) args : " + user);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                authoriterUser(user));
    }

    /**
     * Recupère une array List contenant les authorité d'un user.
     * @param user : user
     * @return List<GrantedAuthority> : liste d'authorité d 'un user
     */
    private List<GrantedAuthority> authoriterUser(User user) {
        LOGGER.info("-------->authoriterUser(user) args : " + user);
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        switch (user.getLevel()) {
        case 3:
            result.add(new SimpleGrantedAuthority("ADMINISTRATEUR"));
        case 2:
            result.add(new SimpleGrantedAuthority("UTILISATEUR"));
        case 1:
            result.add(new SimpleGrantedAuthority("VISITEUR"));
        }
        return result;
    }

    /**
     * Ajoute le nom de l'user pour faire une deconnection.
     * @param nameUser : nom de l'user à deconnecter
     */
    public void deconnectionManuel(String nameUser) {
        LOGGER.info("-------->deconnectionManuel(nameUser) args : " + nameUser);
        if (!demandeDeconnection.contains(nameUser)) {
            demandeDeconnection.add(nameUser);
        }
    }

}
