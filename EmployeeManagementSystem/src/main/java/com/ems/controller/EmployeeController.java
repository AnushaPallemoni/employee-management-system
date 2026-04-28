package com.ems.controller;

import com.ems.model.Employee;
import com.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EMPLOYEE REST CONTROLLER
 *
 * @RestController -> Handles HTTP requests & returns JSON responses
 * @RequestMapping -> All endpoints start with /api/employees
 * @CrossOrigin    -> Allows frontend (HTML) to call this API
 *
 * REST API Endpoints:
 * -------------------------------------------------------
 * POST   /api/employees          -> Create new employee
 * GET    /api/employees          -> Get all employees
 * GET    /api/employees/{id}     -> Get employee by ID
 * PUT    /api/employees/{id}     -> Update employee
 * DELETE /api/employees/{id}     -> Delete employee
 * GET    /api/employees/search   -> Search employees
 * GET    /api/employees/count    -> Get total count
 * -------------------------------------------------------
 */
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")  // Allow all origins (for frontend fetch calls)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // -------------------------------------------------------
    // POST /api/employees
    // CREATE a new employee
    // HTTP 201 Created on success
    // -------------------------------------------------------
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);  // 201
    }

    // -------------------------------------------------------
    // GET /api/employees
    // READ all employees
    // HTTP 200 OK
    // -------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);  // 200
    }

    // -------------------------------------------------------
    // GET /api/employees/{id}
    // READ one employee by ID
    // HTTP 200 OK or 404 Not Found
    // -------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);  // 200
    }

    // -------------------------------------------------------
    // PUT /api/employees/{id}
    // UPDATE an existing employee
    // HTTP 200 OK or 404 Not Found
    // -------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);  // 200
    }

    // -------------------------------------------------------
    // DELETE /api/employees/{id}
    // DELETE an employee
    // HTTP 200 OK with message, or 404 Not Found
    // -------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee with ID " + id + " deleted successfully!");
        return ResponseEntity.ok(response);  // 200
    }

    // -------------------------------------------------------
    // GET /api/employees/search?keyword=John
    // SEARCH employees by keyword
    // -------------------------------------------------------
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String keyword) {
        List<Employee> employees = employeeService.searchEmployees(keyword);
        return ResponseEntity.ok(employees);  // 200
    }

    // -------------------------------------------------------
    // GET /api/employees/count
    // GET total employee count
    // -------------------------------------------------------
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("totalEmployees", employeeService.getTotalCount());
        return ResponseEntity.ok(response);  // 200
    }

    // -------------------------------------------------------
    // GET /api/employees/department/{dept}
    // GET employees by department
    // -------------------------------------------------------
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getByDepartment(@PathVariable String department) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        return ResponseEntity.ok(employees);  // 200
    }
}
