package com.cpro.retailplaygame.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "users")  // Links this class to the "products" table in the database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String username;
    private String password;
    private int enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authorities> authorities = new ArrayList<>();

    // Better authority management
    public void addAuthority(String roleName) {
        if (this.authorities == null) {
            this.authorities = new ArrayList<>();
        }
        Authorities authority = new Authorities();
        authority.setAuthority(roleName);
        authority.setUser(this);
        this.authorities.add(authority);
    }

    // Default constructor required by Hibernate
    public User() {
        // No-argument constructor
    }

    public User(Long userID, String username, String password, int enabled) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}