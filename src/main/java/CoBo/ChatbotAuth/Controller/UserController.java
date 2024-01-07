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
    @Operation(summary = "사용자 정보 수정 API", description = "데이터에 NULL 있으면 에러 발생")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content()),
            @ApiResponse(responseCode = "401", description = "존재하지 않는 사용자입니다.",
                    content = @Content()),
            @ApiResponse(responseCode = "403", description = "유효하지 않은 토큰입니다.",
                    content = @Content()),
            @ApiResponse(responseCode = "412", description = "유효하지 않은 데이터입니다.",
                    content = @Content())
    })
    public ResponseEntity<HttpStatus> put(@Valid @RequestBody UserPutReq userPutReq, @Parameter(hidden = true) Authentication authentication) {
        return userService.put(userPutReq, authentication);
    }
}
