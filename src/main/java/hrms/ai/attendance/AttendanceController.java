package hrms.ai.attendance;


import hrms.ai.attendance.dto.AttendanceRequestDto;
import hrms.ai.attendance.dto.AttendanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // Employee â€“ Check in
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/attendance/check-in/{employeeId}")
    public AttendanceResponseDto checkIn(@PathVariable Long employeeId) {
        return attendanceService.checkIn(employeeId);
    }

    // Employee â€“ Check out
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/attendance/check-out/{employeeId}")
    public AttendanceResponseDto checkOut(@PathVariable Long employeeId) {
        return attendanceService.checkOut(employeeId);
    }

    // Employee â€“ View own attendance
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/attendance/{employeeId}")
    public List<AttendanceResponseDto> myAttendance(@PathVariable Long employeeId) {
        return attendanceService.getMyAttendance(employeeId);
    }

    // Employee â€“ Request correction
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/attendance/correction/{attendanceId}")
    public AttendanceResponseDto requestCorrection(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceRequestDto dto) {
        return attendanceService.requestCorrection(attendanceId, dto);
    }

    // HR â€“ View all attendance
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/hr/attendance")
    public List<AttendanceResponseDto> allAttendance() {
        return attendanceService.getAllAttendance();
    }

    // HR â€“ Approve correction
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/hr/attendance/approve/{attendanceId}")
    public AttendanceResponseDto approveCorrection(@PathVariable Long attendanceId) {
        return attendanceService.approveCorrection(attendanceId);
    }
}
