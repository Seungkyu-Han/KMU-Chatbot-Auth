package CoBo.ChatbotAuth.Config.Jwt;

import CoBo.ChatbotAuth.Data.Entity.User;
import CoBo.ChatbotAuth.Data.Enum.RegisterStateEnum;
import CoBo.ChatbotAuth.Data.Enum.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenProvider {

    private static final Long accessTokenValidTime = Duration.ofHours(2).toMillis();
    private static final Long refreshTokenValidTime = Duration.ofDays(7).toMillis();

    @Value("${jwt.secret-key}")
    private String secretKey;

    public Integer getUserId(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Integer.class);
    }

    public String getUserRole(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userRole", String.class);
    }

    public Boolean isActiveState(String token){
        return Objects.equals(Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userState", String.class), RegisterStateEnum.ACTIVE.toString());
    }

    public boolean isAccessToken(String token) throws MalformedJwtException{
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getHeader().get("type").toString().equals("access");
    }

    public String createAccessToken(User user){
        return createJwtToken(user.getKakaoId(), user.getRole(), user.getRegisterState(),"access", accessTokenValidTime);
    }

    public String createRefreshToken(User user){
        return createJwtToken(user.getKakaoId(), user.getRole(), user.getRegisterState(), "refresh", refreshTokenValidTime);
    }

    private String createJwtToken(Integer userId, RoleEnum role, RegisterStateEnum registerState, String type, Long tokenValidTime){
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("userRole", role);
        claims.put("userState", registerState);

        return Jwts.builder()
                .setHeaderParam("type", type)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
