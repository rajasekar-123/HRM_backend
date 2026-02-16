package hrms.ai.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private String token;
    private String username;
    private String role;
    private String message;
}
