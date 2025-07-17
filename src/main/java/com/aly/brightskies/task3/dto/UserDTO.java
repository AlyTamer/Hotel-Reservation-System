package com.aly.brightskies.task3.dto;

import com.aly.brightskies.task3.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class UserDTO {
    private int id;
    private String username;
    private String email;
    private int number;


}
