package CoBo.ChatbotAuth.Data.Dto.Auth.Req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthSmtpReq {
    public String email;
}
