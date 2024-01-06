package CoBo.ChatbotAuth.Controller;

import CoBo.ChatbotAuth.Data.Dto.Auth.Req.AuthPostRegisterReq;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthGetLoginRes;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthPostReissueRes;
import CoBo.ChatbotAuth.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증 및 로그인 관련 API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    @Operation(summary = "로그인 API", description = "카카오 코드를 이용하여 로그인")
    @Parameters({
            @Parameter(name = "code", description = "카카오 로그인 code")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = AuthGetLoginRes.class)))
    })
    public ResponseEntity<AuthGetLoginRes> getLogin(@RequestParam String code) throws IOException{
        return authService.getLogin(code);
    }

    @PostMapping("/reissue")
    @Operation(summary = "AccessToken 재발급 API", description = "AccessToken 재발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = AuthPostReissueRes.class)))
    })
    public ResponseEntity<AuthPostReissueRes> postReissue( @Valid @NotNull @Parameter(hidden = true) @RequestHeader("Authorization") String authorization){
        return authService.postReissue(authorization);
    }

    @PostMapping("/register")
    @Operation(summary = "INACTIVE 상태에서 초기 정보를 넣는 API", description = "모든 데이터가 NOT NULL 이어야 하고 사용하고 나면 ACTIVE 상태로 변경\n이메일 인증이 완료된 상태에서만 사용 가능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content()),
            @ApiResponse(responseCode = "401", description = "이메일 인증이 완료되지 않았습니다.",
                    content = @Content()),
            @ApiResponse(responseCode = "403", description = "토큰이 없습니다.",
                    content = @Content())
    })
    public ResponseEntity<HttpStatus> postRegister(@Valid @RequestBody AuthPostRegisterReq authPostRegisterReq, @Valid @NotNull @Parameter(hidden = true) @RequestHeader("Authorization") String authorization){
        return authService.postRegister(authPostRegisterReq, authorization);
    }
}
