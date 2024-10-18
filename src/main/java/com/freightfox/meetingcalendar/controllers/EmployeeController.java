package com.freightfox.meetingcalendar.controllers;


import com.freightfox.meetingcalendar.dtos.MeetingDto;
// import com.freightfox.meetingcalendar.dtos.MeetingDto;
import com.freightfox.meetingcalendar.entities.Employee;
import com.freightfox.meetingcalendar.entities.Meeting;
// import com.freightfox.meetingcalendar.entities.Meeting;
import com.freightfox.meetingcalendar.services.EmployeeService;
import com.freightfox.meetingcalendar.services.MeetingService;

// import com.freightfox.meetingcalendar.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MeetingService meetingService;

    // Create a new employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.ok(newEmployee);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Get an employee by ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/{employeeId}/meetings")
    public ResponseEntity<Meeting> bookMeeting(@PathVariable Long employeeId, @RequestBody MeetingDto meetingDTO) {
        Meeting bookedMeeting = meetingService.bookMeeting(employeeId, meetingDTO);
        return ResponseEntity.ok(bookedMeeting);
    }

    /**
     * Simple test to check if the API is running
     * @return a "Working" string
     */
    @GetMapping("/test")
    public String test(){
        return "Working";
    }
}
