package hrms.ai.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class HolidayResponseDto {
    private String name;
    private LocalDate date;
    private String dayOfWeek;
}
