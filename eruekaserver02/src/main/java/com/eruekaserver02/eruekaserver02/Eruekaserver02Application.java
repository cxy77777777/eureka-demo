package com.eruekaserver02.eruekaserver02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Eruekaserver02Application {

    public static void main(String[] args) {
        SpringApplication.run(Eruekaserver02Application.class, args);
    }

}
