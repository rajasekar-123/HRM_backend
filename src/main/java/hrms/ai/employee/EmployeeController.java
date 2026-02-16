package hrms.ai.employee;

import hrms.ai.employee.dto.EmployeeRequestDto;
import hrms.ai.employee.dto.EmployeeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // HR â€“ Create employee
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/hr/employees")
    public EmployeeResponseDto create(@RequestBody EmployeeRequestDto dto) {
        return employeeService.createEmployee(dto);
    }

    // HR â€“ View all employees
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/hr/employees")
    public List<EmployeeResponseDto> getAll() {
        return employeeService.getAllEmployees();
    }

    // HR â€“ Update employee
    @PreAuthorize("hasRole('HR')")
    @PutMapping("/hr/employees/{id}")
    public EmployeeResponseDto update(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDto dto) {
        return employeeService.updateEmployee(id, dto);
    }

    // HR â€“ Deactivate employee
    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/hr/employees/{id}")
    public void deactivate(@PathVariable Long id) {
        employeeService.deactivateEmployee(id);
    }

    // Employee â€“ View own profile
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/profile/{id}")
    public EmployeeResponseDto getProfile(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HR')")
    @GetMapping("/employee/me")
    public EmployeeResponseDto getMyProfile(java.security.Principal principal) {
        return employeeService.getEmployeeByEmail(principal.getName());
    }
}
