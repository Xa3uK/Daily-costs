package org.fishbone.dailycosts.controllers;

import java.util.Optional;
import org.fishbone.dailycosts.dto.PurchaseDTO;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.services.PersonDetailsService;
import org.fishbone.dailycosts.services.PurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    ModelMapper modelMapper;
    PersonDetailsService personDetailsService;
    PurchaseService purchaseService;

    public PurchaseController(ModelMapper modelMapper, PersonDetailsService personDetailsService,
                              PurchaseService purchaseService) {
        this.modelMapper = modelMapper;
        this.personDetailsService = personDetailsService;
        this.purchaseService = purchaseService;
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("PurchaseDTO") PurchaseDTO purchaseDTO) {

        purchaseDTO.setPrice(purchaseDTO.getPrice().replace(",", "."));
        Purchase purchase = modelMapper.map(purchaseDTO, Purchase.class);

        String userLogin = personDetailsService.getCurrentUserLogin();
        Optional<User> user = personDetailsService.findUserByLogin(userLogin);
        purchaseService.save(purchase, user.get());

        return "redirect:/main";
    }
}
