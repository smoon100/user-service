package com.codedecode.userinfo.controller;

import com.codedecode.userinfo.dto.UserDTO;
import com.codedecode.userinfo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addUserShouldReturnUser() {
        // Arrange
        UserDTO userDTO = new UserDTO(1, "John Doe", "password", "123 Main St", "New York");
        when(userService.addUser(userDTO)).thenReturn(userDTO);
        // Act
        ResponseEntity<UserDTO> response = userController.addUser(userDTO);

        // Assert
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody().getUserId() == userDTO.getUserId();

        verify(userService, times(1)).addUser(userDTO);
    }

    @Test
    public void fetchUserDetailsByIdShouldReturnUser() {
        // Arrange
        Integer userId = 1;
        UserDTO userDTO = new UserDTO(1, "John Doe", "password", "123 Main St", "New York");
        when(userService.fetchUserDetailsById(userId)).thenReturn(new ResponseEntity<>(userDTO, HttpStatus.OK));
        // Act
        ResponseEntity<UserDTO> response = userController.fetchUserDetailsById(userId);
        // Assert
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().getUserId() == userDTO.getUserId();

        verify(userService, times(1)).fetchUserDetailsById(userId);
    }
}
