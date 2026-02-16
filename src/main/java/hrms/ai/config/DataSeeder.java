package hrms.ai.config;

import hrms.ai.employee.Employee;
import hrms.ai.employee.EmployeeRepository;
import hrms.ai.holiday.Holiday;
import hrms.ai.user.Role;
import hrms.ai.user.User;
import hrms.ai.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final hrms.ai.holiday.HolidayRepository holidayRepository;
    private final hrms.ai.leave.leavebalance.LeaveBalanceRepository leaveBalanceRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {
            User hr = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_HR)
                    .active(true)
                    .build();
            userRepository.save(hr);
            log.info("Seeded HR user: admin / admin123");
        } else {
            User hr = userRepository.findByUsername("admin").get();
            hr.setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(hr);
            log.info("Updated HR user password: admin / admin123");
        }

        if (userRepository.findByUsername("employee1").isEmpty()) {
            User emp = User.builder()
                    .username("employee1")
                    .password(passwordEncoder.encode("emp123"))
                    .role(Role.ROLE_EMPLOYEE)
                    .active(true)
                    .build();
            userRepository.save(emp);
            log.info("✅ Seeded Employee user: employee1 / emp123");
        }

        if (employeeRepository.count() == 0) {
            Employee employee = Employee.builder()
                    .employeeCode("EMP001")
                    .fullName("Arun")
                    .email("employee1") // Matched with username
                    .phone("9876543210")
                    .department("Engineering")
                    .designation("Software Engineer")
                    .dateOfJoining(LocalDate.of(2024, 1, 15))
                    .active(true)
                    .build();
            employeeRepository.save(employee);
            log.info("✅ Seeded employee: John Doe (EMP001)");
        }

        // Ensure Admin has an Employee record for Profile view
        if (employeeRepository.findByEmail("admin").isEmpty()) {
            Employee adminEmp = Employee.builder()
                    .employeeCode("ADM001")
                    .fullName("System Admin")
                    .email("admin")
                    .phone("0000000000")
                    .department("Human Resources")
                    .designation("HR Manager")
                    .dateOfJoining(LocalDate.of(2023, 1, 1))
                    .active(true)
                    .build();
            employeeRepository.save(adminEmp);
            log.info("✅ Seeded Admin Employee record for Profile access");
        }

        // Patch for existing data
        employeeRepository.findByEmployeeCode("EMP001").ifPresent(emp -> {
            if (!"employee1".equals(emp.getEmail())) {
                emp.setEmail("employee1");
                employeeRepository.save(emp);
                log.info("✅ Patched demo employee email to 'employee1' for login consistency");
            }
        });

        // Seed Holidays
        // Seed Leave Balance for Employee 1 (if completely empty, this is a simple
        // checked seed)
        // In a real app, you'd check by employee ID.
        // Seed Leave Balance for Employee 1
        employeeRepository.findByEmployeeCode("EMP001").ifPresent(emp -> {
            if (leaveBalanceRepository.findByEmployeeId(emp.getId()).isEmpty()) {
                hrms.ai.leave.leavebalance.LeaveBalance lb = hrms.ai.leave.leavebalance.LeaveBalance.builder()
                        .employeeId(emp.getId())
                        .casualLeave(10)
                        .sickLeave(10)
                        .privilegeLeave(15)
                        .build();
                leaveBalanceRepository.save(lb);
                log.info("✅ Seeded Leave Balance for EMP001");
            }
        });

        // Seed Holidays for Current Year
        int year = LocalDate.now().getYear();
        if (!holidayRepository.existsByHolidayDate(LocalDate.of(year, 1, 1))) {
            holidayRepository
                    .save(new Holiday(null, "New Year", LocalDate.of(year, 1, 1), "New Year Celebration", true));
            holidayRepository
                    .save(new Holiday(null, "Republic Day", LocalDate.of(year, 1, 26), "National Holiday", true));
            holidayRepository
                    .save(new Holiday(null, "Independence Day", LocalDate.of(year, 8, 15), "National Holiday", true));
            holidayRepository
                    .save(new Holiday(null, "Christmas", LocalDate.of(year, 12, 25), "Christmas Celebration", true));
            log.info("✅ Seeded sample holidays for " + year);
        }
    }
}
