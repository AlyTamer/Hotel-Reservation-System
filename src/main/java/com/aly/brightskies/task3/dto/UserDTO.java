package com.aly.brightskies.task3.dto;

import com.aly.brightskies.task3.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private int number;
    private String password;
    private Role role;

}

