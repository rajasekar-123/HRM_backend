package hrms.ai.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;


    public LeavePolicy savePolicy(LeavePolicy policy) {
        return policyRepository.save(policy);
    }


    public String getPolicyText(String type) {
        return policyRepository
                .findByPolicyTypeAndActiveTrue(type)
                .map(LeavePolicy::getPolicyText)
                .orElse("No policy found.");
    }
}
