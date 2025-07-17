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
    if(type!=null&&type.length()>0) {
        rooms=roomRepo.findAllByRoomType(type);
    }
    else {
        rooms=roomRepo.findAll(Status.AVAILABLE);
    }
    List<RoomDTO> roomDTOS = new ArrayList<RoomDTO>();
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

}
