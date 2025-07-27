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
                room.getStatus()==Status.AVAILABLE
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
        room.setStatus(dto.isStatus() ? Status.AVAILABLE : Status.BOOKED);

        Room saved = roomRepo.save(room);
        return toRoomDTO(saved);
    }

public RoomDTO updateRoom(int id,RoomDTO dto) {
    Room current = roomRepo.findById(id);
    current.setRoomNumber(dto.getRoomNumber());
    current.setRoomType(dto.getRoomType());
    current.setStatus(dto.isStatus()?Status.AVAILABLE:Status.BOOKED);
    roomRepo.save(current);
    return toRoomDTO(current);
}
private RoomDTO toRoomDTO(Room room) {
    return new RoomDTO(
            room.getRoomNumber(),
            room.getRoomType(),
            room.getStatus() == Status.AVAILABLE
    );
}
private Room toRoomEntity(RoomDTO dto) {
    Room room = new Room();
    room.setId(dto.getId());
    room.setRoomNumber(dto.getRoomNumber());
    room.setRoomType(dto.getRoomType());
    room.setStatus(dto.isStatus() ? Status.AVAILABLE : Status.BOOKED);
    return room;
}

    public Room updateRoom(int id,Room r) {
        Room current =roomRepo.findById(id);
        current.setRoomNumber(r.getRoomNumber());
        current.setRoomType(r.getRoomType());
        current.setStatus(r.getStatus());
        current.setId(r.getId());
        roomRepo.save(current);
        return current;
    }

    public void deleteRoom(int id) {
        roomRepo.deleteById(id);
    }

    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    public Room createNewRoom(Room r) {

        return roomRepo.save(r);
    }
    public int getRoomById(int id) {
        Room room = roomRepo.findById(id);
        if (room != null) {
            return room.getId();
        } else {
            throw new RuntimeException("Room not found with id: " + id);
        }
    }

}
