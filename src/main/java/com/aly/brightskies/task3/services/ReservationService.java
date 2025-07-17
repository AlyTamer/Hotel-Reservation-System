package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.ReservationDTO;
import com.aly.brightskies.task3.entities.Reservation;
import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.entities.Status;
import com.aly.brightskies.task3.entities.User;
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
    public ReservationDTO createReservation(ReservationDTO res) {
        User user = userRepo.findById(res.getUserId()).orElseThrow();
        Room room  = roomRepo.findById(res.getRoomId()).orElseThrow();
        Reservation r = new Reservation();
        r.setUserId(user);
        r.setRoomId(room);
        r.setCheckInDate(res.getCheckInDate());
        r.setCheckOutDate(res.getCheckOutDate());
        r.setStatus(Status.BOOKED);
        reservationRepo.save(r);
        return new ReservationDTO(
                r.getId(),
                r.getUserId().getId(),
                r.getRoomId().getId(),
                r.getCheckInDate(),
                r.getCheckOutDate(),
                Status.BOOKED
        );
    }
    public List<ReservationDTO> getUserReservation(int userId){
        User user = userRepo.findById(userId).orElseThrow();
        List<Reservation> reservations = reservationRepo.getAllByUserId(user);
        List<ReservationDTO> dtos = new ArrayList<>();
        for(Reservation reservation : reservations){
            ReservationDTO dto = new ReservationDTO(
                    reservation.getId(),
                    reservation.getUserId().getId(),
                    reservation.getRoomId().getId(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate(),
                    reservation.getStatus());
            dtos.add(dto);

        }
        return dtos;
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
