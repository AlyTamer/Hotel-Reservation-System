package com.aly.brightskies.task3.dto;
import com.aly.brightskies.task3.entities.Status;

import lombok.*;

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
