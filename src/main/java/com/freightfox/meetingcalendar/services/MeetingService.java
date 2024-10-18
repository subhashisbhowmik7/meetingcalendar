package com.freightfox.meetingcalendar.services;


import com.freightfox.meetingcalendar.dtos.MeetingDto;
import com.freightfox.meetingcalendar.entities.*;
import com.freightfox.meetingcalendar.repositories.EmployeeRepository;
import com.freightfox.meetingcalendar.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



@Service
public class MeetingService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    // Book a meeting for a given employee
       
    public Meeting bookMeeting(Long employeeId, MeetingDto meetingDTO) {
        Employee owner = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Fetch participants and add owner to the participants list
        List<Employee> participants = employeeRepository.findAllById(meetingDTO.getParticipantIds());
        if (!participants.contains(owner)) {
            participants.add(owner);
        }

        // Create the meeting and associate it with participants
        Meeting meeting = new Meeting(meetingDTO.getStartTime(), meetingDTO.getEndTime(), participants);

        // Associate the meeting with the employees' meetings
        for (Employee participant : participants) {
            participant.getMeetings().add(meeting);
        }

        // Save the meeting
        meetingRepository.save(meeting);

        // Save the updated employees (with the new meeting)
        employeeRepository.saveAll(participants);

        return meeting;
    }    

    // Find free slots between two employees for a given duration
    public List<FreeSlot> findFreeSlots(Long employee1Id, Long employee2Id, Duration duration) {
        Employee emp1 = employeeRepository.findById(employee1Id).orElseThrow();
        Employee emp2 = employeeRepository.findById(employee2Id).orElseThrow();

        List<FreeSlot> emp1FreeSlots = calculateFreeSlots(emp1.getMeetings());
        List<FreeSlot> emp2FreeSlots = calculateFreeSlots(emp2.getMeetings());

        return findOverlappingFreeSlots(emp1FreeSlots, emp2FreeSlots, duration);
    }

    // Check if any participants have meeting conflicts
    public List<Employee> findConflictingParticipants(MeetingDto meetingDTO) {
        List<Employee> conflictingEmployees = new ArrayList<>();
        List<Employee> participants = employeeRepository.findAllById(meetingDTO.getParticipantIds());

        for (Employee participant : participants) {
            if (hasMeetingConflict(participant, meetingDTO.getStartTime(), meetingDTO.getEndTime())) {
                conflictingEmployees.add(participant);
            }
        }

        return conflictingEmployees;
    }

    // Check if an employee has a meeting conflict
    private boolean hasMeetingConflict(Employee employee, LocalDateTime startTime, LocalDateTime endTime) {
        for (Meeting meeting : employee.getMeetings()) {
            if (startTime.isBefore(meeting.getEndTime()) && endTime.isAfter(meeting.getStartTime())) {
                return true;
            }
        }
        return false;
    }

    // Calculate free time slots for an employee based on their meetings
    private List<FreeSlot> calculateFreeSlots(List<Meeting> meetings) {
        List<FreeSlot> freeSlots = new ArrayList<>();
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));  // 9:00 AM
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0));  // 5:00 PM

        meetings.sort(Comparator.comparing(Meeting::getStartTime));  // Sort by start time

        // Add free time before the first meeting
        if (!meetings.isEmpty() && meetings.get(0).getStartTime().isAfter(startOfDay)) {
            freeSlots.add(new FreeSlot(startOfDay, meetings.get(0).getStartTime()));
        }

        // Add free time between meetings
        for (int i = 0; i < meetings.size() - 1; i++) {
            LocalDateTime endOfCurrentMeeting = meetings.get(i).getEndTime();
            LocalDateTime startOfNextMeeting = meetings.get(i + 1).getStartTime();
            if (endOfCurrentMeeting.isBefore(startOfNextMeeting)) {
                freeSlots.add(new FreeSlot(endOfCurrentMeeting, startOfNextMeeting));
            }
        }

        // Add free time after the last meeting
        if (!meetings.isEmpty() && meetings.get(meetings.size() - 1).getEndTime().isBefore(endOfDay)) {
            freeSlots.add(new FreeSlot(meetings.get(meetings.size() - 1).getEndTime(), endOfDay));
        }

        // If no meetings, whole day is free
        if (meetings.isEmpty()) {
            freeSlots.add(new FreeSlot(startOfDay, endOfDay));
        }

        return freeSlots;
    }

    // Find overlapping free time slots between two employees
    private List<FreeSlot> findOverlappingFreeSlots(List<FreeSlot> emp1FreeSlots, List<FreeSlot> emp2FreeSlots, Duration duration) {
        List<FreeSlot> overlappingFreeSlots = new ArrayList<>();
        int i = 0, j = 0;

        while (i < emp1FreeSlots.size() && j < emp2FreeSlots.size()) {
            FreeSlot emp1Slot = emp1FreeSlots.get(i);
            FreeSlot emp2Slot = emp2FreeSlots.get(j);

            LocalDateTime latestStart = emp1Slot.getStartTime().isAfter(emp2Slot.getStartTime()) ? emp1Slot.getStartTime() : emp2Slot.getStartTime();
            LocalDateTime earliestEnd = emp1Slot.getEndTime().isBefore(emp2Slot.getEndTime()) ? emp1Slot.getEndTime() : emp2Slot.getEndTime();

            if (Duration.between(latestStart, earliestEnd).compareTo(duration) >= 0) {
                overlappingFreeSlots.add(new FreeSlot(latestStart, earliestEnd));
            }

            if (emp1Slot.getEndTime().isBefore(emp2Slot.getEndTime())) {
                i++;
            } else {
                j++;
            }
        }

        return overlappingFreeSlots;
    }
}
