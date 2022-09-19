package org.fishbone.dailycosts.repositories;

import java.util.Date;
import java.util.List;
import org.fishbone.dailycosts.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findByUserId(int userId);

    List<Purchase> findAllByDateBetweenAndUserId(Date from, Date to, int userId);
    List<Purchase> findAllByDateBetweenAndProductCategoryContainsAndUserId(Date from,
                                                                           Date to,
                                                                           String category,
                                                                           int userId);
}
