package com.philately.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "papers")
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private PaperEnum paperName;
    @Column(nullable = false)
    private String description;

    public Paper() {
    }

    public Long getId() {
        return id;
    }

    public Paper setId(Long id) {
        this.id = id;
        return this;
    }

    public PaperEnum getPaperName() {
        return paperName;
    }

    public Paper setPaperName(PaperEnum paperName) {
        this.paperName = paperName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Paper setDescription(String description) {
        this.description = description;
        return this;
    }
}
