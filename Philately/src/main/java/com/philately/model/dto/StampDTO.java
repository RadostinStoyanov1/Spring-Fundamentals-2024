package com.philately.model.dto;

import com.philately.model.entity.Paper;
import com.philately.model.entity.PaperEnum;
import com.philately.model.entity.User;
import jakarta.persistence.*;

public class StampDTO {
    private Long id;
    private String name;
    private String description;
    private PaperEnum paperEnum;
    private Integer price;
    private String imageUrl;
    private Long ownerId;
    private String username;

    public StampDTO() {
    }

    public Long getId() {
        return id;
    }

    public StampDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StampDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StampDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public PaperEnum getPaperEnum() {
        return paperEnum;
    }

    public StampDTO setPaperEnum(PaperEnum paperEnum) {
        this.paperEnum = paperEnum;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public StampDTO setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public StampDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public StampDTO setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public StampDTO setUsername(String username) {
        this.username = username;
        return this;
    }
}
