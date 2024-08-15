package com.philately.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @OneToMany
    private List<Stamp> wishedStamps;
    @OneToMany
    private List<Stamp> purchasedStamps;

    public User() {
        this.wishedStamps = new ArrayList<>();
        this.purchasedStamps = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Stamp> getWishedStamps() {
        return wishedStamps;
    }

    public User setWishedStamps(List<Stamp> wishedStamps) {
        this.wishedStamps = wishedStamps;
        return this;
    }

    public List<Stamp> getPurchasedStamps() {
        return purchasedStamps;
    }

    public User setPurchasedStamps(List<Stamp> purchasedStamps) {
        this.purchasedStamps = purchasedStamps;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
