package CoBo.ChatbotAuth.Data.Entity;

import CoBo.ChatbotAuth.Data.Entity.Embedded.ChatId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ChatbotChat {

    @EmbeddedId
    private ChatId id;

    @Column(length = 500)
    private String question;

    @Column(length = 500)
    private String answer;

    private LocalDateTime createdAt;

    @Builder
    public ChatbotChat(ChatId id, String question, String answer, LocalDateTime createdAt) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.createdAt = createdAt;
    }

}
