package hrms.ai.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeId(Long employeeId);

    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    long countByDateAndStatus(LocalDate now, String present);

    @Query("SELECT a.status FROM Attendance a WHERE a.employeeId = :employeeId AND a.date = :date")
    Optional<String> findStatusByEmployeeAndDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);
}
