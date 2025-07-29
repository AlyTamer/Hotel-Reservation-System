package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.ReservationDTO;
import com.aly.brightskies.task3.entities.Reservation;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.exceptions.*;
import com.aly.brightskies.task3.repositories.ReservationRepo;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private final RoomRepo roomRepo;
    private final ReservationRepo reservationRepo;
    private final UserRepo userRepo;

    @Autowired
    public ReservationService(RoomRepo roomRepo, ReservationRepo reservationRepo, UserRepo userRepo) {
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
        this.roomRepo = roomRepo;
    }


    public List<ReservationDTO> getUserReservation(int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserException(UserExceptionMessages.USER_NOT_FOUND));
        List<Reservation> reservations = reservationRepo.getAllByUserId(user);
        if (reservations == null || reservations.isEmpty()) {
            throw new ReservationException(ReservationExceptionMessages.RESERVATION_NOT_FOUND);
        }
        List<ReservationDTO> dtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtos.add(toReservationDTO(reservation));
        }
        return dtos;
    }

    public ReservationDTO updateReservation(int id, ReservationDTO res) {
        Reservation currentRes = reservationRepo.findById(id).orElseThrow(() -> new ReservationException(ReservationExceptionMessages.RESERVATION_NOT_FOUND));
        try {
            currentRes.setCheckInDate(res.getCheckInDate());
            currentRes.setCheckOutDate(res.getCheckOutDate());
            currentRes.setStatus(res.getStatus());
            reservationRepo.save(currentRes);
        } catch (Exception e) {
            throw new ReservationException(ReservationExceptionMessages.FAILED_TO_UPDATE_RESERVATION);
        }
        return toReservationDTO(currentRes);
    }

    public void deleteReservation(int id) {
        try {
            reservationRepo.deleteById(id);
        } catch (Exception e) {
            throw new ReservationException(ReservationExceptionMessages.FAILED_TO_DELETE_RESERVATION);
        }
    }

    public ReservationDTO toReservationDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getUserId().getId(),
                reservation.getRoomId().getId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getStatus()
        );
    }


    public List<ReservationDTO> getAllReservationDTOs() {
        List<Reservation> reservations = reservationRepo.findAll();
        if (reservations.isEmpty()) {
            throw new ReservationException(ReservationExceptionMessages.RESERVATION_NOT_FOUND);
        }
        List<ReservationDTO> dtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtos.add(toReservationDTO(reservation));
        }
        return dtos;
    }

    public ReservationDTO createReservation(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setUserId(userRepo.findById(dto.getUserId()).orElseThrow(() -> new UserException(UserExceptionMessages.USER_NOT_FOUND)));
        try {
            reservation.setRoomId(roomRepo.findById(dto.getRoomId()));
        } catch (Exception e) {
            throw new RoomException(RoomExceptionMessages.ROOM_NOT_FOUND);
        }
        try {
            reservation.setCheckInDate(dto.getCheckInDate());
            reservation.setCheckOutDate(dto.getCheckOutDate());
            reservation.setStatus(dto.getStatus());

            Reservation saved = reservationRepo.save(reservation);
            return toReservationDTO(saved);
        } catch (Exception e) {
            throw new ReservationException(ReservationExceptionMessages.FAILED_TO_CREATE_RESERVATION);
        }

    }


}