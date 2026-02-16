package hrms.ai.attendance;


import hrms.ai.attendance.dto.AttendanceRequestDto;
import hrms.ai.attendance.dto.AttendanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    // Employee â€“ Check in
    public AttendanceResponseDto checkIn(Long employeeId) {

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, today)
                .orElse(
                        Attendance.builder()
                                .employeeId(employeeId)
                                .date(today)
                                .status("PRESENT")
                                .build()
                );

        attendance.setCheckIn(java.time.LocalTime.now());

        return mapToResponse(attendanceRepository.save(attendance));
    }

    // Employee â€“ Check out
    public AttendanceResponseDto checkOut(Long employeeId) {

        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("Check-in not found"));

        attendance.setCheckOut(java.time.LocalTime.now());

        return mapToResponse(attendanceRepository.save(attendance));
    }

    // Employee â€“ View own attendance
    public List<AttendanceResponseDto> getMyAttendance(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Employee â€“ Request correction
    public AttendanceResponseDto requestCorrection(Long attendanceId, AttendanceRequestDto dto) {

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setCorrectionRequested(true);
        attendance.setCorrectionReason(dto.getCorrectionReason());

        return mapToResponse(attendanceRepository.save(attendance));
    }

    // HR â€“ View all attendance
    public List<AttendanceResponseDto> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // HR â€“ Approve correction
    public AttendanceResponseDto approveCorrection(Long attendanceId) {

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setCorrectionRequested(false);
        attendance.setStatus("PRESENT");

        return mapToResponse(attendanceRepository.save(attendance));
    }

    // Mapper
    private AttendanceResponseDto mapToResponse(Attendance a) {
        return AttendanceResponseDto.builder()
                .id(a.getId())
                .employeeId(a.getEmployeeId())
                .date(a.getDate())
                .checkIn(a.getCheckIn())
                .checkOut(a.getCheckOut())
                .status(a.getStatus())
                .correctionRequested(a.getCorrectionRequested())
                .correctionReason(a.getCorrectionReason())
                .build();
    }
}
