package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PaintingInfoDTO {
    private Long id;
    private String name;
    private String author;
    private String url;
    private StyleName styleName;
    private User owner;
    private boolean isFavorite;
    private Integer votes;

    public PaintingInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public PaintingInfoDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaintingInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public PaintingInfoDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PaintingInfoDTO setUrl(String url) {
        this.url = url;
        return this;
    }

    public StyleName getStyleName() {
        return styleName;
    }

    public PaintingInfoDTO setStyleName(StyleName styleName) {
        this.styleName = styleName;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public PaintingInfoDTO setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public PaintingInfoDTO setFavorite(boolean favorite) {
        isFavorite = favorite;
        return this;
    }

    public Integer getVotes() {
        return votes;
    }

    public PaintingInfoDTO setVotes(Integer votes) {
        this.votes = votes;
        return this;
    }
}
