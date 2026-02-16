package hrms.ai.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HrDashboardDto {

    private long totalEmployees;
    private long presentToday;
    private double attendancePercentage;
    private long pendingLeaves;
    private boolean payrollGenerated;
    private long pendingPerformanceReviews;
}

