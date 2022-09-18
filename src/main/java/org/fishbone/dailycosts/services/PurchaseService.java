package org.fishbone.dailycosts.services;

import java.util.Date;
import java.util.List;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.Revenue;
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

    public List<Purchase> findAll(){
        return purchaseRepository.findAll();
    }

    public List<Purchase> findRevenueByUserId(int userId){
        return purchaseRepository.findByUserId(userId);
    }

    @Transactional
    public void save(Purchase purchase, User user){
        purchase.setDate(new Date());
        purchase.setUser(user);
        purchaseRepository.save(purchase);
    }
}
