package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.entities.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @SuppressWarnings("ALL")
    @PostMapping("/login")
    public void login(@RequestParam User user) {
        try{
            //TODO implement login check
        }
        catch (Exception e){

        }
    }
}
