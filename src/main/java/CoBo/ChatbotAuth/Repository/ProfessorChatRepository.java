package CoBo.ChatbotAuth.Repository;

import CoBo.ChatbotAuth.Data.Entity.Embedded.ChatId;
import CoBo.ChatbotAuth.Data.Entity.ProfessorChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorChatRepository extends JpaRepository<ProfessorChat, ChatId> {
}
