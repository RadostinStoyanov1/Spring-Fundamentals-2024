package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDTO;
import com.paintingscollectors.model.dto.PaintingInfoDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.StyleRepository;
import com.paintingscollectors.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final UserRepository userRepository;
    private final StyleRepository styleRepository;
    private final UserSession userSession;
    private final ModelMapper modelMapper;

    public PaintingService(PaintingRepository paintingRepository, UserRepository userRepository, StyleRepository styleRepository, UserSession userSession, ModelMapper modelMapper) {
        this.paintingRepository = paintingRepository;
        this.userRepository = userRepository;
        this.styleRepository = styleRepository;
        this.userSession = userSession;
        this.modelMapper = modelMapper;
    }

    public boolean create(AddPaintingDTO data) {
        if (!userSession.isLoggedIn()) {
            return false;
        }

        Optional<User> byId = userRepository.findById(userSession.id());

        if (byId.isEmpty()) {
            return false;
        }

        Optional<Style> byName = styleRepository.findByName(data.getStyleName());

        if (byName.isEmpty()) {
            return false;
        }

        Painting painting = new Painting();
        painting.setName(data.getName());
        painting.setAuthor(data.getAuthor());
        painting.setStyle(byName.get());
        painting.setOwner(byId.get());
        painting.setUrl(data.getUrl());
        painting.setFavorite(false);
        painting.setVotes(0);

        paintingRepository.save(painting);

        return true;
    }

    public List<PaintingInfoDTO> findAllPaintings() {
        List<PaintingInfoDTO> paintings = paintingRepository.findAll()
                .stream()
                .map(painting -> modelMapper.map(painting, PaintingInfoDTO.class))
                .collect(Collectors.toList());
        return paintings;
    }

    public List<PaintingInfoDTO> findAllPaintingsOrdered() {
        List<Painting> orderedPaintings = paintingRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Painting::getVotes).reversed()
                        .thenComparing(Painting::getName))
                .toList();

        List<PaintingInfoDTO> result = orderedPaintings
                .stream()
                .map(painting -> modelMapper.map(painting, PaintingInfoDTO.class))
                .limit(2)
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public void removePainting(long id) {
        Painting toBeRemoved = paintingRepository.findById(id).get();
        User user = userRepository.findById(userSession.id()).get();
        if (!toBeRemoved.isFavorite()) {
            user.getPaintings().remove(toBeRemoved);
            paintingRepository.delete(toBeRemoved);
            userRepository.save(user);
        }
    }

    @Transactional
    public void removeFavoritePainting(long id) {
        Painting toBeRemoved = paintingRepository.findById(id).get();
        User user = userRepository.findById(userSession.id()).get();
        user.getFavoritePaintings().remove(toBeRemoved);
        userRepository.save(user);
    }


    @Transactional
    public void addFavoritePainting(long id) {
        User user = userRepository.findById(userSession.id()).get();
        Painting painting = paintingRepository.findById(id).get();
        boolean isPresent = checkForPainting(user.getFavoritePaintings(), painting);
        if (!isPresent) {
            user.getFavoritePaintings().add(painting);
            painting.setFavorite(true);
            paintingRepository.save(painting);
            userRepository.save(user);
        }
    }

    private boolean checkForPainting(List<Painting> favoritePaintings, Painting painting) {
        return favoritePaintings.contains(painting);
    }

    @Transactional
    public void addVotedPainting(long id) {
        User user = userRepository.findById(userSession.id()).get();
        Painting painting = paintingRepository.findById(id).get();
        boolean isPresent = checkForPainting(user.getRatedPaintings(), painting);
        boolean isOwned = checkForPainting(user.getPaintings(), painting);
        if (!isPresent && !isOwned) {
            user.getRatedPaintings().add(painting);
            painting.setVotes(painting.getVotes() + 1);
            paintingRepository.save(painting);
            userRepository.save(user);
        }
    }
}
