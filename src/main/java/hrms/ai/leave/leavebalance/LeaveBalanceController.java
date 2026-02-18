package hrms.ai.leave.leavebalance;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee/leave-balance")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    // Employee â€“ View own leave balance
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public LeaveBalance getMyBalance(@PathVariable Long employeeId) {
        return leaveBalanceService.getBalance(employeeId);
    }
}
