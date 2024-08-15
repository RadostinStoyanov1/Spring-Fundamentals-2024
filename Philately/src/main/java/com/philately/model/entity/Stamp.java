package com.philately.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stamps")
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne(optional = false)
    private Paper paper;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private String imageUrl;
    @ManyToOne
    private User owner;

    public Stamp() {
    }

    public Long getId() {
        return id;
    }

    public Stamp setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Stamp setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Stamp setDescription(String description) {
        this.description = description;
        return this;
    }

    public Paper getPaper() {
        return paper;
    }

    public Stamp setPaper(Paper paper) {
        this.paper = paper;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Stamp setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Stamp setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Stamp setOwner(User owner) {
        this.owner = owner;
        return this;
    }
}
