package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.dto.ReservationDTO;
import com.aly.brightskies.task3.dto.RoomDTO;
import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.exceptions.ConflictException;
import com.aly.brightskies.task3.services.ReservationService;
import com.aly.brightskies.task3.services.RoomService;
import com.aly.brightskies.task3.services.UserService;
//import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Management",
        description = "Admin operations for managing rooms, reservations, and users")
@PreAuthorize("hasRole('ADMIN')")
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

    @Operation(
            summary = "Get all rooms",
            description = "Retrieve a list of all rooms in the system",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/rooms")
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRoomDTOs();
    }

    @Operation(
            summary = "Create a new room",
            description = "Add a new room to the system",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    
    @PostMapping("/rooms")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO r) {
        System.out.println("Current auth: " + SecurityContextHolder.getContext().getAuthentication());
        RoomDTO newRoom = roomService.createNewRoom(r);
        System.out.println("Room created: " + newRoom);
        return ResponseEntity.ok(newRoom);
    }

    @Operation(
            summary = "Update an existing room",
            description = "Modify the details of an existing room",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    
    @PutMapping("/rooms/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable int id,
                                           @RequestBody RoomDTO r) {
        RoomDTO updated = roomService.updateRoom(id,r);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete a room",
            description = "Remove a room from the system by its ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    
    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get all reservations",
            description = "Retrieve a list of all reservations in the system",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/reservations")
    public List<ReservationDTO> getAllReservations() {
        return reservationService.getAllReservationDTOs();
    }

    @Operation(
            summary = "Delete a reservation",
            description = "Remove a reservation from the system by its ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    
    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }

    @Operation(
            summary = "Create a new reservation",
            description = "Add a new reservation to the system",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    
    @PostMapping("/reservations/create")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO dto) {
        ReservationDTO created = reservationService.createReservation(dto);
        return ResponseEntity.ok(created);
    }


    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all users in the system",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @Operation(
            summary = "Change user role",
            description = "Update the role of a user by their ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    
    @PutMapping("/users/{id}/role")
    public void changeUserRole(@PathVariable int id,
                               @RequestParam Role newRole) {
        try {
            userService.updateUserRole(id, newRole);
            System.out.println("Changed role to " + newRole);
        } catch (Exception e) {
            throw new ConflictException("Couldnt change role");
        }

    }

    @Operation(
            summary = "Delete a user",
            description = "Remove a user from the system by their ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {

        userService.deleteById(id);
    }

}
