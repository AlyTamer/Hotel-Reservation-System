package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.User;
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
        try {
            User user = userRepo.findById(id).isPresent() ? userRepo.findById(id).get() : null;
            if (user != null) {
            user.setRole(newRole);
            userRepo.save(user);
            return;}
            throw new Exception("User not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        List<User> users = userRepo.findAll();
        UserDTO userDTO = new UserDTO();
        try {
            for (User user : users) {
                userDTO.setEmail(user.getEmail());
                userDTO.setId(user.getId());
                userDTO.setNumber(user.getNumber());
                userDTO.setUsername(user.getName());
                list.add(userDTO);
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    public void deleteById(int id) {
        if(userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
        }

    }

}
