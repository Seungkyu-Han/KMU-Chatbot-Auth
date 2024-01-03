package CoBo.ChatbotAuth.Data.Dto.Auth.Res;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthGetLoginRes {

    String accessToken;

    String refreshToken;

    @Builder
    public AuthGetLoginRes(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
