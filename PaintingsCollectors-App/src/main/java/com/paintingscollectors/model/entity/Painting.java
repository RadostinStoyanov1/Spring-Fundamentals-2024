package com.paintingscollectors.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "paintings")
public class Painting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    @ManyToOne(optional = false)
    private Style style;
    @ManyToOne
    private User owner;
    @Column(nullable = false)
    private String url;
    private boolean isFavorite;
    @Column(nullable = false)
    private Integer votes;

    public Painting() {
        this.isFavorite = false;
    }

    public Long getId() {
        return id;
    }

    public Painting setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Painting setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Painting setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Style getStyle() {
        return style;
    }

    public Painting setStyle(Style style) {
        this.style = style;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Painting setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Painting setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public Painting setFavorite(boolean favorite) {
        isFavorite = favorite;
        return this;
    }

    public Integer getVotes() {
        return votes;
    }

    public Painting setVotes(Integer votes) {
        this.votes = votes;
        return this;
    }
}
