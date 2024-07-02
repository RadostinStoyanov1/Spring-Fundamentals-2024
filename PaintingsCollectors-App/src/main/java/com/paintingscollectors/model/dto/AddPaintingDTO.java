package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.StyleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddPaintingDTO {
    @NotBlank
    @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!")
    private String name;
    @NotBlank
    @Size(min = 5, max = 30, message = "Name length must be between 5 and 30 characters!")
    private String author;
    @NotBlank(message = "Please enter valid image url!")
    @Size(max = 150, message = "Please enter valid image url!")
    private String url;
    @NotNull(message = "You must select a style!")
    private StyleName styleName;

    public AddPaintingDTO() {
    }

    public String getName() {
        return name;
    }

    public AddPaintingDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public AddPaintingDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public AddPaintingDTO setUrl(String url) {
        this.url = url;
        return this;
    }

    public StyleName getStyleName() {
        return styleName;
    }

    public AddPaintingDTO setStyleName(StyleName styleName) {
        this.styleName = styleName;
        return this;
    }
}
