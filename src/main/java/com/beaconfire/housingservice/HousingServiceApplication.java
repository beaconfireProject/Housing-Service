package com.beaconfire.housingservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableFeignClients(basePackages = "com.beaconfire.housingservice.feign")
public class HousingServiceApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load(); // Load .env file

        // Inject into Spring environment
        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
        SpringApplication.run(HousingServiceApplication.class, args);
    }

}
