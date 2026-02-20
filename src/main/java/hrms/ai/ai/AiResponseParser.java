package hrms.ai.ai;

import com.fasterxml.jackson.databind.ObjectMapper;

import hrms.ai.leave.dto.LeaveAiDecisionDto;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AiResponseParser {

    private final ObjectMapper mapper = new ObjectMapper();

    // Pattern to find JSON object in messy LLM output
    private static final Pattern JSON_PATTERN = Pattern.compile("\\{[^{}]*\"eligible\"[^{}]*\\}", Pattern.DOTALL);

    public LeaveAiDecisionDto parse(String aiResponse) {
        try {
            // 1. Try direct parse first
            return tryParse(aiResponse);
        } catch (Exception e1) {
            try {
                // 2. Clean up markdown code blocks
                String cleaned = aiResponse
                        .replaceAll("```json\\s*", "")
                        .replaceAll("```\\s*", "")
                        .trim();
                return tryParse(cleaned);
            } catch (Exception e2) {
                try {
                    // 3. Extract JSON object using regex
                    Matcher matcher = JSON_PATTERN.matcher(aiResponse);
                    if (matcher.find()) {
                        return tryParse(matcher.group());
                    }
                } catch (Exception e3) {
                    // Fall through
                }

                // 4. Try to build response from text analysis
                return buildFromText(aiResponse);
            }
        }
    }

    private LeaveAiDecisionDto tryParse(String json) throws Exception {
        return mapper.readValue(json.trim(), LeaveAiDecisionDto.class);
    }

    private LeaveAiDecisionDto buildFromText(String text) {
        String lower = text.toLowerCase();

        boolean eligible = !lower.contains("not eligible")
                && !lower.contains("not advisable")
                && !lower.contains("cannot")
                && !lower.contains("denied")
                && !lower.contains("insufficient")
                && !lower.contains("\"eligible\": false")
                && !lower.contains("\"eligible\":false");

        // Extract a short reason from the text
        String reason = text.length() > 200 ? text.substring(0, 200) + "..." : text;
        // Clean up for display
        reason = reason.replaceAll("[\\r\\n]+", " ").replaceAll("\\s+", " ").trim();

        return LeaveAiDecisionDto.builder()
                .eligible(eligible)
                .reason(reason)
                .suggestion(eligible
                        ? "Your leave request looks good. Go ahead and submit!"
                        : "Please review your request or contact HR for assistance.")
                .build();
    }
}
