package com.freightfox.meetingcalendar.entities;

import jakarta.persistence.*;
import java.util.List;



@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Many-to-Many relationship with Meeting
    @ManyToMany
    @JoinTable(
        name = "employee_meetings",  // Join table
        joinColumns = @JoinColumn(name = "employee_id"),  // Foreign key in join table referring to Employee
        inverseJoinColumns = @JoinColumn(name = "meeting_id")  // Foreign key in join table referring to Meeting
    )
    private List<Meeting> meetings;

    // Constructors, Getters, Setters
    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
