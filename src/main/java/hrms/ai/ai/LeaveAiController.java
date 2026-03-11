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


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/check")
    public LeaveAiDecisionDto checkLeaveEligibility(@RequestBody LeaveApplyDto request)
    {
        return leaveAiService.evaluateLeave(request);
    }
}
