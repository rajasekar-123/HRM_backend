package hrms.ai.holiday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    Optional<Holiday> findByHolidayDate(LocalDate holidayDate);

    boolean existsByHolidayDate(LocalDate holidayDate);

    @Query("SELECT h FROM Holiday h WHERE h.holidayDate >= :date ORDER BY h.holidayDate")
    List<Holiday> findUpcomingHolidays(@Param("date") LocalDate date);
}
