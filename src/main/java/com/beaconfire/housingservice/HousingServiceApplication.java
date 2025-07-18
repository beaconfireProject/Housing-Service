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
        EnvLoader.init();
        SpringApplication.run(HousingServiceApplication.class, args);
    }

}
