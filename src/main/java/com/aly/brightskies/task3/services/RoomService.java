package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.RoomDTO;
import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.entities.Status;
import com.aly.brightskies.task3.repositories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
private final RoomRepo roomRepo;
@Autowired
public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
}
public List<RoomDTO> getRooms(String type) {
    List<Room> rooms;
    if(type!=null&&!type.isEmpty()) {
        rooms=roomRepo.findAllByRoomType(type);
    }
    else {
        rooms=roomRepo.findAllByStatus(Status.AVAILABLE);
    }
    List<RoomDTO> roomDTOS = new ArrayList<>();
    for(Room room:rooms) {
        RoomDTO dto = new RoomDTO(
                room.getRoomNumber(),
                room.getRoomType(),
                room.getStatus()
        );
        roomDTOS.add(dto);
    }
    return roomDTOS;
}
public List<RoomDTO> getAllRoomDTOs() {
    List<Room> rooms = roomRepo.findAll();
    List<RoomDTO> dtos = new ArrayList<>();
    for (Room room : rooms) {
        dtos.add(toRoomDTO(room));
    }
    return dtos;
}
    public RoomDTO createNewRoom(RoomDTO dto) {
        Room room = new Room();
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setStatus(dto.getStatus());

        Room saved = roomRepo.save(room);
        return toRoomDTO(saved);
    }

public RoomDTO updateRoom(int id,RoomDTO dto) {
    Room current = roomRepo.findById(id);
    current.setRoomNumber(dto.getRoomNumber());
    current.setRoomType(dto.getRoomType());
    current.setStatus(dto.getStatus());
    return toRoomDTO(current);
}
private RoomDTO toRoomDTO(Room room) {
    return new RoomDTO(
            room.getRoomNumber(),
            room.getRoomType(),
            room.getStatus()
    );
}

    public void deleteRoom(int id) {
        roomRepo.deleteById(id);
    }



}
