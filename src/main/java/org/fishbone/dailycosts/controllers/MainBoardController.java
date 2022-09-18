package org.fishbone.dailycosts.controllers;

import java.util.List;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.Revenue;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.services.BalanceService;
import org.fishbone.dailycosts.services.PersonDetailsService;
import org.fishbone.dailycosts.services.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainBoardController {

    PersonDetailsService personDetailsService;
    BalanceService balanceService;
    PurchaseService purchaseService;

    public MainBoardController(PersonDetailsService personDetailsService, BalanceService balanceService,
                               PurchaseService purchaseService) {
        this.personDetailsService = personDetailsService;
        this.balanceService = balanceService;
        this.purchaseService = purchaseService;
    }

    @GetMapping()
    public String mainBoard(Model model) {
        User user = personDetailsService.findUserByLogin(personDetailsService.getCurrentUserLogin()).get();
        List<Revenue> revenueList = balanceService.findRevenueByUserId(user.getId());
        List<Purchase> purchaseList = purchaseService.findRevenueByUserId(user.getId());

        double balance = revenueList.stream().map(Revenue::getAmount).mapToDouble(Double::doubleValue).sum();
        balance -= purchaseList.stream().map(Purchase::getPrice).mapToDouble(Double::doubleValue).sum();
        user.setBalance(balance);

        model.addAttribute("purchases", purchaseList);
        model.addAttribute("user", user);

        return "main";
    }
}
