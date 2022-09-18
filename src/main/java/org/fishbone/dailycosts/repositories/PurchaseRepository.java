package org.fishbone.dailycosts.repositories;

import java.util.List;
import org.fishbone.dailycosts.models.Purchase;
import org.fishbone.dailycosts.models.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findByUserId(int userId);
}
