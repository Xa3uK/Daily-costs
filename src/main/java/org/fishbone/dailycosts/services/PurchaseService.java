package org.fishbone.dailycosts.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;
import org.fishbone.dailycosts.dto.PurchaseDTO;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.PurchaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseService {
    private final String pattern = "yyyy-MM-dd";
    private final DateFormat df = new SimpleDateFormat(pattern);
    PurchaseRepository purchaseRepository;
    ModelMapper modelMapper;

    public PurchaseService(PurchaseRepository purchaseRepository, ModelMapper modelMapper) {
        this.purchaseRepository = purchaseRepository;
        this.modelMapper = modelMapper;
    }

    public List<Purchase> findPurchaseByUserId(int userId) {
        return purchaseRepository.findByUserId(userId);
    }

    @SneakyThrows
    public List<Purchase> findPurchaseByFilter(String dateFrom, String dateTo, String category, int userId) {
        Date fromDate = new SimpleDateFormat("yy-MM-dd").parse(dateFrom);
        Date toDate = new SimpleDateFormat("yy-MM-dd").parse(dateTo);

        return category.equals("All")
            ? purchaseRepository.findAllByDateBetweenAndUserId(fromDate, toDate, userId)
            : purchaseRepository.findAllByDateBetweenAndProductCategoryContainsAndUserId(fromDate, toDate, category,
            userId);
    }

    public void deletePurchaseById(int id) {
        purchaseRepository.deleteById(id);
    }

    @Transactional
    public void save(PurchaseDTO purchaseDTO, User user) {
        if (purchaseDTO.getDate().isBlank()) {
            purchaseDTO.setDate(df.format(new Date()));
        }

        purchaseDTO.setPrice(purchaseDTO.getPrice().replace(",", "."));

        Purchase purchase = modelMapper.map(purchaseDTO, Purchase.class);
        purchase.setUser(user);
        purchaseRepository.save(purchase);
    }
}
