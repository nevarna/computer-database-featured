package com.navarna.computerdb.controller.security;

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
@ComponentScan("com.navarna.computerdb.controller.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(digestFilter()).authorizeRequests().antMatchers("/resources/**").permitAll()
                .antMatchers("/erreur**").permitAll().antMatchers("/api/**").permitAll().antMatchers("/dashboard")
                .hasAnyAuthority("VISITEUR").antMatchers("/addComputer**", "/editComputer**")
                .hasAnyAuthority("UTILISATEUR").antMatchers("/dashboard/delete").hasAnyAuthority("ADMINISTRATEUR").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/erreur?message=accessDenied").authenticationEntryPoint(digestEntryPoint())
                .and().csrf().ignoringAntMatchers("/api/**")
                ;
    }

    /**
     * Crée un bean digest entryPoint.
     * @return DigestAuthentificationEntryPoint : une entrypoint digest
     *         configurer
     */
    @Bean
    public DigestAuthenticationEntryPoint digestEntryPoint() {
        DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
        entryPoint.setRealmName("Contacts Realm via Digest Authentication");
        entryPoint.setKey("acegi");
        return entryPoint;
    }

    /**
     * Crée un bean digest filtre.
     * @return DigestAuthentificationFilter : un filtre digest.
     */
    @Bean
    public DigestAuthenticationFilter digestFilter() {
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setUserDetailsService(userService);
        filter.setAuthenticationEntryPoint(digestEntryPoint());
        return filter;
    }
}
