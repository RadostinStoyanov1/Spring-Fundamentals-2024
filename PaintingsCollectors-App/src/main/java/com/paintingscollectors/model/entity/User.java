package com.paintingscollectors.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;

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
    @OneToMany(mappedBy = "owner")
    private List<Painting> paintings;
    @ManyToMany
    private List<Painting> favoritePaintings;
    @ManyToMany
    private List<Painting> ratedPaintings;

    public User() {
        this.paintings = new ArrayList<>();
        this.favoritePaintings = new ArrayList<>();
        this.ratedPaintings = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<Painting> getPaintings() {
        return paintings;
    }

    public User setPaintings(List<Painting> paintings) {
        this.paintings = paintings;
        return this;
    }

    public List<Painting> getFavoritePaintings() {
        return favoritePaintings;
    }

    public User setFavoritePaintings(List<Painting> favoritePaintings) {
        this.favoritePaintings = favoritePaintings;
        return this;
    }

    public List<Painting> getRatedPaintings() {
        return ratedPaintings;
    }

    public User setRatedPaintings(List<Painting> ratedPaintings) {
        this.ratedPaintings = ratedPaintings;
        return this;
    }
}
