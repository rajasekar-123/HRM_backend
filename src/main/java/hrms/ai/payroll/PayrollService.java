package hrms.ai.payroll;

import hrms.ai.leave.LeaveRepository;
import hrms.ai.leave.LeaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final LeaveRepository leaveRepository;

    // HR â€“ Generate monthly payroll
    public Payroll generatePayroll(
            Long employeeId,
            YearMonth month,
            double basic,
            double hra,
            double allowances,
            double pf,
            double esi,
            double tax
    ) {

        // Calculate LOP from approved leave
        double lopDeduction = calculateLOP(employeeId, month, basic);

        double gross = basic + hra + allowances;
        double deductions = pf + esi + tax + lopDeduction;
        double net = gross - deductions;

        Payroll payroll = Payroll.builder()
                .employeeId(employeeId)
                .salaryMonth(month)
                .basic(basic)
                .hra(hra)
                .allowances(allowances)
                .pf(pf)
                .esi(esi)
                .tax(tax)
                .lop(lopDeduction)
                .grossSalary(gross)
                .totalDeductions(deductions)
                .netSalary(net)
                .build();

        return payrollRepository.save(payroll);
    }

    // Employee â€“ View own payslips
    public List<Payroll> getEmployeePayroll(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }

    // Calculate LOP based on approved leave
    private double calculateLOP(Long employeeId, YearMonth month, double basic) {

        List<LeaveRequest> leaves = leaveRepository.findByEmployeeId(employeeId);

        int lopDays = leaves.stream()
                .filter(l -> "APPROVED".equals(l.getStatus()))
                .filter(l -> YearMonth.from(l.getFromDate()).equals(month))
                .mapToInt(LeaveRequest::getTotalDays)
                .sum();

        double dailySalary = basic / 30.0;

        return lopDays * dailySalary;
    }
}

