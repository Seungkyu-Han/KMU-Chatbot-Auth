package CoBo.ChatbotAuth.Data.Entity;

import CoBo.ChatbotAuth.Data.Enum.RegisterStateEnum;
import CoBo.ChatbotAuth.Data.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    private Integer kakaoId;

    private Integer studentId;

    @Column(length = 5)
    private String name;

    private String email;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Enumerated(EnumType.STRING)
    private RegisterStateEnum registerState;

    @Builder
    public User(Integer kakaoId, Integer studentId, String name, String email, String refreshToken, RoleEnum role, RegisterStateEnum registerState) {
        this.kakaoId = kakaoId;
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.refreshToken = refreshToken;
        this.role = role;
        this.registerState = registerState;
    }
}
