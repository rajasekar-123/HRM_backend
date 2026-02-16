package hrms.ai.performance.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
    public class PerformanceRequestDto
{
        private Long employeeId;
        private String goal;
        private String reviewPeriod;
    }


