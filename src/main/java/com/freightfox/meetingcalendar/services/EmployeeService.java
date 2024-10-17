package com.freightfox.meetingcalendar.services;


import com.freightfox.meetingcalendar.entities.Employee;
import com.freightfox.meetingcalendar.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Add a new employee
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // List all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Find employee by ID
    public Employee findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found!"));
    }
}
