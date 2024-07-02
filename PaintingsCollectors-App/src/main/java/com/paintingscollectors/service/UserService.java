package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDTO;
import com.paintingscollectors.model.dto.PaintingInfoDTO;
import com.paintingscollectors.model.dto.UserLoginDTO;
import com.paintingscollectors.model.dto.UserRegisterDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
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

    @Transactional
    public List<PaintingInfoDTO> findUserPaintings(Long id) {
        Optional<User> user = userRepository.findById(id);
        List<PaintingInfoDTO> paintings = user.get().getPaintings()
                .stream()
                .map(painting -> modelMapper.map(painting, PaintingInfoDTO.class))
                .collect(Collectors.toList());
        return paintings;
    }

    @Transactional
    public List<PaintingInfoDTO> findUserFavoritePaintings(Long id) {
        Optional<User> user = userRepository.findById(id);
        List<PaintingInfoDTO> paintings = user.get().getFavoritePaintings()
                .stream()
                .map(painting -> modelMapper.map(painting, PaintingInfoDTO.class))
                .collect(Collectors.toList());
        return paintings;
    }
}
