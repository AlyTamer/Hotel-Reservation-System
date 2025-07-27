package com.aly.brightskies.task3.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int roomNumber;
    private String roomType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;


}
