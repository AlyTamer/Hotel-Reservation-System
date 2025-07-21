package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.ReservationDTO;
import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Reservation;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.Status;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.repositories.ReservationRepo;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final RoomRepo roomRepo;
    private final ReservationRepo reservationRepo;
    private final UserRepo userRepo;
    @Autowired
    public UserService(RoomRepo roomRepo, ReservationRepo reservationRepo, UserRepo userRepo) {
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
        this.roomRepo = roomRepo;
    }

    public User updateUserRole(int id, Role newRole) {
        try {
            User user = userRepo.findById(id).get();
            user.setRole(newRole);
            userRepo.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
