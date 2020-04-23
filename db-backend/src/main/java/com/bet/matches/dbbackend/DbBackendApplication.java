package com.bet.matches.dbbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DbBackendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DbBackendApplication.class, args);
    }

}
