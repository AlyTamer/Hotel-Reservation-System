package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.RoomDTO;
import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.entities.Status;
import com.aly.brightskies.task3.exceptions.RoomException;
import com.aly.brightskies.task3.exceptions.RoomExceptionMessages;
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
        try {
            if (type != null && !type.isEmpty()) {

                rooms = roomRepo.findAllByRoomType(type);

            } else {
                rooms = roomRepo.findAllByStatus(Status.AVAILABLE);
            }
        } catch (Exception e) {
            throw new RoomException(RoomExceptionMessages.NO_ROOMS_FOUND);
        }
        if (rooms == null || rooms.isEmpty()) {
            throw new RoomException(RoomExceptionMessages.ROOM_NOT_FOUND);
        }
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for (Room room : rooms) {
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
        List<Room> rooms;
        try {
            rooms = roomRepo.findAll();
        } catch (Exception e) {
            throw new RoomException(RoomExceptionMessages.NO_ROOMS_FOUND);
        }
        List<RoomDTO> dtos = new ArrayList<>();
        for (Room room : rooms) {
            dtos.add(toRoomDTO(room));
        }
        return dtos;
    }

    public RoomDTO createNewRoom(RoomDTO dto) {
        try {
            Room room = new Room();
            room.setRoomNumber(dto.getRoomNumber());
            room.setRoomType(dto.getRoomType());
            room.setStatus(dto.getStatus());

            Room saved = roomRepo.save(room);
            return toRoomDTO(saved);
        } catch (Exception e) {
            throw new RoomException(RoomExceptionMessages.FAILED_TO_CREATE_ROOM);
        }

    }

    public RoomDTO updateRoom(int id, RoomDTO dto) {
        Room current;
        try {
            current = roomRepo.findById(id);
        } catch (Exception e) {
            throw new RoomException(RoomExceptionMessages.ROOM_NOT_FOUND);
        }
        try {
            current.setRoomNumber(dto.getRoomNumber());
            current.setRoomType(dto.getRoomType());
            current.setStatus(dto.getStatus());
        } catch (Exception e) {
            throw new RoomException(RoomExceptionMessages.FAILED_TO_UPDATE_ROOM);
        }
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
        try {
            roomRepo.deleteById(id);
        } catch (Exception e) {
            throw new RoomException(RoomExceptionMessages.ROOM_NOT_FOUND);
        }
    }


}
