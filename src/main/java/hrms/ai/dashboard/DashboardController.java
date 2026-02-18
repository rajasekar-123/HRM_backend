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
        return dashboardService.getHrDashboard();
    }

    // Employee Overview
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/{employeeId}")
    public EmployeeDashboardDto employeeOverview(
            @PathVariable Long employeeId) {
        return dashboardService.getEmployeeDashboard(employeeId);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/me")
    public EmployeeDashboardDto employeeOverviewMe(java.security.Principal principal) {
        return dashboardService.getEmployeeDashboardByEmail(principal.getName());
    }
}
