package com.ems;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * BASIC APPLICATION TEST
 *
 * Verifies that the Spring Boot application context loads correctly.
 * Run this to check if all beans are wired properly.
 */
@SpringBootTest
class EmployeeManagementSystemApplicationTests {

    @Test
    void contextLoads() {
        // This test just checks the Spring context starts without errors
        System.out.println("Spring Boot Application context loaded successfully!");
    }
}
