package hrms.ai.ai;

import hrms.ai.leave.dto.LeaveApplyDto;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

  public String buildLeavePrompt(LeaveApplyDto dto, String policyContext) {

    return """
        You are an HR Leave Policy Assistant. Analyze this leave request.

        POLICY:
        %s

        REQUEST:
        - Type: %s
        - From: %s
        - To: %s
        - Reason: %s

        Reply ONLY with this JSON (no extra text):
        {"eligible": true, "reason": "short explanation", "suggestion": "what to do next"}

        Set eligible to true if leave follows policy, false otherwise.
        """.formatted(
        policyContext,
        dto.getLeaveType(),
        dto.getFromDate(),
        dto.getToDate(),
        dto.getReason());
  }
}
