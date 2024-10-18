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

    @ManyToMany
    @JoinTable(
        name = "meeting_participants",
        joinColumns = @JoinColumn(name = "meeting_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    // @JsonBackReference  // Prevents infinite recursion during JSON serialization
    private List<Employee> participants;

    // Constructors, Getters, Setters
    public Meeting() {}

    public Meeting(LocalDateTime startTime, LocalDateTime endTime, List<Employee> participants) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
    }

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

    public List<Employee> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Employee> participants) {
        this.participants = participants;
    }
}
