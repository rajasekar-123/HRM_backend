package hrms.ai.ai;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class RagRetrieverService {

    private final Map<String, String> policyStore = new HashMap<>();

    @PostConstruct
    public void init() {
        // Short codes used by frontend
        policyStore.put("CL",
                "12 days of casual leave per year. Cannot be combined with other leave types.");
        policyStore.put("SL",
                "Employees get 8 days of sick leave. Medical certificate required for more than 2 consecutive days.");
        policyStore.put("PL",
                "Employees are entitled to 15 days of privilege leave per year. Unused leave can be carried forward up to 5 days.");

        // Full names as aliases
        policyStore.put("Casual Leave", policyStore.get("CL"));
        policyStore.put("Sick Leave", policyStore.get("SL"));
        policyStore.put("Privilege Leave", policyStore.get("PL"));
        policyStore.put("Annual Leave", policyStore.get("PL"));
        policyStore.put("Maternity Leave", "26 weeks of paid maternity leave for female employees.");
        policyStore.put("Paternity Leave", "2 weeks of paid paternity leave for male employees.");
    }

    /**
     * Retrieve leave policy context for a given leave type.
     * Returns relevant policy text to augment LLM prompts.
     */
    public String retrieveLeavePolicy(String leaveType) {
        return policyStore.getOrDefault(leaveType,
                "Standard leave policy applies. Please consult HR for more details.");
    }
}
