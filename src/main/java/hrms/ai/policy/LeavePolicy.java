package hrms.ai.policy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeavePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyType; // CL, SL, PL

    @Column(length = 5000)
    private String policyText;

    @Builder.Default
    private boolean active = true;
}
