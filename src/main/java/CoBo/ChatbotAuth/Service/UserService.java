package CoBo.ChatbotAuth.Service;

import CoBo.ChatbotAuth.Data.Dto.User.Req.UserPutReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {

    ResponseEntity<HttpStatus> put(UserPutReq userPutReq, Authentication authentication);
}
