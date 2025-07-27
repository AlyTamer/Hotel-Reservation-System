package com.aly.brightskies.task3.dto;

import com.aly.brightskies.task3.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {

    private int id;
    private int userId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private Status status;


}

