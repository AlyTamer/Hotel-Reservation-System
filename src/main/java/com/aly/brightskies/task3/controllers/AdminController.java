package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Reservation;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.services.ReservationService;
import com.aly.brightskies.task3.services.RoomService;
import com.aly.brightskies.task3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final UserService userService;


    @Autowired
    public AdminController(RoomService roomService,
                           ReservationService reservationService,
                           UserService userService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody Room r) {
        Room newRoom = roomService.createNewRoom(r);
        return ResponseEntity.ok(newRoom);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable int id,
                                              @RequestBody Room r) {
        Room current = roomService.updateRoom(id,r);
        return ResponseEntity.ok(current);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }

    @PostMapping("/reservations/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation r) {
        Reservation newRes=reservationService.createReservation(r);
        return ResponseEntity.ok(newRes);
    }
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @PutMapping("/users/{id}/role")
    public void changeUserRole(@PathVariable int id,
                                               @RequestParam Role newRole) {
        try {
            userService.updateUserRole(id, newRole);
            System.out.println("Changed role to "+newRole);
        } catch (Exception e) {
            System.out.println("Couldnt change role");
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteById(id);
    }

}
