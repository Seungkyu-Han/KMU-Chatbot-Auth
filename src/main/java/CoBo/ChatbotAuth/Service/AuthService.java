package CoBo.ChatbotAuth.Service;

import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthGetLoginRes;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {

    ResponseEntity<AuthGetLoginRes> getLogin(String code) throws IOException;
}
