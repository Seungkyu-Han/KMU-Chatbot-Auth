package CoBo.ChatbotAuth.Data.Dto.Auth.Res;

import CoBo.ChatbotAuth.Data.Enum.RegisterStateEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthGetLoginRes {

    String accessToken;

    String refreshToken;

    RegisterStateEnum registerStateEnum;

    @Builder
    public AuthGetLoginRes(String accessToken, String refreshToken, RegisterStateEnum registerStateEnum) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.registerStateEnum = registerStateEnum;
    }
}
