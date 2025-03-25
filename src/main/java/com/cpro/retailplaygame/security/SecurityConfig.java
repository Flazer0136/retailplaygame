package com.cpro.retailplaygame.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .password(passwordEncoder().encode("test123")) // Encode password
                .roles("CUSTOMER")
                .build();

        UserDetails owner = User.builder()
                .username("owner")
                .password(passwordEncoder().encode("test123"))
                .roles("CUSTOMER", "OWNER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("test123"))
                .roles("CUSTOMER", "OWNER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(customer, owner, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(configurer ->
                configurer
                    .requestMatchers(HttpMethod.GET, "/api/products").hasRole("CUSTOMER")
                    .requestMatchers(HttpMethod.GET, "/api/products/**").hasRole("CUSTOMER")
                    .requestMatchers(HttpMethod.POST, "/api/products").hasRole("OWNER")
                    .requestMatchers(HttpMethod.PUT, "/api/products").hasRole("OWNER")
                    .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                    .requestMatchers("/register", "/login", "/css/**", "/js/**", "/error").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login") // Specify custom login page
                .loginProcessingUrl("/authenticate") // The URL where login form submits data
                .defaultSuccessUrl("/products", true)  // Redirect to products after login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable());

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

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery(
            "SELECT username, password, enabled FROM users WHERE username=?");
        manager.setAuthoritiesByUsernameQuery(
            "SELECT u.username, a.authority FROM users u " +
            "JOIN authorities a ON u.userID = a.userID WHERE u.username=?");
        return manager;
    }
}
