package hrms.ai.performance.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PerformanceResponseDto {

    private Long id;
    private Long employeeId;
    private String goal;
    private String reviewPeriod;
    private Integer rating;
    private String managerFeedback;
    private String selfAssessment;
    private boolean appraisalTriggered;
    private LocalDate reviewDate;
}
