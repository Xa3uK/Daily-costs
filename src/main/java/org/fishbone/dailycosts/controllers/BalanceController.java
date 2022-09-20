package org.fishbone.dailycosts.controllers;

import java.util.Optional;
import javax.validation.Valid;
import org.fishbone.dailycosts.dto.RevenueDTO;
import org.fishbone.dailycosts.models.Revenue;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.services.BalanceService;
import org.fishbone.dailycosts.services.PersonDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/balance")
public class BalanceController {

    BalanceService balanceService;
    PersonDetailsService personDetailsService;
    ModelMapper modelMapper;

    public BalanceController(BalanceService balanceService, PersonDetailsService personDetailsService,
                             ModelMapper modelMapper) {
        this.balanceService = balanceService;
        this.personDetailsService = personDetailsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String show(Model model) {
        User user = personDetailsService.findUserByLogin(personDetailsService.getCurrentUserLogin()).get();

        model.addAttribute("revenues", balanceService.findRevenueByUserId(user.getId()));
        model.addAttribute("RevenueDTO", new RevenueDTO());

        return "balance";
    }

    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("RevenueDTO") @Valid RevenueDTO revenueDTO,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            User user = personDetailsService.findUserByLogin(personDetailsService.getCurrentUserLogin()).get();

            model.addAttribute("revenues", balanceService.findRevenueByUserId(user.getId()));
            return "/balance";
        }

        revenueDTO.setAmount(revenueDTO.getAmount().replace(",", "."));
        Revenue revenue = modelMapper.map(revenueDTO, Revenue.class);

        String userLogin = personDetailsService.getCurrentUserLogin();
        Optional<User> user = personDetailsService.findUserByLogin(userLogin);
        balanceService.save(revenue, user.get());

        return "redirect:/balance";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {

        balanceService.deleteRevenueById(id);

        return "redirect:/balance";
    }

}
