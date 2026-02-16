package hrms.ai.leave.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveApplyDto {

    private Long employeeId;
    private String leaveType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
}
