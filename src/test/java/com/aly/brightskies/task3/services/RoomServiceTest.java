package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.RoomDTO;
import com.aly.brightskies.task3.entities.Room;
import com.aly.brightskies.task3.entities.Status;
import com.aly.brightskies.task3.repositories.RoomRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepo roomRepo;

    private Room testRoom;

    @BeforeEach
    void setUp() {
        testRoom = new Room();
        testRoom.setRoomNumber(101);
        testRoom.setRoomType("Single");
        testRoom.setStatus(Status.AVAILABLE);
        roomRepo.save(testRoom);
    }

    @Test
    void testGetAllRoomDTOs() {
        List<RoomDTO> results = roomService.getAllRoomDTOs();
        assertFalse(results.isEmpty());
        assertEquals(101, results.getFirst().getRoomNumber());
    }

    @Test
    void testGetRooms_ByType_Single() {
        String roomType = "Single";
        List<RoomDTO> results = roomService.getRooms(roomType);
        assertFalse(results.isEmpty());
        assertEquals(roomType, results.getFirst().getRoomType());
    }
    @Test
    void testGetRooms_ByType_Double() {
        testRoom.setRoomType("Double");
        String roomType = "Double";
        List<RoomDTO> results = roomService.getRooms(roomType);
        assertFalse(results.isEmpty(), "Expected no rooms of type Double to be found");
    }

    @Test
    void testGetRooms_OnlyAvailable() {
List<RoomDTO> results =roomService.getRooms(null);
        assertFalse(results.isEmpty());
        assertEquals(Status.AVAILABLE, results.getFirst().getStatus());  }

    @Test
    void testCreateNewRoom() {
        RoomDTO newRoom = new RoomDTO();
        newRoom.setRoomNumber(102);
        newRoom.setRoomType("Double");
        newRoom.setStatus(Status.AVAILABLE);

        RoomDTO createdRoom = roomService.createNewRoom(newRoom);
        assertNotNull(createdRoom);
        assertEquals(102, createdRoom.getRoomNumber());
        assertEquals("Double", createdRoom.getRoomType());
        assertEquals(Status.AVAILABLE, createdRoom.getStatus());


        Room savedRoom = roomRepo.findAll()
                .stream()
                .filter(r-> r.getRoomNumber() == createdRoom.getRoomNumber())
                .findFirst()
                .orElse(null);
    }

    @Test
    void testUpdateRoom() {
        RoomDTO updateRoom = new RoomDTO();
        updateRoom.setRoomNumber(101);
        updateRoom.setRoomType("Suite");
        updateRoom.setStatus(Status.BOOKED);

        RoomDTO updatedRoom = roomService.updateRoom(testRoom.getId(), updateRoom);
        assertNotNull(updatedRoom);
        assertEquals("Suite", updatedRoom.getRoomType());
        assertEquals(Status.BOOKED, updatedRoom.getStatus());

        Room savedRoom = roomRepo.findById(testRoom.getId());
        assertNotNull(savedRoom);
        assertEquals("Suite", savedRoom.getRoomType());
    }


    @Test
    void testDeleteRoom() {
        int roomId = testRoom.getId();
        roomService.deleteRoom(roomId);

        Room deletedRoom = roomRepo.findById(roomId);
        assertNull(deletedRoom, "Expected room to be deleted");
    }
}
