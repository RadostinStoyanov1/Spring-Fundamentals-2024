package com.philately.controller;

import com.philately.config.UserSession;
import com.philately.service.StampService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserSession userSession;
    private final StampService stampService;

    public HomeController(UserSession userSession, StampService stampService) {
        this.userSession = userSession;
        this.stampService = stampService;
    }

    @GetMapping("/")
    public String index() {
        if(userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        model.addAttribute("myStamps", stampService.findAllUploadedByUserStamps());
        model.addAttribute("offeredStamps", stampService.findAllOfferedStamps());
        model.addAttribute("wishlistStamps", stampService.findAllWishListStamps());
        model.addAttribute("purchasedStamps", stampService.findAllPurchasedStamps());

        return "home";
    }
}
