package com.freightfox.meetingcalendar.entities;


import java.time.LocalDateTime;

public class FreeSlot {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Constructor
    public FreeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "FreeSlot{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
