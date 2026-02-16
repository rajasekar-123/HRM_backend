package hrms.ai.leave.leavebalance;


import hrms.ai.leave.LeaveRequest;
import hrms.ai.leave.leavebalance.LeaveBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;

    // HR â€“ Initialize leave balance when employee is created
    public void initializeBalance(Long employeeId) {

        LeaveBalance balance = LeaveBalance.builder()
                .employeeId(employeeId)
                .casualLeave(12)
                .sickLeave(8)
                .privilegeLeave(15)
                .build();

        leaveBalanceRepository.save(balance);
    }

    // AI / Leave â€“ Check if balance is sufficient
    public boolean hasSufficientBalance(
            Long employeeId,
            String leaveType,
            int days) {

        LeaveBalance balance = leaveBalanceRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));

        return switch (leaveType) {
            case "CL" -> balance.getCasualLeave() >= days;
            case "SL" -> balance.getSickLeave() >= days;
            case "PL" -> balance.getPrivilegeLeave() >= days;
            default -> false;
        };
    }


    public void deductLeave(LeaveRequest leave) {

        LeaveBalance balance = leaveBalanceRepository.findByEmployeeId(leave.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));

        int days = leave.getTotalDays();

        switch (leave.getLeaveType()) {
            case "CL" -> balance.setCasualLeave(balance.getCasualLeave() - days);
            case "SL" -> balance.setSickLeave(balance.getSickLeave() - days);
            case "PL" -> balance.setPrivilegeLeave(balance.getPrivilegeLeave() - days);
        }

        leaveBalanceRepository.save(balance);
    }

    // Employee â€“ View own leave balance
    public LeaveBalance getBalance(Long employeeId) {
        return leaveBalanceRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Leave balance not found"));
    }
}
