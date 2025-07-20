package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.ReservationDTO;
import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Reservation;
import com.aly.brightskies.task3.entities.Status;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.repositories.ReservationRepo;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserDTO findByName(String username) {
        User user = (User) userRepo.findByName(username);
        if (user == null) {
            return null; // or throw an exception
        }
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getNumber(),
                user.getPassword()
        );
    }
    public ReservationDTO updateReservation(int id,ReservationDTO res){
        Reservation currentRes= reservationRepo.findById(id).orElseThrow();
        currentRes.setCheckInDate(res.getCheckInDate());
        currentRes.setCheckOutDate(res.getCheckOutDate());
        currentRes.setStatus(Status.BOOKED);
        reservationRepo.save(currentRes);
        return new ReservationDTO(
                currentRes.getId(),
                currentRes.getUserId().getId(),
                currentRes.getRoomId().getId(),
                currentRes.getCheckInDate(),
                currentRes.getCheckOutDate(),
                currentRes.getStatus()
        );
    }
    public void cancelReservation(int id){
        if(!reservationRepo.existsById(id)){
            System.out.println("Reservation not found");
        }
        reservationRepo.deleteById(id);
    }
}
