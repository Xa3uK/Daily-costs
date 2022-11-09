package org.fishbone.dailycosts.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.fishbone.dailycosts.models.User;
import org.fishbone.dailycosts.repositories.UserRepository;
import org.fishbone.dailycosts.security.PersonDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class PersonDetailsServiceTest {

    @Mock
    UserRepository userRepository;
    PersonDetailsService personDetailsService;

    @BeforeEach
    void setUp() {
        personDetailsService = new PersonDetailsService(userRepository);
    }

    @Test
    void testLoadUserByExistingUserName() {
        String existingUserLogin = "Valera";
        User user = new User();
        user.setLogin(existingUserLogin);
        when(userRepository.findByLogin(existingUserLogin)).thenReturn(Optional.of(user));

        PersonDetails personDetails = personDetailsService.loadUserByUsername(existingUserLogin);
        verify(userRepository).findByLogin(existingUserLogin);

        assertThat(personDetails).isNotNull();
        assertEquals(existingUserLogin, personDetails.getUsername());
    }

    @Test
    void testLoadUserByNonExistingUserName() {
        String nonExistingUserLogin = "NonExistingLogin";
        when(userRepository.findByLogin(nonExistingUserLogin)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class,
            () -> personDetailsService.loadUserByUsername(nonExistingUserLogin));

        verify(userRepository).findByLogin(nonExistingUserLogin);
        assertEquals(exception.getClass(), UsernameNotFoundException.class);
        assertEquals("User not found ((", exception.getMessage());
    }

    @Test
    void testFindUserByLogin() {
        String login = "Valera";
        personDetailsService.findUserByLogin(login);

        verify(userRepository).findByLogin(login);
    }
}