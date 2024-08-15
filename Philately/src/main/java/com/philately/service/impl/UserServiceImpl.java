package com.philately.service.impl;

import com.philately.config.UserSession;
import com.philately.model.dto.UserLoginDTO;
import com.philately.model.dto.UserRegisterDTO;
import com.philately.model.entity.User;
import com.philately.repository.UserRepository;
import com.philately.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserSession userSession;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserSession userSession, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(UserRegisterDTO data) {
        Optional<User> currentUser = userRepository
                .findByUsernameOrEmail(data.getUsername(), data.getEmail());

        if (currentUser.isPresent()) {
            return false;
        }

        User foundUser = modelMapper.map(data, User.class);
        foundUser.setPassword(passwordEncoder.encode(foundUser.getPassword()));

        userRepository.save(foundUser);

        return true;

    }

    @Override
    public boolean isUsernameUnique(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }

    public boolean login(UserLoginDTO data) {
        Optional<User> byUsername = userRepository.findByUsername(data.getUsername());

        if (byUsername.isEmpty()) {
            return false;
        }

        boolean passMatch = passwordEncoder
                .matches(data.getPassword(), byUsername.get().getPassword());

        if (!passMatch) {
            return false;
        }

        userSession.login(byUsername.get().getId(), data.getUsername());

        return true;
    }

    @Override
    public void logoutUser() {
        User user = userRepository.findById(userSession.id()).orElse(null);
        if (user != null) {
            user.setWishedStamps(new ArrayList<>());
            userRepository.save(user);
        }
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }


}
