package com.cpro.retailplaygame.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

// JDBC Auth
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager(dataSource);

        // Define your SQL query for loading users and authorities (roles)
        userDetailsService.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users WHERE username = ?");

        userDetailsService.setAuthoritiesByUsernameQuery(
                "SELECT u.username, a.authority FROM users u " +
                        "JOIN authorities a ON u.userID = a.userID WHERE u.username = ?");

        return userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/products").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/api/products/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.POST, "/api/products").hasRole("OWNER")
                .antMatchers(HttpMethod.PUT, "/api/products").hasRole("OWNER")
                .antMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();  // Use basic HTTP authentication

        return http.build();
    }
}
