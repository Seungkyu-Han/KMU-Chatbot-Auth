package CoBo.ChatbotAuth.Controller.ExceptionHandler;

import CoBo.ChatbotAuth.Controller.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> UserMethodArgumentNotValidExceptionHandler(){
        return new ResponseEntity<>("유효하지 않은 데이터입니다.", HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> UserNoSuchElementExceptionHandler(){
        return new ResponseEntity<>("존재하지 않는 사용자입니다.", HttpStatus.FORBIDDEN);
    }
}
