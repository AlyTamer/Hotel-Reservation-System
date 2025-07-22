package com.aly.brightskies.task3.component;

import com.aly.brightskies.task3.security.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SwaggerTokenGen implements CommandLineRunner {
    @Autowired
    private JWTUtility jwtUtility;
    @Override
    public void run(String... args) throws Exception {
        String token = jwtUtility.generateToken("aly");
        System.out.println();
        System.out.println("─────────────────────────────────────");
        System.out.println(" SWAGGER BEARER TOKEN (paste into UI) ");
        System.out.println("─────────────────────────────────────");
        System.out.println("Bearer " + token);
        System.out.println("─────────────────────────────────────");
        System.out.println();
    }
}
