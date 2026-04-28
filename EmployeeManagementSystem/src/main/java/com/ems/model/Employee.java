package com.ems.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * EMPLOYEE ENTITY
 *
 * @Entity   -> Tells JPA this class maps to a database table
 * @Table    -> Specifies the table name in PostgreSQL
 * @Id      -> Primary key
 * @GeneratedValue -> Auto-increment the ID
 */
@Entity
@Table(name = "employees")
@Data                   // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Lombok: generates no-argument constructor
@AllArgsConstructor     // Lombok: generates all-argument constructor
@Builder                // Lombok: generates builder pattern
public class Employee {

    // PRIMARY KEY — auto-incremented by PostgreSQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FIRST NAME — required, max 50 chars
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    // LAST NAME — required, max 50 chars
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    // EMAIL — must be unique and valid format
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // PHONE NUMBER — optional
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    // DEPARTMENT — required
    @NotBlank(message = "Department is required")
    @Column(name = "department", nullable = false, length = 50)
    private String department;

    // JOB TITLE — required
    @NotBlank(message = "Job title is required")
    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    // SALARY — must be positive
    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    @Column(name = "salary", nullable = false)
    private Double salary;

    // STATUS — Active / Inactive
    @Column(name = "status", length = 20)
    private String status = "Active";
}
