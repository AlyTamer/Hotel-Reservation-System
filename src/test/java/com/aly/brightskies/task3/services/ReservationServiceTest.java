package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.ReservationDTO;
import com.aly.brightskies.task3.entities.*;
import com.aly.brightskies.task3.repositories.ReservationRepo;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.aly.brightskies.task3.Main.class)

@ActiveProfiles("test")
@Transactional
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private ReservationRepo reservationRepo;
    private User testUser;
    private Room testRoom;

    @BeforeEach
    void setUp() {
        try {
            testUser = new User();
            testUser.setUserName("Test User");
            testUser.setEmail("user@example.com");
            testUser.setPassword("password");
            testUser.setNumber(123456789);
            testUser.setRole(Role.ROLE_USER);
            userRepo.save(testUser);

            testRoom = new Room();
            testRoom.setRoomNumber(101);
            testRoom.setRoomType("Single");
            testRoom.setStatus(Status.AVAILABLE);
            roomRepo.save(testRoom);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testCreateReservation() {
        ReservationDTO reservation = new ReservationDTO(
                0, testUser.getId(), testRoom.getId(), Date.valueOf("2025-08-01"), Date.valueOf("2025-08-05"), Status.BOOKED
        );
        ReservationDTO result = reservationService.createReservation(reservation);
        assertNotNull(result);
        assertEquals(Status.BOOKED, result.getStatus());
        assertEquals(testUser.getId(), result.getUserId());
    }

    @Test
    void testGetUserReservation() {
        Reservation res = new Reservation();
        res.setUserId(testUser);
        res.setRoomId(testRoom);
        res.setCheckInDate(Date.valueOf("2025-08-01"));
        res.setCheckOutDate(Date.valueOf("2025-08-05"));
        res.setStatus(Status.BOOKED);
        reservationRepo.save(res);
        List<ReservationDTO> results = reservationService.getUserReservation(testUser.getId());
        assertEquals(1,results.size());
        assertEquals(testRoom.getId(), results.getFirst().getRoomId());

    }

    @Test
    void testUpdateReservation() {
        Reservation res = new Reservation();
        res.setUserId(testUser);
        res.setRoomId(testRoom);
        res.setCheckInDate(Date.valueOf("2025-08-01"));
        res.setCheckOutDate(Date.valueOf("2025-08-05"));
        res.setStatus(Status.BOOKED);
        res =reservationRepo.save(res);

        ReservationDTO updateDto = new ReservationDTO(
                res.getId(), testUser.getId(), testRoom.getId(), Date.valueOf("2025-08-02"), Date.valueOf("2025-08-06"), Status.AVAILABLE
        );
        ReservationDTO updatedReservation = reservationService.updateReservation(res.getId(), updateDto);
        assertNotNull(updatedReservation);
        assertEquals(Status.AVAILABLE, updatedReservation.getStatus());
    }
    @Test
    void testDeleteReservation() {
        Reservation res = new Reservation();
        res.setUserId(testUser);
        res.setRoomId(testRoom);
        res.setCheckInDate(Date.valueOf("2025-08-01"));
        res.setCheckOutDate(Date.valueOf("2025-08-05"));
        res.setStatus(Status.BOOKED);
        res = reservationRepo.save(res);

        try {
            reservationService.deleteReservation(res.getId());
        } catch (Exception e) {
            fail("Deletion failed: " + e.getMessage());
        }
        assertEquals(0, reservationRepo.count());
        assertFalse(reservationRepo.existsById(res.getId()));
    }
    @Test
    void testGetAllReservationDTOs() {
        Reservation res1 = new Reservation();
        res1.setUserId(testUser);
        res1.setRoomId(testRoom);
        res1.setCheckInDate(Date.valueOf("2025-08-01"));
        res1.setCheckOutDate(Date.valueOf("2025-08-05"));
        res1.setStatus(Status.BOOKED);
        reservationRepo.save(res1);

        Reservation res2 = new Reservation();
        res2.setUserId(testUser);
        res2.setRoomId(testRoom);
        res2.setCheckInDate(Date.valueOf("2025-08-06"));
        res2.setCheckOutDate(Date.valueOf("2025-08-10"));
        res2.setStatus(Status.AVAILABLE);
        reservationRepo.save(res2);

        List<ReservationDTO> results = reservationService.getAllReservationDTOs();
        assertEquals(2, results.size());
    }


}