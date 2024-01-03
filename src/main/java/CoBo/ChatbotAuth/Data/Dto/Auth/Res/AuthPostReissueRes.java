package CoBo.ChatbotAuth.Data.Dto.Auth.Res;

import CoBo.ChatbotAuth.Data.Enum.RegisterStateEnum;
import lombok.Builder;

@Builder
public class AuthPostReissueRes {


    String accessToken;

    String refreshToken;

    RegisterStateEnum registerStateEnum;

}
