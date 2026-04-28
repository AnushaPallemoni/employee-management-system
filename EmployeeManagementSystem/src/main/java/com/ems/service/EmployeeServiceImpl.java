package com.ems.service;

import com.ems.exception.ResourceNotFoundException;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EMPLOYEE SERVICE IMPLEMENTATION
 *
 * @Service -> Marks this as a Spring Service Bean (business logic layer)
 *
 * This class contains ALL the business logic:
 *   - Talks to the Repository (database layer)
 *   - Validates business rules
 *   - Throws exceptions when needed
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    // Spring automatically injects the EmployeeRepository bean
    @Autowired
    private EmployeeRepository employeeRepository;

    // -------------------------------------------------------
    // CREATE — Add a new employee
    // -------------------------------------------------------
    @Override
    public Employee createEmployee(Employee employee) {
        // Check if email already exists
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Email '" + employee.getEmail() + "' is already registered!");
        }
        // Set default status if not provided
        if (employee.getStatus() == null || employee.getStatus().isBlank()) {
            employee.setStatus("Active");
        }
        return employeeRepository.save(employee);
    }

    // -------------------------------------------------------
    // READ ALL — Get list of all employees
    // -------------------------------------------------------
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // -------------------------------------------------------
    // READ ONE — Get employee by ID
    // -------------------------------------------------------
    @Override
    public Employee getEmployeeById(Long id) {
        // If not found, throw 404 exception
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
    }

    // -------------------------------------------------------
    // UPDATE — Modify existing employee details
    // -------------------------------------------------------
    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        // First check if employee exists
        Employee existingEmployee = getEmployeeById(id);

        // Check email uniqueness only if email was changed
        if (!existingEmployee.getEmail().equals(updatedEmployee.getEmail())) {
            if (employeeRepository.existsByEmail(updatedEmployee.getEmail())) {
                throw new IllegalArgumentException("Email '" + updatedEmployee.getEmail() + "' is already in use!");
            }
        }

        // Update each field
        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setJobTitle(updatedEmployee.getJobTitle());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setStatus(updatedEmployee.getStatus());

        // Save updated employee back to DB
        return employeeRepository.save(existingEmployee);
    }

    // -------------------------------------------------------
    // DELETE — Remove employee by ID
    // -------------------------------------------------------
    @Override
    public void deleteEmployee(Long id) {
        // Check if employee exists first
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    // -------------------------------------------------------
    // SEARCH — Find employees by keyword
    // -------------------------------------------------------
    @Override
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.searchEmployees(keyword);
    }

    // -------------------------------------------------------
    // FILTER BY DEPARTMENT
    // -------------------------------------------------------
    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    // -------------------------------------------------------
    // COUNT — Total number of employees
    // -------------------------------------------------------
    @Override
    public long getTotalCount() {
        return employeeRepository.count();
    }
}
