package com.aly.brightskies.task3.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private int id;
    private int roomNumber;
    private String roomType;
    private boolean status;

}
