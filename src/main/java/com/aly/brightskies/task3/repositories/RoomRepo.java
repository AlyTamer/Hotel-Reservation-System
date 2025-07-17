package com.aly.brightskies.task3.repositories;

import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room,Integer> {
    List<Room> findAllByStatus(Status status);

    List<Room> findAllByRoomType(String roomType);

    List<Room> findAll(Status status);
}
