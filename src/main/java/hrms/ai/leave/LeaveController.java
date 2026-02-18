package hrms.ai.leave;

import hrms.ai.leave.dto.LeaveApplyDto;
import hrms.ai.leave.dto.LeaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    // Employee â€“ Apply leave
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/leave/apply")
    public LeaveResponseDto apply(@RequestBody LeaveApplyDto dto) {
        return leaveService.applyLeave(dto);
    }

    // Employee â€“ View own leaves
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/leave/{employeeId}")
    public List<LeaveResponseDto> myLeaves(@PathVariable Long employeeId) {
        return leaveService.getMyLeaves(employeeId);
    }

    // HR â€“ View all leave requests
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/hr/leave")
    public List<LeaveResponseDto> allLeaves() {
        return leaveService.getAllLeaves();
    }

    // HR â€“ Approve leave
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/hr/leave/approve/{leaveId}")
    public LeaveResponseDto approve(@PathVariable Long leaveId) {
        return leaveService.approveLeave(leaveId);
    }

    // HR â€“ Reject leave
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/hr/leave/reject/{leaveId}")
    public LeaveResponseDto reject(
            @PathVariable Long leaveId,
            @RequestParam String reason) {
        return leaveService.rejectLeave(leaveId, reason);
    }
}
