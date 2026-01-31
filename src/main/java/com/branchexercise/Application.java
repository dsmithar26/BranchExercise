package com.branchexercise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@Slf4j
public class Application {

    static void main(String[] args) {
        log.info("Application Start up");
        SpringApplication.run(Application.class, args);
    }
}
