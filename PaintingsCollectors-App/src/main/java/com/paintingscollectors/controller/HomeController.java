package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDTO;
import com.paintingscollectors.model.dto.PaintingInfoDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.service.PaintingService;
import com.paintingscollectors.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private final UserSession userSession;
    private final UserService userService;
    private final PaintingService paintingService;

    public HomeController(UserSession userSession, UserService userService, PaintingService paintingService) {
        this.userSession = userSession;
        this.userService = userService;
        this.paintingService = paintingService;
    }

    @GetMapping("/")
    public String nonLoggedIndex() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    @Transactional
    public String loggedInIndex(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        List<PaintingInfoDTO> paintings = userService.findUserPaintings(userSession.id());

        List<PaintingInfoDTO> favoritePaintings = userService.findUserFavoritePaintings(userSession.id());
        List<PaintingInfoDTO> allPaintings = paintingService.findAllPaintings();

        List<PaintingInfoDTO> orderedPaintings = paintingService.findAllPaintingsOrdered();

        model.addAttribute("myPaintings", paintings);
        model.addAttribute("myFavorites", favoritePaintings);
        model.addAttribute("otherPaintings", allPaintings);
        model.addAttribute("mostRatedPaintings", orderedPaintings);

        return "home";
    }
}
