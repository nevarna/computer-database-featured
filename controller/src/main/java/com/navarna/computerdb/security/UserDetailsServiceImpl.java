package com.navarna.computerdb.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private ServiceUserImpl servUser ;
    private ArrayList<String> demandeDeconnection ;
    
    public UserDetailsServiceImpl () {
        demandeDeconnection = new ArrayList<String>();
    }
    
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if(demandeDeconnection.contains(name)) {
         demandeDeconnection.remove(name);
         throw new UsernameNotFoundException("deconnection");
        }
        Optional<User> user = servUser.findByName(name);
        if (user.isPresent()) {
            return buildUserForAuthentication(user.get());
        }
        throw new UsernameNotFoundException("Aucun utilisateur");
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user) {
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                buildUserAuthority(user));
    }

    private List<GrantedAuthority> buildUserAuthority(User user) {
        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>();
        switch(user.getLevel()) {
        case 3:
            Result.add(new SimpleGrantedAuthority("ADMINISTRATEUR"));
        case 2 :
            Result.add(new SimpleGrantedAuthority("UTILISATEUR"));
        case 1 :
            Result.add(new SimpleGrantedAuthority("VISITEUR"));
        }
        return Result;
    }
    
    public void deconnectionManuel(String nameUser) {
        if(!demandeDeconnection.contains(nameUser)) {
            demandeDeconnection.add(nameUser);
        }
    }

}
