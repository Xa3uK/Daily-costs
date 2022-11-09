package org.fishbone.dailycosts.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    UserRepository userRepository;
    RegistrationService registrationService;

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationService(userRepository, new BCryptPasswordEncoder());
    }

    @Test
    void testRegister() {
        User user = new User();
        user.setName("Valera");
        user.setPass("simplePassword");

        registrationService.register(user);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();

        assertEquals(user, capturedUser);
        assertEquals(capturedUser.getRole(), "ROLE_USER");
        assertNotEquals(user.getPass(), capturedUser.getEncodedPassword());
    }
}