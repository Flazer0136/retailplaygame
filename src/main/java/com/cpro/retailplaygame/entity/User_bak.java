//package com.cpro.retailplaygame.entity;
//
//import jakarta.persistence.*;
//
//// The @Entity annotation tells Java this is an entity
//// class that will be stored in the database
//@Entity
//@Table(name = "users")  // Links this class to the "users" table in the database
//public class User_bak {
//    // @Id marks this variable as the primary
//    // key for the User entity
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userID;
//    // These are regular variables to hold the user's info
//    private String username;
//    private String email;
//    private String firstname;
//    private String lastname;
//    private String passwordhash;
//    private String salt;
//
//    // Getters and Setters below are used to access
//    // or modify the values of the variables
//    public Long getUserID() {
//        return userID;
//    }
//
//    public void setUserID(Long userID) {
//        this.userID = userID;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public String getPasswordhash() {
//        return passwordhash;
//    }
//
//    public void setPasswordhash(String passwordhash) {
//        this.passwordhash = passwordhash;
//    }
//
//    public String getSalt() {
//        return salt;
//    }
//
//    public void setSalt(String salt) {
//        this.salt = salt;
//    }
//}