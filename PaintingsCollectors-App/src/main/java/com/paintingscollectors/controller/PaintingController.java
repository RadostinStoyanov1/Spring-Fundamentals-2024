package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDTO;
import com.paintingscollectors.service.PaintingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaintingController {

    private final PaintingService paintingService;
    private final UserSession userSession;

    public PaintingController(PaintingService paintingService, UserSession userSession) {
        this.paintingService = paintingService;
        this.userSession = userSession;
    }

    @ModelAttribute("paintingData")
    public AddPaintingDTO paintingData() {
        return new AddPaintingDTO();
    }

    @GetMapping("/add-painting")
    public String addPainting() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        return "add-painting";
    }

    @PostMapping("/add-painting")
    public String doAddPainting(
            @Valid AddPaintingDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("paintingData", data);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.paintingData", bindingResult);

            return "redirect:/add-painting";
        }

        boolean success = paintingService.create(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("paintingData", data);

            return "redirect:/add-painting";
        }

        return "redirect:/home";
    }

    @GetMapping("/paintings/remove/{id}")
    public String removePainting(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        paintingService.removePainting(id);

        return "redirect:/home";
    }

    @GetMapping("paintings/remove-favorite/{id}")
    public String removeFavoritePainting(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        paintingService.removeFavoritePainting(id);

        return "redirect:/home";
    }

    @GetMapping("/paintings/add-favorite/{id}")
    public String addFavoritePainting(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        paintingService.addFavoritePainting(id);

        return "redirect:/home";
    }

    @GetMapping("/paintings/vote/{id}")
    public String addVotedPainting(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        paintingService.addVotedPainting(id);

        return "redirect:/home";
    }
}
