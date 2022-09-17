package org.fishbone.dailycosts.repositories;

import java.util.List;
import java.util.Optional;
import org.fishbone.dailycosts.models.Revenue;
import org.fishbone.dailycosts.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Revenue, Integer> {

    List<Revenue> findByUserId(int userId);
}
