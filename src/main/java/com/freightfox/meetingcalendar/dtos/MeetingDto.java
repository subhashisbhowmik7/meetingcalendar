package com.freightfox.meetingcalendar.dtos;


import java.time.LocalDateTime;
import java.util.List;

public class MeetingDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Long> participantIds;  // Employee IDs

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

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }
}
