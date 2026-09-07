package com.hrms.attendance.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attendance_breaks")
public class AttendanceBreak {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "attendance_id", nullable = false) private UUID attendanceId;
    @Column(name = "break_type", nullable = false, length = 50) private String breakType = "lunch";
    @Column(name = "start_time", nullable = false) private LocalDateTime startTime;
    @Column(name = "end_time") private LocalDateTime endTime;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getAttendanceId() { return attendanceId; }
    public void setAttendanceId(UUID attendanceId) { this.attendanceId = attendanceId; }
    public String getBreakType() { return breakType; }
    public void setBreakType(String breakType) { this.breakType = breakType; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
