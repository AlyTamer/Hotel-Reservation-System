package com.aly.brightskies.task3.components;

import com.aly.brightskies.task3.security.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SwaggerTokenGen implements CommandLineRunner {
    @Autowired
    private JWTUtility jwtUtility;
    @Override
    public void run(String... args)  {
        try {
            String token = jwtUtility.generateToken("admin");
            System.out.println();
            System.out.println("─────────────────────────────────────");
            System.out.println(" SWAGGER BEARER TOKEN  ");
            System.out.println("─────────────────────────────────────");
            System.out.println("Bearer " + token);
            System.out.println("─────────────────────────────────────");
            System.out.println();
        } catch (Exception e) {
            System.out.println("No user found");
        }
    }
}
