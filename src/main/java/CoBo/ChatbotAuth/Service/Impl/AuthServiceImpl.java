package CoBo.ChatbotAuth.Service.Impl;

import CoBo.ChatbotAuth.Config.Jwt.JwtTokenProvider;
import CoBo.ChatbotAuth.Data.Dto.Auth.Req.AuthPostRegisterReq;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthGetLoginRes;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthPostReissueRes;
import CoBo.ChatbotAuth.Data.Entity.User;
import CoBo.ChatbotAuth.Data.Entity.ValidEmail;
import CoBo.ChatbotAuth.Data.Enum.RegisterStateEnum;
import CoBo.ChatbotAuth.Data.Enum.RoleEnum;
import CoBo.ChatbotAuth.Repository.UserRepository;
import CoBo.ChatbotAuth.Repository.ValidEmailRepository;
import CoBo.ChatbotAuth.Service.AuthService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${kakao.auth.client_id}")
    private String client_id;

    @Value("${kakao.auth.redirect_uri}")
    private String redirect_uri;
    @Value("${kakao.auth.admin_redirect_uri}")
    private String admin_redirect_uri;

    private final UserRepository userRepository;
    private final ValidEmailRepository validEmailRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<AuthGetLoginRes> getLogin(String code) throws IOException {
        Integer kakaoId = getKakaoId(getKakaoAccessToken(code, redirect_uri));

        Optional<User> optionalUser = userRepository.findById(kakaoId);

        if(optionalUser.isEmpty())
            return register(kakaoId);

        User user = optionalUser.get();

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        AuthGetLoginRes authGetLoginRes = AuthGetLoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .registerStateEnum(user.getRegisterState())
                .build();

        return new ResponseEntity<>(authGetLoginRes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthGetLoginRes> getLoginAdmin(String code) throws IOException {
        Integer kakaoId = getKakaoId(getKakaoAccessToken(code, admin_redirect_uri));

        Optional<User> optionalUser = userRepository.findById(kakaoId);

        if(optionalUser.isEmpty())
            return register(kakaoId);

        if(!(optionalUser.get().getRole().equals(RoleEnum.DEVELOPER) || optionalUser.get().getRole().equals(RoleEnum.PROFESSOR)))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        User user = optionalUser.get();

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        AuthGetLoginRes authGetLoginRes = AuthGetLoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .registerStateEnum(user.getRegisterState())
                .build();

        return new ResponseEntity<>(authGetLoginRes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthPostReissueRes> postReissue(String authorization) {
        String token = authorization.split(" ")[1];
        User user = userRepository.findByRefreshToken(token);

        String accessToken = jwtTokenProvider.createAccessToken(user);

        AuthPostReissueRes authPostReissueRes = AuthPostReissueRes.builder()
                .accessToken(accessToken)
                .refreshToken(token)
                .registerStateEnum(user.getRegisterState())
                .build();

        return new ResponseEntity<>(authPostReissueRes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> postRegister(AuthPostRegisterReq authPostRegisterReq, String authorization) {

        Optional<ValidEmail> validEmail = validEmailRepository.findById(authPostRegisterReq.getEmail());

        if(validEmail.isEmpty() || !validEmail.get().getIsValid())
            throw new NoSuchElementException();

        Integer userId = jwtTokenProvider.getUserId(authorization.split(" ")[1]);

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty())
            throw new NullPointerException();

        optionalUser.get().setEmail(authPostRegisterReq.getEmail());
        optionalUser.get().setStudentId(authPostRegisterReq.getStudentId());
        optionalUser.get().setRegisterState(RegisterStateEnum.ACTIVE);

        userRepository.save(optionalUser.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<AuthGetLoginRes> register(Integer kakaoId){

        User user = User.builder()
                .kakaoId(kakaoId)
                .registerState(RegisterStateEnum.INACTIVE)
                .role(RoleEnum.STUDENT)
                .build();

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        AuthGetLoginRes authGetLoginRes = AuthGetLoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .registerStateEnum(RegisterStateEnum.INACTIVE)
                .build();


        return new ResponseEntity<>(authGetLoginRes, HttpStatus.CREATED);
    }

    private Integer getKakaoId(String kakaoAccessToken) throws IOException {
        JsonElement element = getJsonElementByAccessToken(kakaoAccessToken);

        return element.getAsJsonObject().get("id").getAsInt();
    }

    private JsonElement getJsonElementByAccessToken(String token) throws IOException {
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);

        return getJsonElement(httpURLConnection);
    }

    private String getKakaoAccessToken(String code, String uri) throws IOException{
        String accessToken;
        String reqURL = "https://kauth.kakao.com/oauth/token";

        URL url = new URL(reqURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
        String stringBuilder = "grant_type=authorization_code" +
                "&client_id=" + client_id +
                "&redirect_uri=" + uri +
                "&code=" + code;

        bufferedWriter.write(stringBuilder);
        bufferedWriter.flush();

        httpURLConnection.getResponseCode();

        JsonElement element = getJsonElement(httpURLConnection);

        accessToken = element.getAsJsonObject().get("access_token").getAsString();

        bufferedWriter.close();

        return accessToken;
    }

    private JsonElement getJsonElement(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();

        while((line = bufferedReader.readLine()) != null){
            result.append(line);
        }

        bufferedReader.close();

        return JsonParser.parseString(result.toString());
    }
}
