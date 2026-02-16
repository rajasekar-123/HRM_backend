package hrms.ai.leave.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LeaveAiDecisionDto {
    private boolean eligible;
    private String reason;
    private String suggestion;
}

