package hrms.ai.performance;

import hrms.ai.performance.dto.PerformanceRequestDto;
import hrms.ai.performance.dto.PerformanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    // HR â€“ Assign goal
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/assign")
    public PerformanceResponseDto assignGoal(@RequestBody PerformanceRequestDto dto) {
        return performanceService.assignGoal(dto);
    }

    // HR â€“ Review employee
    @PreAuthorize("hasRole('HR')")
    @PostMapping("/review/{id}")
    public PerformanceResponseDto review(
            @PathVariable Long id,
            @RequestParam Integer rating,
            @RequestParam String feedback) {

        return performanceService.reviewPerformance(id, rating, feedback);
    }

    // Employee â€“ Submit self assessment
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/self/{id}")
    public PerformanceResponseDto selfAssessment(
            @PathVariable Long id,
            @RequestParam String assessment) {

        return performanceService.submitSelfAssessment(id, assessment);
    }

    // Employee â€“ View own performance
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public List<PerformanceResponseDto> getPerformance(
            @PathVariable Long employeeId) {

        return performanceService.getEmployeePerformance(employeeId);
    }
}
