package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.dto.ReservationDTO;
import com.aly.brightskies.task3.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Reservation Manager", description = "Manage,Create,& Delete reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(
            summary = "Get Reservations",
            description = "Retrieve all reservations for a specific user by userID"
    )
    @GetMapping("/reservation")
    public List<ReservationDTO> getReservations(@RequestParam int userID) {
        return reservationService.getUserReservation(userID);

    }

    @Operation(
            summary = "Create Reservation",
            description = "Create a new reservation with the provided details"
    )
    @PostMapping("/reservation")
    public void createReservation(@RequestBody ReservationDTO reservation) {
        reservationService.createReservation(reservation);
    }

    @Operation(
            summary = "Delete Reservation",
            description = "Delete a reservation by its reservation ID"
    )
    @PostMapping("/reservation/{id}")
    public void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }

    @Operation(
            summary = "Update Reservation",
            description = "Update an existing reservation with new details"
    )
    @PostMapping("/reservation/update")
    public ReservationDTO updateReservation(@RequestParam int id,
                                            @RequestBody ReservationDTO newReservation) {
        return reservationService.updateReservation(id, newReservation);

    }


}
