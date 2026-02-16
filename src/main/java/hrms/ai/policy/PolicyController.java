package hrms.ai.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;


    @PreAuthorize("hasRole('HR')")
    @PostMapping
    public LeavePolicy savePolicy(@RequestBody LeavePolicy policy) {
        return policyService.savePolicy(policy);
    }
}
