package CoBo.ChatbotAuth.Repository;

import CoBo.ChatbotAuth.Data.Entity.ChatbotChat;
import CoBo.ChatbotAuth.Data.Entity.Embedded.ChatId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotChatRepository extends JpaRepository<ChatbotChat, ChatId> {
}
