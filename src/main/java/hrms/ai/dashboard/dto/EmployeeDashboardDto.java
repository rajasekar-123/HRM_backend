package hrms.ai.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class EmployeeDashboardDto {

    private Map<String, Integer> leaveBalance;
    private String todayAttendance;
    private String checkIn; // LocalTime formatted as String
    private String checkOut; // LocalTime formatted as String
    private Double netSalary;
    private Integer latestRating;
    private Boolean appraisalTriggered;
    private List<HolidayResponseDto> upcomingHolidays;
}
