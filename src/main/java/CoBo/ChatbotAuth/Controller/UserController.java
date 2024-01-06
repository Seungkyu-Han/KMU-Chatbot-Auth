package CoBo.ChatbotAuth.Controller;

import CoBo.ChatbotAuth.Data.Dto.User.Req.UserPutReq;
import CoBo.ChatbotAuth.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "사용자 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("")
    @Operation(summary = "사용자 정보 수정 API", description = "모든 데이터가 NOT NULL 이어야 하고 이메일 인증이 완료된 상태에서만 사용 가능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content()),
            @ApiResponse(responseCode = "403", description = "토큰이 없습니다.",
                    content = @Content())
    })
    public ResponseEntity<HttpStatus> put(@Valid @RequestBody UserPutReq userPutReq, @Parameter(hidden = true) Authentication authentication) {
        return userService.put(userPutReq, authentication);
    }
}
