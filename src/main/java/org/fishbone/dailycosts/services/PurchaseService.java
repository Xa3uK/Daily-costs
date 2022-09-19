package org.fishbone.dailycosts.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseService {

    PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> findPurchaseByUserId(int userId) {
        return purchaseRepository.findByUserId(userId);
    }

    @SneakyThrows
    public List<Purchase> findPurchaseByFilter( String dateFrom, String dateTo, String category, int userId){
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
    public void save(Purchase purchase, User user) {
        purchase.setDate(new Date());
        purchase.setUser(user);
        purchaseRepository.save(purchase);
    }
}
