package com.aly.brightskies.task3.dto;

import com.aly.brightskies.task3.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {

    private int id;
    private int userId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private Status status;
public ReservationDTO( int userId, Date checkInDate, Date checkOutDate, Status status) {
    this.userId = userId;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.status = status;
}

}

