package hrms.ai.employee.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EmployeeResponseDto {
    private Long id;
    private String employeeCode;
    private String fullName;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private LocalDate dateOfJoining;
    private Boolean active;
}