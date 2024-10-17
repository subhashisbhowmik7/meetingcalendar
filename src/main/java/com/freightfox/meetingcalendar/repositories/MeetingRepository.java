package com.freightfox.meetingcalendar.repositories;


import com.freightfox.meetingcalendar.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
