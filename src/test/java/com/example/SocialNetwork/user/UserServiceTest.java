package com.example.SocialNetwork.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserByEmail() {

        String email = "test@example.com";
        User mockUser = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        User retrievedUser = userService.getUserByEmail(email);

        assertEquals(mockUser, retrievedUser);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testDeleteUser() {
        Integer userId = 1;
        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
