package hrms.ai.payroll;

import jakarta.persistence.*;
import lombok.*;

import java.time.YearMonth;

@Entity
@Table(name = "payroll")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private YearMonth salaryMonth;

    // Earnings
    private double basic;
    private double hra;
    private double allowances;

    // Deductions
    private double pf;
    private double esi;
    private double tax;
    private double lop;   // Loss of pay deduction

    private double grossSalary;
    private double totalDeductions;
    private double netSalary;
}
