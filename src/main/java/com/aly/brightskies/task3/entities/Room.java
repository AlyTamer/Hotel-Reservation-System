package com.aly.brightskies.task3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Room {
    @Id
    private int id;
    private int roomNumber;
    private String roomType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;
}
