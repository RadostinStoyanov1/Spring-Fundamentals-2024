package com.philately.controller;

import com.philately.config.UserSession;
import com.philately.model.dto.AddStampDTO;
import com.philately.service.StampService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/stamps")
public class StampController {

    private final UserSession userSession;
    private final StampService stampService;

    public StampController(UserSession userSession, StampService stampService) {
        this.userSession = userSession;
        this.stampService = stampService;
    }

    @ModelAttribute("stampData")
    public AddStampDTO recipeData() {
        return new AddStampDTO();
    }

    @GetMapping("/add-stamp")
    public String addRecipe(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/login";
        }

        if (!model.containsAttribute("stampData")) {
            model.addAttribute("stampData", new AddStampDTO());
        }

        return "add-stamp";
    }

    @PostMapping("/add-stamp")
    public String addTask(
            @Valid AddStampDTO addStampDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("stampData", addStampDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.stampData", bindingResult);

            return "redirect:/stamps/add-stamp";
        }

        boolean success = stampService.create(addStampDTO);

        if (!success) {
            redirectAttributes.addFlashAttribute("stampData", addStampDTO);

            return "redirect:/stamps/add-stamp";
        }

        return "redirect:/home";
    }

    @GetMapping("/add-stamp-wish/{id}")
    public String moveStampToWishList(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        stampService.moveStampToWishList(id);
        return "redirect:/home";
    }

    @GetMapping("/remove-stamp-wish/{id}")
    public String removeStampFromWishList(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        stampService.removeFromWishList(id);
        return "redirect:/home";
    }

    @GetMapping("/purchase-all")
    public String purchaseAllFromWishList() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        stampService.purchaseAllFromWishList();
        return "redirect:/home";
    }
}
