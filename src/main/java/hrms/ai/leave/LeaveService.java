package hrms.ai.leave;

import hrms.ai.leave.dto.LeaveApplyDto;

import hrms.ai.holiday.HolidayService;
import hrms.ai.leave.dto.LeaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final HolidayService holidayService;

    // Employee â€“ View own leaves
    public List<LeaveResponseDto> getMyLeaves(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // HR â€“ View all leave requests
    public List<LeaveResponseDto> getAllLeaves() {
        return leaveRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // HR â€“ Approve leave
    public LeaveResponseDto approveLeave(Long leaveId) {

        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus("APPROVED");

        return mapToResponse(leaveRepository.save(leave));
    }

    // HR â€“ Reject leave
    public LeaveResponseDto rejectLeave(Long leaveId, String rejectionReason) {

        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus("REJECTED");
        leave.setRejectionReason(rejectionReason);

        return mapToResponse(leaveRepository.save(leave));
    }

    // Mapper
    private LeaveResponseDto mapToResponse(LeaveRequest l) {
        return LeaveResponseDto.builder()
                .id(l.getId())
                .employeeId(l.getEmployeeId())
                .leaveType(l.getLeaveType())
                .fromDate(l.getFromDate())
                .toDate(l.getToDate())
                .totalDays(l.getTotalDays())
                .status(l.getStatus())
                .reason(l.getReason())
                .rejectionReason(l.getRejectionReason())
                .appliedDate(l.getAppliedDate())
                .build();
    }

    public LeaveResponseDto applyLeave(LeaveApplyDto dto) {

        // 1ï¸âƒ£ Holiday check
        LocalDate current = dto.getFromDate();
        while (!current.isAfter(dto.getToDate())) {

            if (holidayService.isHoliday(current)) {
                throw new RuntimeException(
                        "Leave cannot be applied on holiday: " + current);
            }
            current = current.plusDays(1);
        }

        // 2ï¸âƒ£ Calculate days
        int days = (int) ChronoUnit.DAYS.between(
                dto.getFromDate(),
                dto.getToDate()) + 1;

        // 3ï¸âƒ£ Save leave
        LeaveRequest leave = LeaveRequest.builder()
                .employeeId(dto.getEmployeeId())
                .leaveType(dto.getLeaveType())
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate())
                .totalDays(days)
                .reason(dto.getReason())
                .status("PENDING")
                .appliedDate(LocalDate.now())
                .build();

        return mapToResponse(leaveRepository.save(leave));
    }
}
