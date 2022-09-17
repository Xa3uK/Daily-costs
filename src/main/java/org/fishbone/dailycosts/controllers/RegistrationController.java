package org.fishbone.dailycosts.controllers;

import javax.validation.Valid;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.services.RegistrationService;
import org.fishbone.dailycosts.utils.PersonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    public RegistrationController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @PostMapping("/reg")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        LOGGER.debug("Start: register()");

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }

        LOGGER.debug("End: register()");
        registrationService.register(user);
        return "redirect:/auth/login";
    }
}
