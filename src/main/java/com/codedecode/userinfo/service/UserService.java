package com.codedecode.userinfo.service;

import com.codedecode.userinfo.dto.UserDTO;
import com.codedecode.userinfo.entity.User;
import com.codedecode.userinfo.mapper.UserMapper;
import com.codedecode.userinfo.repo.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepos userRepos;

    public UserDTO addUser(UserDTO userDTO) {
        User savedUser = userRepos.save(UserMapper.INSTANCE.mapUserDTOToUser(userDTO));
        return UserMapper.INSTANCE.mapUserToUserDTO(savedUser);
    }

    public ResponseEntity<UserDTO> fetchUserDetailsById(Integer userId) {
        Optional<User> fetchedUser = userRepos.findById(userId);
        if (fetchedUser.isPresent()) {
            return new ResponseEntity<>(UserMapper.INSTANCE.mapUserToUserDTO(fetchedUser.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
