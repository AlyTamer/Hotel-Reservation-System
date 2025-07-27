package com.aly.brightskies.task3.dto;
import com.aly.brightskies.task3.entities.Status;
import com.aly.brightskies.task3.repositories.RoomRepo;
import com.aly.brightskies.task3.services.RoomService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Getter
@NoArgsConstructor
@Setter
@ToString
@AllArgsConstructor
public class RoomDTO {
    private int roomNumber;
    private String roomType;
    private Status status;
    private int id;

    public RoomDTO(int roomNumber, String roomType, Status status) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;

    }

}
