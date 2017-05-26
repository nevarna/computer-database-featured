package com.navarna.computerdb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.navarna.computerdb.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userService;

    public UserDetailsServiceImpl userDetais() {
        return userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .addFilter(digestFilter())
        .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/dashboard").hasAnyAuthority("VISITEUR")
                .antMatchers("/addComputer**","/editComputer**").hasAnyAuthority("UTILISATEUR")
                .antMatchers("/dashboard/delete").hasAnyAuthority("ADMINISTRATEUR")
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and().exceptionHandling().accessDeniedPage("/erreur?message=accessDenied").authenticationEntryPoint(digestEntryPoint())
                
                ;
    }

    @Bean
    public DigestAuthenticationEntryPoint digestEntryPoint() {
        DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
        entryPoint.setRealmName("Contacts Realm via Digest Authentication");
        entryPoint.setKey("acegi");
        return entryPoint;
    }

    @Bean
    public DigestAuthenticationFilter digestFilter() {
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setUserDetailsService(userDetais());
        filter.setAuthenticationEntryPoint(digestEntryPoint());
        return filter;
    }
}
