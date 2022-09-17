package org.fishbone.dailycosts.repositories;

import java.util.Optional;
import org.fishbone.dailycosts.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);
}
