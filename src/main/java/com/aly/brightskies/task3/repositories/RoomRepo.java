package com.aly.brightskies.task3.repositories;

import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room,Integer> {

    List<Room> findAllByRoomType(String roomType);

    @Query("SELECT r FROM Room r WHERE r.status = :status")
    List<Room> findAll(Status status);
}
