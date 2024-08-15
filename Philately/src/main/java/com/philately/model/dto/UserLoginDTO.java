package com.philately.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {
    @NotBlank
    @Size(min = 3, max = 20, message = "username must be between 3 and 20 characters")
    private String username;
    @NotBlank
    @Size(min = 3, max = 20, message = "password must be between 3 and 20 characters")
    private String password;

    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserLoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
