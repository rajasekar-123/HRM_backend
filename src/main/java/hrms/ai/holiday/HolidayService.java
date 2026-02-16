package hrms.ai.holiday;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;

    // HR â€“ Add holiday
    public Holiday addHoliday(Holiday holiday) {

        if (holidayRepository.existsByHolidayDate(holiday.getHolidayDate())) {
            throw new RuntimeException("Holiday already exists for this date");
        }

        holiday.setActive(true);
        return holidayRepository.save(holiday);
    }

    // HR â€“ Update holiday
    public Holiday updateHoliday(Long id, Holiday updated) {

        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holiday not found"));

        holiday.setName(updated.getName());
        holiday.setDescription(updated.getDescription());
        holiday.setHolidayDate(updated.getHolidayDate());
        holiday.setActive(updated.getActive());

        return holidayRepository.save(holiday);
    }

    // HR â€“ Get all holidays
    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    // System â€“ Check if date is holiday
    public boolean isHoliday(LocalDate date) {
        return holidayRepository.findByHolidayDate(date)
                .map(Holiday::getActive)
                .orElse(false);
    }
}

