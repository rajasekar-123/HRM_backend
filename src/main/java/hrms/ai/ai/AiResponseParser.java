package hrms.ai.ai;

import com.fasterxml.jackson.databind.ObjectMapper;

import hrms.ai.leave.dto.LeaveAiDecisionDto;
import org.springframework.stereotype.Component;

@Component
public class AiResponseParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public LeaveAiDecisionDto parse(String aiResponse) {
        try {
            // Clean up Markdown code blocks if present
            String cleanJson = aiResponse.replaceAll("```json", "").replaceAll("```", "").trim();
            return mapper.readValue(cleanJson, LeaveAiDecisionDto.class);
        } catch (Exception e) {
            return LeaveAiDecisionDto.builder()
                    .eligible(false)
                    .reason("AI response could not be validated")
                    .suggestion("Please contact HR")
                    .build();
        }
    }
}
