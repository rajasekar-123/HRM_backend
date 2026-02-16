package hrms.ai.dashboard;

import hrms.ai.dashboard.dto.EmployeeDashboardDto;
import hrms.ai.dashboard.dto.HrDashboardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // HR Overview
    @PreAuthorize("hasAuthority('ROLE_HR')")
    @GetMapping("/hr")
    public HrDashboardDto hrOverview() {
        System.out.println("DashboardController: Received request for hrOverview");
        HrDashboardDto dummy = HrDashboardDto.builder()
                .totalEmployees(10)
                .presentToday(5)
                .attendancePercentage(50.0)
                .pendingLeaves(2)
                .payrollGenerated(true)
                .pendingPerformanceReviews(3)
                .build();
        System.out.println("DashboardController: Returning dummy data");
        return dummy;
        // return dashboardService.getHrDashboard();
    }

    // Employee Overview
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/{employeeId}")
    public EmployeeDashboardDto employeeOverview(
            @PathVariable Long employeeId) {
        return dashboardService.getEmployeeDashboard(employeeId);
    }
}
