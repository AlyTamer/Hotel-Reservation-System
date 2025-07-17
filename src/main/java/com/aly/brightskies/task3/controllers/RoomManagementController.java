package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.entities.Status;
import com.aly.brightskies.task3.repositories.ReservationRepo;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomManagementController {
    private final ReservationRepo reservationRepo;
    private final UserRepo userRepo;
    private final RoomRepo roomRepo;
    @Autowired
    public RoomManagementController(ReservationRepo rvr, UserRepo ur, RoomRepo rr) {
        this.reservationRepo=rvr;
        this.userRepo=ur;
        this.roomRepo=rr;
    }
    @GetMapping
    public List<Room> getRooms(@RequestParam(required = false) String type) {
        if (type != null) {
            return roomRepo.findAllByRoomType(type);
        } else {
            return roomRepo.findAllByStatus(Status.AVAILABLE);
        }
    }

}
