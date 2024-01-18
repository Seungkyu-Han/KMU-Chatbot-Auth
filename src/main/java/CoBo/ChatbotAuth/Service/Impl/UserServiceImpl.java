package CoBo.ChatbotAuth.Service.Impl;

import CoBo.ChatbotAuth.Data.Dto.User.Req.UserPutReq;
import CoBo.ChatbotAuth.Data.Entity.User;
import CoBo.ChatbotAuth.Repository.UserRepository;
import CoBo.ChatbotAuth.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<HttpStatus> put(UserPutReq userPutReq, Authentication authentication) {

        Integer kakaoId = Integer.valueOf(authentication.getName());

        Optional<User> optionalUser = userRepository.findById(kakaoId);

        if(optionalUser.isEmpty())
            throw new IllegalStateException();

        optionalUser.get().setName(userPutReq.getName());
        // optionalUser.get().setEmail(userPutReq.getEmail());
        optionalUser.get().setStudentId(userPutReq.getStudentId());

        userRepository.save(optionalUser.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
