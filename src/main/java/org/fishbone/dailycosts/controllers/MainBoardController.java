package org.fishbone.dailycosts.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.SneakyThrows;
import org.fishbone.dailycosts.dto.PurchaseDTO;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.Revenue;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.services.BalanceService;
import org.fishbone.dailycosts.services.PersonDetailsService;
import org.fishbone.dailycosts.services.PurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class MainBoardController {

    PersonDetailsService personDetailsService;
    BalanceService balanceService;
    PurchaseService purchaseService;
    ModelMapper modelMapper;

    public MainBoardController(PersonDetailsService personDetailsService, BalanceService balanceService,
                               PurchaseService purchaseService, ModelMapper modelMapper) {
        this.personDetailsService = personDetailsService;
        this.balanceService = balanceService;
        this.purchaseService = purchaseService;
        this.modelMapper = modelMapper;
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

        model.addAttribute("PurchaseDTO", new PurchaseDTO());

        return "main";
    }

    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("PurchaseDTO") @Valid PurchaseDTO purchaseDTO,
                      BindingResult bindingResult,
                      @RequestParam(required = false, defaultValue = "All") String category,
                      @RequestParam(required = false, defaultValue = "22-01-01") String from,
                      @RequestParam(required = false, defaultValue = "25-12-31") String to) {

        if (bindingResult.hasErrors()) {
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
            return "/main";
        }

        purchaseDTO.setPrice(purchaseDTO.getPrice().replace(",", "."));
        Purchase purchase = modelMapper.map(purchaseDTO, Purchase.class);

        String userLogin = personDetailsService.getCurrentUserLogin();
        Optional<User> user = personDetailsService.findUserByLogin(userLogin);
        purchaseService.save(purchase, user.get());

        return "redirect:/main";
    }

    @GetMapping("/delete/{id}")
    public String deletePurchase(@PathVariable("id") int id) {

        purchaseService.deletePurchaseById(id);

        return "redirect:/main";
    }
}