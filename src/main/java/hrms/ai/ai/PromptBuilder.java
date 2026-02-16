package hrms.ai.ai;



import hrms.ai.leave.dto.LeaveApplyDto;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildLeavePrompt(LeaveApplyDto dto, String policyContext) {

        return """
        You are an AI Leave Policy Assistant inside an HRM system.

        STRICT RULES:
        - Use ONLY the policy context provided below.
        - Do NOT assume or invent rules.
        - Do NOT approve leave.
        - Only suggest whether leave is advisable or not.
        - Respond ONLY in valid JSON.
        - Keep explanation short and clear.

        POLICY CONTEXT:
        ----------------
        %s

        EMPLOYEE REQUEST:
        ----------------
        Leave Type   : %s
        From Date   : %s
        To Date     : %s
        Reason      : %s

        RESPONSE FORMAT (JSON ONLY):
        {
          "eligible": true | false,
          "reason": "short policy-based explanation",
          "suggestion": "what employee should do next"
        }
        """.formatted(
                policyContext,
                dto.getLeaveType(),
                dto.getFromDate(),
                dto.getToDate(),
                dto.getReason()
        );
    }
}
