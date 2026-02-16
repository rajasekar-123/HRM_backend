package hrms.ai.performance;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "performance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private String goal;              // KPI / target
    private String reviewPeriod;      // Q1-2026 / 2026-H1

    private Integer rating;           // 1â€“5

    @Column(length = 1000)
    private String managerFeedback;

    @Column(length = 1000)
    private String selfAssessment;

    private boolean appraisalTriggered;

    private LocalDate reviewDate;
}
