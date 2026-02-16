package hrms.ai.payroll;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    // HR â€“ Generate payroll
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/generate")
    public Payroll generatePayroll(
            @RequestParam Long employeeId,
            @RequestParam String month,
            @RequestParam double basic,
            @RequestParam double hra,
            @RequestParam double allowances,
            @RequestParam double pf,
            @RequestParam double esi,
            @RequestParam double tax
    ) {

        return payrollService.generatePayroll(
                employeeId,
                YearMonth.parse(month),
                basic,
                hra,
                allowances,
                pf,
                esi,
                tax
        );
    }

    // Employee â€“ View payslips
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public List<Payroll> getMyPayroll(@PathVariable Long employeeId) {
        return payrollService.getEmployeePayroll(employeeId);
    }
}
