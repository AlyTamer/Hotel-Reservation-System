package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.exceptions.UserException;
import com.aly.brightskies.task3.exceptions.UserExceptionMessages;
import com.aly.brightskies.task3.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;

    }

    public void updateUserRole(int id, Role newRole) {

        User user=userRepo.findById(id).orElseThrow(()-> new UserException(UserExceptionMessages.USER_NOT_FOUND));
        if (user != null) {
            user.setRole(newRole);
            userRepo.save(user);
            return;
        }
        throw new UserException(UserExceptionMessages.FAILED_TO_UPDATE_ROLE);


    }

    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            throw new UserException(UserExceptionMessages.NO_USERS_FOUND);
        }
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(user.getEmail());
            userDTO.setNumber(user.getNumber());
            userDTO.setUsername(user.getUserName());
            list.add(userDTO);
        }
        return list;
    }

    public void deleteById(int id) {
        try{
            userRepo.deleteById(id);
        }catch(Exception e) {
            throw new UserException(UserExceptionMessages.USER_NOT_FOUND);
        }

    }

    public boolean existsByUsername(String username) {
        return userRepo.existsByUserName(username);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
