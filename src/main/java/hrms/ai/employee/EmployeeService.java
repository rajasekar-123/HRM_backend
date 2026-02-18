package hrms.ai.employee;

import hrms.ai.employee.dto.EmployeeRequestDto;
import hrms.ai.employee.dto.EmployeeResponseDto;
import hrms.ai.user.Role;
import hrms.ai.user.User;
import hrms.ai.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // HR â€“ Create employee + auto-create user account
    public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {

        Employee employee = Employee.builder()
                .employeeCode(dto.getEmployeeCode())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .dateOfJoining(dto.getDateOfJoining())
                .active(true)
                .build();

        Employee saved = employeeRepository.save(employee);

        // Auto-create a User login account for the new employee
        if (userRepository.findByUsername(dto.getEmail()).isEmpty()) {
            String rawPassword = (dto.getPassword() != null && !dto.getPassword().isEmpty())
                    ? dto.getPassword()
                    : "welcome123";

            User user = User.builder()
                    .username(dto.getEmail())
                    .password(passwordEncoder.encode(rawPassword))
                    .role(Role.ROLE_EMPLOYEE)
                    .active(true)
                    .build();
            userRepository.save(user);
            log.info("Auto-created user account for employee: {} (username: {})", dto.getFullName(), dto.getEmail());
        }

        return mapToResponse(saved);
    }

    // HR â€“ Get all employees
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // HR â€“ Update employee
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFullName(dto.getFullName());
        employee.setPhone(dto.getPhone());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());

        return mapToResponse(employeeRepository.save(employee));
    }

    // HR â€“ Deactivate employee (soft delete)
    public void deactivateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setActive(false);
        employeeRepository.save(employee);
    }

    // Employee â€“ View own profile
    public EmployeeResponseDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public EmployeeResponseDto getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Employee not found for email: " + email));
    }

    // Mapper (NO MapStruct â€“ real world often manual)
    private EmployeeResponseDto mapToResponse(Employee e) {
        return EmployeeResponseDto.builder()
                .id(e.getId())
                .employeeCode(e.getEmployeeCode())
                .fullName(e.getFullName())
                .email(e.getEmail())
                .phone(e.getPhone())
                .department(e.getDepartment())
                .designation(e.getDesignation())
                .dateOfJoining(e.getDateOfJoining())
                .active(e.getActive())
                .build();
    }
}
