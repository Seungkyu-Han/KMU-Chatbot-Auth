package CoBo.ChatbotAuth.Data.Dto.Auth.Req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthPostRegisterReq {

    @NotNull
    @Schema(description = "학번", example = "2021111222")
    private Integer studentId;

    @NotNull
    @Schema(description = "이메일", example = "trust1204@stu.kmu.ac.kr")
    private String email;
}
