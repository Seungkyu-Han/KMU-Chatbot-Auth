package CoBo.ChatbotAuth.Data.Entity;

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

    private String accessToken;

    @Enumerated(EnumType.ORDINAL)
    private RoleEnum role;

    @Builder
    public User(Integer kakaoId, Integer studentId, String name, String email, String accessToken, RoleEnum role) {
        this.kakaoId = kakaoId;
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
        this.role = role;
    }
}
