package hrms.ai.attendance;

import hrms.ai.attendance.dto.AttendanceRequestDto;
import hrms.ai.attendance.dto.AttendanceResponseDto;
import hrms.ai.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/attendance/check-in/{employeeId}")
    public AttendanceResponseDto checkIn(@PathVariable Long employeeId) {
        return attendanceService.checkIn(employeeId);
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/attendance/check-in/me")
    public AttendanceResponseDto checkInMe(java.security.Principal principal) {
        Long empId = employeeService.getEmployeeByEmail(principal.getName()).getId();
        return attendanceService.checkIn(empId);
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/attendance/check-out/{employeeId}")
    public AttendanceResponseDto checkOut(@PathVariable Long employeeId) {
        return attendanceService.checkOut(employeeId);
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/attendance/check-out/me")
    public AttendanceResponseDto checkOutMe(java.security.Principal principal) {
        Long empId = employeeService.getEmployeeByEmail(principal.getName()).getId();
        return attendanceService.checkOut(empId);
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/attendance/{employeeId}")
    public List<AttendanceResponseDto> myAttendance(@PathVariable Long employeeId) {
        return attendanceService.getMyAttendance(employeeId);
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/attendance/correction/{attendanceId}")
    public AttendanceResponseDto requestCorrection(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceRequestDto dto) {
        return attendanceService.requestCorrection(attendanceId, dto);
    }


    @PreAuthorize("hasRole('HR')")
    @GetMapping("/hr/attendance")
    public List<AttendanceResponseDto> allAttendance() {
        return attendanceService.getAllAttendance();
    }


    @PreAuthorize("hasRole('HR')")
    @PostMapping("/hr/attendance/approve/{attendanceId}")
    public AttendanceResponseDto approveCorrection(@PathVariable Long attendanceId) {
        return attendanceService.approveCorrection(attendanceId);
    }
}
