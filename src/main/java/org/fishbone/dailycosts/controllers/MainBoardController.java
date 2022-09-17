package org.fishbone.dailycosts.controllers;

import java.util.Optional;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.UserRepository;
import org.fishbone.dailycosts.services.PersonDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainBoardController {

    PersonDetailsService personDetailsService;

    public MainBoardController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @GetMapping()
    public String mainBoard(Model model) {
        String userLogin = personDetailsService.getCurrentUserLogin();

        personDetailsService.findUserByLogin(userLogin).ifPresent(value -> model.addAttribute("user", value));

        return "main";
    }
}
