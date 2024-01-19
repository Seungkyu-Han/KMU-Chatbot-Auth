package CoBo.ChatbotAuth.Repository;

import CoBo.ChatbotAuth.Data.Entity.ValidEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidEmailRepository extends JpaRepository<ValidEmail, String> {
}
