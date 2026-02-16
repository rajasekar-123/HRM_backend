package hrms.ai.leave.leavebalance;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_balance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private Integer casualLeave;   // CL

    @Column(nullable = false)
    private Integer sickLeave;     // SL

    @Column(nullable = false)
    private Integer privilegeLeave; // PL

}
