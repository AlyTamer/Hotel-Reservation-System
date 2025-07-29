package com.aly.brightskies.task3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,nullable = false)
    private String userName;
    @Column(unique = true,nullable = false)
    private String email;
    private int number;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String email, int number, String password, Role role) {
        this.userName = username;
        this.email = email;
        this.number = number;
        this.password = password;
        this.role = role;
    }
}

