package com.aly.brightskies.task3.dto;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.services.RoomService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RoomDTO {
    private int roomNumber;
    private String roomType;
    private boolean status;
    private int id;

    public RoomDTO(int roomNumber, String roomType, boolean status) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;

    }

    public RoomDTO(int id, int roomNumber, String roomType, boolean b) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = b;
    }
}
