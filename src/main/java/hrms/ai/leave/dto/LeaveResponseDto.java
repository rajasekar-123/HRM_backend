package hrms.ai.leave.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class LeaveResponseDto {

    private Long id;
    private Long employeeId;
    private String leaveType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer totalDays;
    private String status;
    private String reason;
    private String rejectionReason;
    private LocalDate appliedDate;
}
