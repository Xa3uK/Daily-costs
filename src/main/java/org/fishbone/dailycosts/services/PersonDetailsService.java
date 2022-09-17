package org.fishbone.dailycosts.services;

import java.util.Optional;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.UserRepository;
import org.fishbone.dailycosts.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public PersonDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PersonDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found ((");
        } else {
            return new PersonDetails(user.get());
        }
    }

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
