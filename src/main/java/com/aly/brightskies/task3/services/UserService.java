package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.exceptions.ResourceNotFoundException;
import com.aly.brightskies.task3.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    @Autowired
    public UserService( UserRepo userRepo) {
        this.userRepo = userRepo;

    }

    public void updateUserRole(int id, Role newRole) {

            User user = userRepo.findById(id).isPresent() ? userRepo.findById(id).get() : null;
            if (user != null) {
            user.setRole(newRole);
            userRepo.save(user);
            return;
            }
            throw new ResourceNotFoundException("User not found");


    }

    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        List<User> users = userRepo.findAll();
            if(users.isEmpty()) {
                throw new ResourceNotFoundException("No users found");
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

        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        }
        else
            throw new ResourceNotFoundException("User not found");


    }

}
