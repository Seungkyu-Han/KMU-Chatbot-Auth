package CoBo.ChatbotAuth.Data.Entity.Embedded;

import CoBo.ChatbotAuth.Data.Entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class ChatId implements Serializable {

    @ManyToOne
    private User user;

    private Long id;

    @Builder
    public ChatId(User user, Long id) {
        this.user = user;
        this.id = id;
    }
}
