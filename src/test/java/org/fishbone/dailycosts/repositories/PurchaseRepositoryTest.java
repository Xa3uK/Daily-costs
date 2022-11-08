package org.fishbone.dailycosts.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void savePurchase() {
        Purchase purchase = new Purchase("Milk", "Food", 12.0);

        purchaseRepository.save(purchase);
        List<Purchase> purchaseList = purchaseRepository.findAll();

        assertEquals(purchaseList.size(), 1);
        assertEquals(purchaseList.get(0).getProductName(), "Milk");
        assertEquals(purchaseList.get(0).getProductCategory(), "Food");
        assertEquals(purchaseList.get(0).getPrice(), 12.0);
        assertNotNull(purchaseList.get(0).getDate());
    }

    @Test
    void testFindPurchaseByUserId() {
        User user = new User();
        userRepository.save(user);
        Purchase purchase1 = new Purchase("Milk", "Food", 12.0, user);
        Purchase purchase2 = new Purchase("Sugar", "Food", 6.0, user);
        Purchase purchase3 = new Purchase("Meat", "Food", 18.0, user);
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);
        purchaseRepository.save(purchase3);

        User user2 = new User();
        userRepository.save(user2);
        Purchase purchase4 = new Purchase("Banana", "Food", 16.0, user2);
        purchaseRepository.save(purchase4);

        List<Purchase> purchaseList = purchaseRepository.findByUserId(user.getId());

        assertEquals(purchaseList.size(), 3);
        assertTrue(purchaseList.containsAll(List.of(purchase1, purchase2, purchase3)));
        assertFalse(purchaseList.contains(purchase4));
    }
}