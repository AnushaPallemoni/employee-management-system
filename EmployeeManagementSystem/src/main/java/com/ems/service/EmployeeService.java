package com.ems.service;

import com.ems.model.Employee;
import java.util.List;

/**
 * EMPLOYEE SERVICE INTERFACE
 *
 * Defines what operations the service layer must provide.
 * The actual logic is in EmployeeServiceImpl.
 *
 * WHY use an interface?
 * -> Good practice: separates "what to do" from "how to do"
 * -> Makes it easier to write unit tests (mocking)
 */
public interface EmployeeService {

    // CREATE a new employee
    Employee createEmployee(Employee employee);

    // READ all employees
    List<Employee> getAllEmployees();

    // READ one employee by ID
    Employee getEmployeeById(Long id);

    // UPDATE an existing employee
    Employee updateEmployee(Long id, Employee employee);

    // DELETE an employee
    void deleteEmployee(Long id);

    // SEARCH employees by keyword
    List<Employee> searchEmployees(String keyword);

    // GET employees by department
    List<Employee> getEmployeesByDepartment(String department);

    // GET total count
    long getTotalCount();
}
