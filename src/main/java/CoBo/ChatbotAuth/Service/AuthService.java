package CoBo.ChatbotAuth.Service;

import CoBo.ChatbotAuth.Data.Dto.Auth.Req.AuthPostReissueReq;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthGetLoginRes;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthPostReissueRes;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {

    ResponseEntity<AuthGetLoginRes> getLogin(String code) throws IOException;

    ResponseEntity<AuthPostReissueRes> postReissue(AuthPostReissueReq authPostReissueReq);
}
