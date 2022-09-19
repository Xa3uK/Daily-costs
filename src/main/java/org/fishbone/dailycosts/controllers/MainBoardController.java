package org.fishbone.dailycosts.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;
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
import org.springframework.web.bind.annotation.RequestParam;

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

    @SneakyThrows
    @GetMapping()
    public String mainBoard(Model model,
                            @RequestParam(required = false, defaultValue = "All") String category,
                            @RequestParam(required = false, defaultValue = "22-01-01") String from,
                            @RequestParam(required = false, defaultValue = "25-12-31") String to) {

        User user = personDetailsService.findUserByLogin(personDetailsService.getCurrentUserLogin()).get();
        List<Revenue> revenueList = balanceService.findRevenueByUserId(user.getId());
        List<Purchase> purchaseList = purchaseService.findPurchaseByUserId(user.getId());

        List<Purchase> purchasesWithFilter = purchaseService.findPurchaseByFilter(from, to, category, user.getId());

        double balance = revenueList.stream().map(Revenue::getAmount).mapToDouble(Double::doubleValue).sum();
        balance -= purchaseList.stream().map(Purchase::getPrice).mapToDouble(Double::doubleValue).sum();
        user.setBalance(balance);

        double expenses =
            purchasesWithFilter.stream().map(Purchase::getPrice).mapToDouble(Double::doubleValue).sum();

        model.addAttribute("purchases", purchasesWithFilter);
        model.addAttribute("expenses", expenses);
        model.addAttribute("user", user);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("category", category);

        return "main";
    }
}
