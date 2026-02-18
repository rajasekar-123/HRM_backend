package hrms.ai.holiday;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    // HR â€“ Add holiday
    @PreAuthorize("hasRole('HR')")
    @PostMapping
    public Holiday addHoliday(@RequestBody Holiday holiday) {
        return holidayService.addHoliday(holiday);
    }

    // HR â€“ Update holiday
    @PreAuthorize("hasRole('HR')")
    @PutMapping("/{id}")
    public Holiday updateHoliday(
            @PathVariable Long id,
            @RequestBody Holiday holiday) {
        return holidayService.updateHoliday(id, holiday);
    }

    // HR / Employee â€“ View holidays
    @PreAuthorize("hasAnyRole('HR','EMPLOYEE')")
    @GetMapping
    public List<Holiday> getAllHolidays() {
        return holidayService.getAllHolidays();
    }
}
