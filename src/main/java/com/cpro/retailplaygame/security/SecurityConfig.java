package com.cpro.retailplaygame.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// InMemory Auth
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// JDBC Auth
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    // JDBC Auth
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    // InMemory Auth
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails customer = User.builder()
                .username("customer")
                .password("{noop}test123")
                .roles("CUSTOMER")
                .build();

        UserDetails owner = User.builder()
                .username("owner")
                .password("{noop}test123")
                .roles("CUSTOMER", "OWNER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}test123")
                .roles("CUSTOMER", "OWNER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(customer, owner, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "api/products").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "api/products/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "api/products").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "api/products").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "api/products/**").hasRole("ADMIN")
                        .anyRequest().permitAll() // Allow all other endpoints to be accessed by any user
        );

        http.formLogin(Customizer.withDefaults()); // Enable default login page
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/products").hasRole("CUSTOMER")
//                .antMatchers(HttpMethod.GET, "/api/products/**").hasRole("CUSTOMER")
//                .antMatchers(HttpMethod.POST, "/api/products").hasRole("OWNER")
//                .antMatchers(HttpMethod.PUT, "/api/products").hasRole("OWNER")
//                .antMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();  // Use basic HTTP authentication
//
//        return http.build();
//    }
}
