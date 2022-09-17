package org.fishbone.dailycosts.services;

import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user){
        user.setEncodedPassword(passwordEncoder.encode(user.getPass()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}
