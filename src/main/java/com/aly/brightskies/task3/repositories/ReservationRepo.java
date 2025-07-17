package com.aly.brightskies.task3.repositories;

import com.aly.brightskies.task3.entities.Reservation;
import com.aly.brightskies.task3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    List<Reservation> getAllByUserId(User userId);

    Reservation getById(int id);
}
