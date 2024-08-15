package com.philately.service;

import com.philately.model.dto.UserLoginDTO;
import com.philately.model.dto.UserRegisterDTO;
import com.philately.model.entity.User;

import java.util.Optional;

public interface UserService {
    public boolean register(UserRegisterDTO data);
    public boolean isUsernameUnique(String username);
    public boolean isEmailUnique(String email);
    public boolean login(UserLoginDTO data);
    public void logoutUser();
    public Optional<User> findById(Long id);
    public void saveUser(User user);

}
