package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MAIN ENTRY POINT for Employee Management System
 *
 * @SpringBootApplication combines:
 *   - @Configuration       : marks this as a config class
 *   - @EnableAutoConfiguration : auto-configures Spring Boot
 *   - @ComponentScan       : scans all classes under com.ems
 */
@SpringBootApplication
public class EmployeeManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementSystemApplication.class, args);
        System.out.println("======================================");
        System.out.println(" Employee Management System STARTED!");
        System.out.println(" Open: http://localhost:8080");
        System.out.println("======================================");
    }
}
