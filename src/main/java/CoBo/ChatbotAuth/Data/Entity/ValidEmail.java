package CoBo.ChatbotAuth.Data.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
public class ValidEmail{

    @Id
    @Email
    private String email;

    @Column(length = 10)
    private String code;

    private Boolean isValid;
}
