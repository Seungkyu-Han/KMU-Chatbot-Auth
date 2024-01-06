package CoBo.ChatbotAuth.Data.Dto.Auth.Res;

import CoBo.ChatbotAuth.Data.Enum.RegisterStateEnum;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthPostReissueRes {


    String accessToken;

    String refreshToken;

    RegisterStateEnum registerStateEnum;

}
