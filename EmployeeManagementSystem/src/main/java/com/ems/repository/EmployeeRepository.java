package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * EMPLOYEE REPOSITORY
 *
 * Extends JpaRepository which automatically provides:
 *   - save()       -> INSERT or UPDATE
 *   - findById()   -> SELECT by ID
 *   - findAll()    -> SELECT all rows
 *   - deleteById() -> DELETE by ID
 *   - count()      -> COUNT rows
 *   ...and many more!
 *
 * We only need to write CUSTOM queries here.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Check if email already exists (used during create/update)
    boolean existsByEmail(String email);

    // Find by email (useful for login or search)
    Optional<Employee> findByEmail(String email);

    // Find all employees in a specific department
    List<Employee> findByDepartment(String department);

    // Find all employees with a specific status (Active/Inactive)
    List<Employee> findByStatus(String status);

    // Custom search: find employees by first name OR last name (case-insensitive)
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.lastName)  LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchEmployees(@Param("keyword") String keyword);
}
