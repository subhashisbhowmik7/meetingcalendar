package com.freightfox.meetingcalendar.controllers;


import com.freightfox.meetingcalendar.dtos.MeetingDto;
import com.freightfox.meetingcalendar.entities.Employee;
import com.freightfox.meetingcalendar.entities.FreeSlot;
import com.freightfox.meetingcalendar.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    // Find free slots between two employees for a given duration
    @GetMapping("/free-slots")
    public ResponseEntity<List<FreeSlot>> findFreeSlots(
            @RequestParam Long employee1Id,
            @RequestParam Long employee2Id,
            @RequestParam Duration duration) {

        List<FreeSlot> freeSlots = meetingService.findFreeSlots(employee1Id, employee2Id, duration);
        return ResponseEntity.ok(freeSlots);
    }

    // Check if any participants have conflicts for a meeting
    @PostMapping("/conflicts")
    public ResponseEntity<List<Employee>> checkConflicts(@RequestBody MeetingDto meetingDTO) {
        List<Employee> conflictingEmployees = meetingService.findConflictingParticipants(meetingDTO);
        return ResponseEntity.ok(conflictingEmployees);
    }
}
