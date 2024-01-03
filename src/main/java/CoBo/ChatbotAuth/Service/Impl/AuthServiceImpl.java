package CoBo.ChatbotAuth.Service.Impl;

import CoBo.ChatbotAuth.Config.Jwt.JwtTokenProvider;
import CoBo.ChatbotAuth.Data.Dto.Auth.Req.AuthPostReissueReq;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthGetLoginRes;
import CoBo.ChatbotAuth.Data.Dto.Auth.Res.AuthPostReissueRes;
import CoBo.ChatbotAuth.Data.Entity.User;
import CoBo.ChatbotAuth.Data.Enum.RegisterStateEnum;
import CoBo.ChatbotAuth.Repository.UserRepository;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${kakao.auth.client_id}")
    private String client_id;

    @Value("${kakao.auth.redirect_uri}")
    private String redirect_uri;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<AuthGetLoginRes> getLogin(String code) throws IOException {
        Integer kakaoId = getKakaoId(getKakaoAccessToken(code));

        Optional<User> optionalUser = userRepository.findById(kakaoId);

        if(optionalUser.isEmpty())
            return register(kakaoId);

        User user = optionalUser.get();

        String accessToken = jwtTokenProvider.createAccessToken(user.getKakaoId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getKakaoId());

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
    public ResponseEntity<AuthPostReissueRes> postReissue(AuthPostReissueReq authPostReissueReq) {
        User user = userRepository.findByRefreshToken(authPostReissueReq.getRefreshToken());

        String accessToken = jwtTokenProvider.createAccessToken(user.getKakaoId());

        AuthPostReissueRes authPostReissueRes = AuthPostReissueRes.builder()
                .accessToken(accessToken)
                .refreshToken(authPostReissueReq.getRefreshToken())
                .registerStateEnum(user.getRegisterState())
                .build();

        return new ResponseEntity<>(authPostReissueRes, HttpStatus.OK);
    }

    private ResponseEntity<AuthGetLoginRes> register(Integer kakaoId){

        String accessToken = jwtTokenProvider.createAccessToken(kakaoId);
        String refreshToken = jwtTokenProvider.createRefreshToken(kakaoId);

        User user = User.builder()
                .kakaoId(kakaoId)
                .registerState(RegisterStateEnum.INACTIVE)
                .refreshToken(refreshToken)
                .build();

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

    private String getKakaoAccessToken(String code) throws IOException{
        String accessToken;
        String reqURL = "https://kauth.kakao.com/oauth/token";

        URL url = new URL(reqURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
        String stringBuilder = "grant_type=authorization_code" +
                "&client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
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
