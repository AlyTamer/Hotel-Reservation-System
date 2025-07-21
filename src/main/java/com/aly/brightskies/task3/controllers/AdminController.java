package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.entities.Reservation;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.repositories.ReservationRepo;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.repositories.UserRepo;
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
    private final UserRepo userRepo;
    private final RoomRepo roomRepo;
    private final ReservationRepo reservationRepo;

    @Autowired
    public AdminController(RoomService roomService,
                           ReservationService reservationService,
                           UserService userService,
                           UserRepo userRepo,
                           RoomRepo roomRepo,
                           ReservationRepo reservationRepo) {
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.userService = userService;
        this.userRepo = userRepo;
        this.roomRepo = roomRepo;
        this.reservationRepo = reservationRepo;
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody Room r) {
        Room newRoom = roomRepo.save(r);
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
        return reservationRepo.findAll();
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }

    @PostMapping("/reservations/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation r) {
        Reservation newRes=reservationRepo.save(r);
        return ResponseEntity.ok(newRes);
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> changeUserRole(@PathVariable int id,
                                               @RequestParam Role newRole) {
        User updated;
        try {
            updated = userService.updateUserRole(id, newRole);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepo.deleteById(id);
    }

    @PostMapping("/users/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser =userRepo.save(user);
        return ResponseEntity.ok(newUser);
    }
}
