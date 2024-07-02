package com.paintingscollectors.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "styles")
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private StyleName name;
    private String description;

    public Style() {
    }

    public Style(StyleName styleName, String description) {
        this();
        this.name = styleName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Style setId(Long id) {
        this.id = id;
        return this;
    }

    public StyleName getName() {
        return name;
    }

    public Style setName(StyleName name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Style setDescription(String description) {
        this.description = description;
        return this;
    }
}
