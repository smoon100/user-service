package com.codedecode.userinfo.service;

import com.codedecode.userinfo.dto.UserDTO;
import com.codedecode.userinfo.entity.User;
import com.codedecode.userinfo.mapper.UserMapper;
import com.codedecode.userinfo.repo.UserRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.plaf.basic.BasicEditorPaneUI;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepos userRepos;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addUserShouldReturnUser() {
        // Arrange
        UserDTO userDTO = new UserDTO(1, "John Doe", "password", "123 Main St", "New York");
        User user = new User(1, "John Doe", "password", "123 Main St", "New York");
        when(userRepos.save(any(User.class))).thenReturn(user);
        // Act
        UserDTO savedUser = userService.addUser(userDTO);
        // Assert
        assertEquals(userDTO, UserMapper.INSTANCE.mapUserToUserDTO(user));
        assertEquals(user, UserMapper.INSTANCE.mapUserDTOToUser(userDTO));
        assertEquals(userDTO.getUserName(), savedUser.getUserName());
        assertEquals(userDTO.getUserId(), savedUser.getUserId());

        verify(userRepos, times(1)).save(any(User.class));
    }

    @Test
    void fetchUserDetailsByIdShouldReturnUser_ExistUserId() {
        // Arrange
        Integer userId = 1;
        UserDTO userDTO = new UserDTO(1, "John Doe", "password", "123 Main St", "New York");
        User user = new User(1, "John Doe", "password", "123 Main St", "New York");
        when(userRepos.findById(userId)).thenReturn(Optional.of(user));
        // Act
        ResponseEntity<UserDTO> response = userService.fetchUserDetailsById(userId);
        // Assert
        assertEquals(userDTO.getUserName(), response.getBody().getUserName());
        assertEquals(userDTO.getUserId(), response.getBody().getUserId());

        verify(userRepos, times(1)).findById(userId);
    }

    @Test
    void fetchUserDetailsByIdShouldReturnUser_NotExistUserId() {
        // Arrange
        Integer userId = 1;
        when(userRepos.findById(userId)).thenReturn(Optional.empty());
        // Act
        ResponseEntity<UserDTO> response = userService.fetchUserDetailsById(userId);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());

        verify(userRepos, times(1)).findById(userId);
    }
}
