package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.entities.Reservation;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.repositories.ReservationRepo;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationRepo reservationRepo;
    private final UserRepo userRepo;
    private final RoomRepo roomRepo;
    @Autowired
    public ReservationController(ReservationRepo rvr, UserRepo ur, RoomRepo rr) {
        this.reservationRepo=rvr;
        this.userRepo=ur;
        this.roomRepo=rr;
    }

@GetMapping("/reservation")
    public List<Reservation> getReservations(@RequestParam User user){
    return reservationRepo.getAllByUserId(user);

}
    @PostMapping("/reservation")
    public void createReservation(@RequestParam Reservation reservation){
        reservationRepo.save(reservation);
    }
    @PostMapping("/reservation/delete")
    public void deleteReservation(@RequestParam Reservation reservation){
        reservationRepo.deleteById(reservation.getId());
    }
    @PostMapping("/reservation/update")
    public void updateReservation(@RequestParam int id,
                            @RequestParam Reservation newReservation){
        Reservation r= reservationRepo.getById(id);
        r.setRoomId(newReservation.getRoomId());
        r.setCheckInDate(newReservation.getCheckInDate());
        r.setCheckOutDate(newReservation.getCheckOutDate());


    }


}
