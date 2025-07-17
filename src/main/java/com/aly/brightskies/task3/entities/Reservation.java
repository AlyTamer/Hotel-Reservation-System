package com.aly.brightskies.task3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    private int id;
    @JoinTable(name = "User")
    private User userId;
    private String roomId;
    private Date checkInDate;
    private Date checkOutDate;
}
