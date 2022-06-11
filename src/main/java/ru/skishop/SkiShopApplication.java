package ru.skishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SkiShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkiShopApplication.class, args);
    }
}