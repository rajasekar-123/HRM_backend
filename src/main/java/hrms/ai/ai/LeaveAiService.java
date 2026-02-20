package hrms.ai.ai;

import hrms.ai.holiday.HolidayService;
import hrms.ai.leave.leavebalance.LeaveBalanceService;
import hrms.ai.leave.dto.LeaveAiDecisionDto;
import hrms.ai.leave.dto.LeaveApplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LeaveAiService {

        private final HolidayService holidayService;
        private final LeaveBalanceService leaveBalanceService;
        private final RagRetrieverService ragRetrieverService;
        private final PromptBuilder promptBuilder;
        private final AiResponseParser aiResponseParser;
        private final ChatClient chatClient;

        @Value("${spring.ai.ollama.chat.model:phi}")
        private String model;

        public LeaveAiDecisionDto evaluateLeave(LeaveApplyDto dto) {

                // 1ï¸âƒ£ Holiday validation
                LocalDate date = dto.getFromDate();
                while (!date.isAfter(dto.getToDate())) {
                        if (holidayService.isHoliday(date)) {
                                return LeaveAiDecisionDto.builder()
                                                .eligible(false)
                                                .reason("Selected date " + date + " is a company holiday")
                                                .suggestion("Choose a working day or contact HR")
                                                .build();
                        }
                        date = date.plusDays(1);
                }

                // 2ï¸âƒ£ Calculate days
                int days = (int) ChronoUnit.DAYS.between(
                                dto.getFromDate(),
                                dto.getToDate()) + 1;

                // 3ï¸âƒ£ Leave balance validation
                boolean hasBalance = leaveBalanceService.hasSufficientBalance(
                                dto.getEmployeeId(),
                                dto.getLeaveType(),
                                days);

                if (!hasBalance) {
                        return LeaveAiDecisionDto.builder()
                                        .eligible(false)
                                        .reason("Insufficient leave balance")
                                        .suggestion("Check leave balance or contact HR")
                                        .build();
                }

                // 4ï¸âƒ£ RAG policy retrieval
                String policyContext = ragRetrieverService.retrieveLeavePolicy(dto.getLeaveType());

                // 5ï¸âƒ£ Prompt building
                String prompt = promptBuilder.buildLeavePrompt(dto, policyContext);

                // 6ï¸âƒ£ LLM call (Ollama)
                ChatResponse response = chatClient.call(
                                new Prompt(prompt, OllamaOptions.create().withModel(model)));
                String aiResponse = response.getResult().getOutput().getContent();

                // 7ï¸âƒ£ Parse structured response
                return aiResponseParser.parse(aiResponse);
        }
}
