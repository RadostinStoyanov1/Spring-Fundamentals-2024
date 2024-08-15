package com.philately.service.impl;

import com.philately.config.UserSession;
import com.philately.model.dto.AddStampDTO;
import com.philately.model.dto.StampDTO;
import com.philately.model.entity.Paper;
import com.philately.model.entity.Stamp;
import com.philately.model.entity.User;
import com.philately.repository.StampRepository;
import com.philately.service.PaperService;
import com.philately.service.StampService;
import com.philately.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StampServiceImpl implements StampService {
    private final PaperService paperService;
    private final UserSession userSession;
    private final UserService userService;
    private final StampRepository stampRepository;

    public StampServiceImpl(PaperService paperService, UserSession userSession, UserService userService, StampRepository stampRepository) {
        this.paperService = paperService;
        this.userSession = userSession;
        this.userService = userService;
        this.stampRepository = stampRepository;
    }

    public boolean create(AddStampDTO addStampDTO) {
        if (!userSession.isLoggedIn()) {
            return false;
        }

        Optional<User> byId = userService.findById(userSession.id());
        if (byId.isEmpty()) {
            return false;
        }

        Paper paper = paperService.findPaperByName(addStampDTO.getPaperName().toString());

        Stamp stamp = new Stamp()
                .setName(addStampDTO.getName())
                .setDescription(addStampDTO.getDescription())
                .setPaper(paper)
                .setPrice(addStampDTO.getPrice())
                .setImageUrl(addStampDTO.getImageUrl())
                .setOwner(byId.get());

        stampRepository.save(stamp);

        return true;
    }

    @Override
    @Transactional
    public List<StampDTO> findAllUploadedByUserStamps() {
        List<Stamp> allStamps = stampRepository.findAllByOwner_Id(userSession.id());

        User user = userService.findById(userSession.id()).orElse(null);

        if (user == null) {
            return new ArrayList<>();
        }

        List<Stamp> purchasedStamps = user.getPurchasedStamps();

        for (Stamp purchasedStamp : purchasedStamps) {
            Long purchasedStampIdid = purchasedStamp.getId();
            Stamp stamp = stampRepository.findById(purchasedStampIdid).orElse(null);
            if (stamp != null) {
                allStamps.remove(stamp);
            }
        }

        return allStamps
                .stream()
                .map(this::convertStampToStampDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<StampDTO> findAllOfferedStamps() {
        return stampRepository.findAll()
                .stream()
                .filter(s -> s.getOwner().getId() != userSession.id())
                .map(this::convertStampToStampDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<StampDTO> findAllWishListStamps() {
        User user = userService.findById(userSession.id()).orElse(null);
        if (user == null) {
            return new ArrayList<StampDTO>();
        }
        return user.getWishedStamps()
                .stream()
                .map(this::convertStampToStampDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<StampDTO> findAllPurchasedStamps() {
        User user = userService.findById(userSession.id()).orElse(null);
        if (user == null) {
            return new ArrayList<StampDTO>();
        }
        return user.getPurchasedStamps()
                .stream()
                .map(this::convertStampToStampDTO)
                .toList();
    }

    @Override
    @Transactional
    public void moveStampToWishList(long id) {
        Stamp stamp = stampRepository.findById(id).orElse(null);
        User user = userService.findById(userSession.id()).orElse(null);

        if (user == null || stamp == null) {
            return;
        }

        user.getWishedStamps().add(stamp);
        userService.saveUser(user);
    }

    @Override
    @Transactional
    public void removeFromWishList(long id) {
        Stamp stamp = stampRepository.findById(id).orElse(null);
        User user = userService.findById(userSession.id()).orElse(null);

        if (user == null || stamp == null) {
            return;
        }

        user.getWishedStamps().remove(stamp);
        userService.saveUser(user);
    }

    @Override
    @Transactional
    public void purchaseAllFromWishList() {
        User user = userService.findById(userSession.id()).orElse(null);

        if (user == null) {
            return;
        }

        List<Stamp> wishedStamps = user.getWishedStamps();

        for (Stamp wishedStamp : wishedStamps) {
            wishedStamp.setOwner(user);
            user.getPurchasedStamps().add(wishedStamp);
            stampRepository.save(wishedStamp);
            userService.saveUser(user);
        }

        user.setWishedStamps(new ArrayList<>());
        userService.saveUser(user);
    }

    private StampDTO convertStampToStampDTO(Stamp stamp) {
        return new StampDTO()
                .setName(stamp.getName())
                .setId(stamp.getId())
                .setDescription(stamp.getDescription())
                .setPrice(stamp.getPrice())
                .setImageUrl(stamp.getImageUrl())
                .setPaperEnum(stamp.getPaper().getPaperName())
                .setOwnerId(stamp.getOwner().getId())
                .setUsername(stamp.getOwner().getUsername());
    }
}
