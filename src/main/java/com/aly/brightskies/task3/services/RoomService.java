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
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getStatus()==Status.AVAILABLE
        );
        roomDTOS.add(dto);
    }
    return roomDTOS;
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
}
