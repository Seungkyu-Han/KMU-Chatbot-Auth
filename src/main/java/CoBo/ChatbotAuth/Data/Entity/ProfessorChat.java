package CoBo.ChatbotAuth.Data.Entity;

import CoBo.ChatbotAuth.Data.Entity.Embedded.ChatId;
import CoBo.ChatbotAuth.Data.Enum.StateEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ProfessorChat {

    @EmbeddedId
    private ChatId id;

    @Column(length = 500)
    private String question;

    @Column(length = 500)
    private String answer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.ORDINAL)
    private StateEnum state;

    @Builder
    public ProfessorChat(ChatId id, String question, String answer, LocalDateTime createdAt, LocalDateTime updatedAt, StateEnum state) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.state = state;
    }
}
