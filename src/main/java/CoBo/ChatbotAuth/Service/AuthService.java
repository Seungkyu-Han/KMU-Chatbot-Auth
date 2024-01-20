package CoBo.ChatbotAuth.Service;

import CoBo.ChatbotAuth.Data.Dto.Auth.Req.AuthPostRegisterReq;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthGetLoginRes;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthPostReissueRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {

    ResponseEntity<AuthGetLoginRes> getLogin(String code) throws IOException;
    ResponseEntity<AuthGetLoginRes> getLoginAdmin(String code) throws IOException;

    ResponseEntity<AuthPostReissueRes> postReissue(String authorization);

    ResponseEntity<HttpStatus> postRegister(AuthPostRegisterReq authPostRegisterReq, String authorization);

}
