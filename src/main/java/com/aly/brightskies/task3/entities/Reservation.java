package com.aly.brightskies.task3.entities;

import jakarta.persistence.*;
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
    @ManyToOne
    @JoinColumn(name ="userId")
    private User userId;
    @ManyToOne
    @JoinColumn(name ="roomId")
    private Room roomId;
    private Date checkInDate;
    private Date checkOutDate;
    @Enumerated(EnumType.STRING)
    private Status status;
}
