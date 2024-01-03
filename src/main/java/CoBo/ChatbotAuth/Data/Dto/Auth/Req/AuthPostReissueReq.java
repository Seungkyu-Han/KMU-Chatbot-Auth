package CoBo.ChatbotAuth.Data.Dto.Auth.Req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthPostReissueReq {

    @Schema(description = "다시 반환되는 RefreshToken")
    private String refreshToken;
}
