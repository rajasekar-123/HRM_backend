package hrms.ai.attendance.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class AttendanceResponseDto {
    private Long id;
    private Long employeeId;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private String status;
    private Boolean correctionRequested;
    private String correctionReason;
}
