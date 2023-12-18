package org.vminds.requests.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * This class provides entry point for running application
 */
@EnableCaching
@SpringBootApplication
public class RequestProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestProcessorApplication.class, args);
    }

}
