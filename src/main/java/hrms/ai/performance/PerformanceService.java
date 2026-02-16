package hrms.ai.performance;

import hrms.ai.performance.dto.PerformanceRequestDto;
import hrms.ai.performance.dto.PerformanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;


    public PerformanceResponseDto assignGoal(PerformanceRequestDto dto) {

        Performance performance = Performance.builder()
                .employeeId(dto.getEmployeeId())
                .goal(dto.getGoal())
                .reviewPeriod(dto.getReviewPeriod())
                .reviewDate(LocalDate.now())
                .appraisalTriggered(false)
                .build();

        return mapToDto(performanceRepository.save(performance));
    }


    public PerformanceResponseDto reviewPerformance(Long id,
                                                    Integer rating,
                                                    String feedback) {

        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Performance record not found"));

        performance.setRating(rating);
        performance.setManagerFeedback(feedback);


        if (rating >= 4) {
            performance.setAppraisalTriggered(true);
        }

        return mapToDto(performanceRepository.save(performance));
    }


    public PerformanceResponseDto submitSelfAssessment(Long id,
                                                       String selfAssessment) {

        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Performance record not found"));

        performance.setSelfAssessment(selfAssessment);

        return mapToDto(performanceRepository.save(performance));
    }

    public List<PerformanceResponseDto> getEmployeePerformance(Long employeeId) {
        return performanceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PerformanceResponseDto mapToDto(Performance performance) {
        return PerformanceResponseDto.builder()
                .id(performance.getId())
                .employeeId(performance.getEmployeeId())
                .goal(performance.getGoal())
                .reviewPeriod(performance.getReviewPeriod())
                .rating(performance.getRating())
                .managerFeedback(performance.getManagerFeedback())
                .selfAssessment(performance.getSelfAssessment())
                .appraisalTriggered(performance.isAppraisalTriggered())
                .reviewDate(performance.getReviewDate())
                .build();
    }
}
