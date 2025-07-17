package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.dto.ReservationDTO;
import com.aly.brightskies.task3.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


@GetMapping("/reservation")
    public List<ReservationDTO> getReservations(@RequestParam int userID){
    return reservationService.getUserReservation(userID);

}
    @PostMapping("/reservation")
    public void createReservation(@RequestParam ReservationDTO reservation){
        reservationService.createReservation(reservation);
    }
    @PostMapping("/reservation/delete")
    public void deleteReservation(@RequestParam int resId){
        reservationService.cancelReservation(resId);
    }
    @PostMapping("/reservation/update")
    public ReservationDTO updateReservation(@RequestParam int id,
                            @RequestBody ReservationDTO newReservation){
        return reservationService.updateReservation(id, newReservation);

    }


}
