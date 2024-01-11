package CoBo.ChatbotAuth.Controller.ExceptionHandler;

import CoBo.ChatbotAuth.Controller.AuthController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = AuthController.class)
public class AuthExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> AuthNullPointerExceptionHandler(){
        return new ResponseEntity<>("존재하지 않는 사용자입니다.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> AuthIOExceptionHandler(){
        return new ResponseEntity<>("카카오 서버 인증 중에 문제가 발생했습니다.", HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> AuthNoSuchElementExceptionHandler(){
        return new ResponseEntity<>("인증이 실패한 이메일입니다.", HttpStatus.FORBIDDEN);
    }
}
