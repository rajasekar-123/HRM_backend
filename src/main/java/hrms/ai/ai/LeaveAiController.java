package hrms.ai.ai;

import hrms.ai.leave.dto.LeaveAiDecisionDto;
import hrms.ai.leave.dto.LeaveApplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/leave")
@RequiredArgsConstructor
public class LeaveAiController {

    private final LeaveAiService leaveAiService;

    /**
     * Employee asks AI if leave is advisable.
     * AI validates:
     * 1. Holiday
     * 2. Leave balance
     * 3. Policy via RAG
     */
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/check")
    public LeaveAiDecisionDto checkLeaveEligibility(@RequestBody LeaveApplyDto request
    )
    {
        return leaveAiService.evaluateLeave(request);
    }
}
