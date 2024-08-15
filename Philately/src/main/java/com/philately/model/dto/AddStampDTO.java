package com.philately.model.dto;

import com.philately.model.entity.PaperEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AddStampDTO {
    @NotBlank
    @Size(min = 5, max = 20, message = "Name length must be between 5 and 20 characters!")
    private String name;
    @NotBlank
    @Size(min = 5, max = 25, message = "Name length must be between 5 and 25 characters!")
    private String description;
    @NotBlank
    @Size(max = 150)
    private String imageUrl;
    @Positive
    private Integer price;
    @NotNull(message = "You must select a type of paper!")
    private PaperEnum paperName;

    public AddStampDTO() {
    }

    public String getName() {
        return name;
    }

    public AddStampDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddStampDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AddStampDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public AddStampDTO setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public PaperEnum getPaperName() {
        return paperName;
    }

    public AddStampDTO setPaperName(PaperEnum paperName) {
        this.paperName = paperName;
        return this;
    }
}
