package hrms.ai.dashboard;

import hrms.ai.dashboard.dto.EmployeeDashboardDto;
import hrms.ai.dashboard.dto.HolidayResponseDto;
import hrms.ai.dashboard.dto.HrDashboardDto;
import hrms.ai.employee.EmployeeRepository;
import hrms.ai.attendance.AttendanceRepository;
import hrms.ai.leave.LeaveRepository;
import hrms.ai.payroll.PayrollRepository;
import hrms.ai.performance.PerformanceRepository;
import hrms.ai.holiday.Holiday;
import hrms.ai.holiday.HolidayRepository;
import hrms.ai.leave.leavebalance.LeaveBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

        private final EmployeeRepository employeeRepository;
        private final AttendanceRepository attendanceRepository;
        private final LeaveRepository leaveRepository;
        private final PayrollRepository payrollRepository;
        private final PerformanceRepository performanceRepository;
        private final HolidayRepository holidayRepository;
        private final LeaveBalanceRepository leaveBalanceRepository;

        public HrDashboardDto getHrDashboard() {

                long totalEmployees = employeeRepository.count();

                long presentToday = attendanceRepository
                                .countByDateAndStatus(LocalDate.now(), "PRESENT");

                double attendancePercentage = totalEmployees == 0 ? 0 : (presentToday * 100.0) / totalEmployees;

                long pendingLeaves = leaveRepository
                                .countByStatus("PENDING");

                boolean payrollGenerated = payrollRepository.findAll()
                                .stream()
                                .anyMatch(p -> p.getSalaryMonth()
                                                .equals(YearMonth.now()));

                long pendingReviews = performanceRepository.findAll()
                                .stream()
                                .filter(p -> p.getRating() == null)
                                .count();

                return HrDashboardDto.builder()
                                .totalEmployees(totalEmployees)
                                .presentToday(presentToday)
                                .attendancePercentage(attendancePercentage)
                                .pendingLeaves(pendingLeaves)
                                .payrollGenerated(payrollGenerated)
                                .pendingPerformanceReviews(pendingReviews)
                                .build();
        }

        public EmployeeDashboardDto getEmployeeDashboard(Long employeeId) {

                Map<String, Integer> leaveBalance = leaveBalanceRepository
                                .findByEmployeeId(employeeId)
                                .map(lb -> Map.<String, Integer>of(
                                                "CL", lb.getCasualLeave(),
                                                "SL", lb.getSickLeave(),
                                                "PL", lb.getPrivilegeLeave()))
                                .orElse(Map.of());

                List<HolidayResponseDto> holidayDtos = holidayRepository.findUpcomingHolidays(LocalDate.now())
                                .stream()
                                .map(
                                                h -> HolidayResponseDto.builder()
                                                                .name(h.getName())
                                                                .date(h.getHolidayDate())
                                                                .dayOfWeek(h.getHolidayDate().getDayOfWeek().toString())
                                                                .build())
                                .collect(Collectors.toList());

                String todayAttendance = attendanceRepository
                                .findStatusByEmployeeAndDate(employeeId, LocalDate.now())
                                .orElse("Not Marked");

                Double netSalary = payrollRepository
                                .findByEmployeeIdAndSalaryMonth(
                                                employeeId, YearMonth.now().minusMonths(1))
                                .map(p -> p.getNetSalary())
                                .orElse(null);

                var latestPerformance = performanceRepository
                                .findByEmployeeId(employeeId)
                                .stream()
                                .reduce((first, second) -> second)
                                .orElse(null);

                var todayAttendanceRecord = attendanceRepository
                                .findByEmployeeIdAndDate(employeeId, LocalDate.now())
                                .orElse(null);

                java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter
                                .ofPattern("HH:mm");

                String checkIn = todayAttendanceRecord != null && todayAttendanceRecord.getCheckIn() != null
                                ? todayAttendanceRecord.getCheckIn().format(timeFormatter)
                                : "-";

                String checkOut = todayAttendanceRecord != null && todayAttendanceRecord.getCheckOut() != null
                                ? todayAttendanceRecord.getCheckOut().format(timeFormatter)
                                : "-";

                return EmployeeDashboardDto.builder()
                                .leaveBalance(leaveBalance)
                                .todayAttendance(todayAttendance)
                                .checkIn(checkIn)
                                .checkOut(checkOut)
                                .netSalary(netSalary)
                                .latestRating(latestPerformance != null
                                                ? latestPerformance.getRating()
                                                : null)
                                .appraisalTriggered(latestPerformance != null
                                                && latestPerformance.isAppraisalTriggered())
                                .upcomingHolidays(holidayDtos)
                                .build();
        }
}
