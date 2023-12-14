package com.example.SocialNetwork.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByEmail() {
        String userEmail = "test@example.com";
        User expectedUser = new User();
        when(userService.getUserByEmail(userEmail)).thenReturn(expectedUser);

        User result = userController.getUserByEmail(userEmail);

        assertEquals(expectedUser, result);
        verify(userService, times(1)).getUserByEmail(userEmail);
    }

    @Test
    public void testGetLoggedInUserProfile() {
        String userEmail = "test@example.com";
        User expectedUser = new User();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEmail, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.getUserByEmail(userEmail)).thenReturn(expectedUser);

        ResponseEntity<User> responseEntity = userController.getLoggedInUserProfile(authentication);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedUser, responseEntity.getBody());
        verify(userService, times(1)).getUserByEmail(userEmail);
    }

    @Test
    public void testDeleteUser() {
        int userId = 1;

        userController.deleteUser(userId);

        verify(userService, times(1)).deleteUser(userId);
    }
}
