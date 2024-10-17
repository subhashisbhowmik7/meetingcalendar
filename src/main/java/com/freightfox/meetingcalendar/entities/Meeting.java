package com.freightfox.meetingcalendar.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne // Many meetings can belong to one employee
    @JoinColumn(name = "employee_id") // Foreign key in the Meeting table
    private Employee employee;

    @ManyToMany
    @JoinTable(
        name = "meeting_participants", 
        joinColumns = @JoinColumn(name = "meeting_id"), 
        inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> participants;


    public Meeting(LocalDateTime startTime, LocalDateTime endTime, Employee employee, List<Employee> participants) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee; // Set employee who owns the meeting
        this.participants = participants;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee; // Getter for the owner
    }

    public void setEmployee(Employee employee) {
        this.employee = employee; // Setter for the owner
    }

    public List<Employee> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Employee> participants) {
        this.participants = participants;
    }
}
