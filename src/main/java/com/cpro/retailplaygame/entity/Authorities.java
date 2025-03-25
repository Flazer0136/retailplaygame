package com.cpro.retailplaygame.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    private String authority;

    // Getters and setters
    public Long getAuthorityID() {
        return authorityID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}