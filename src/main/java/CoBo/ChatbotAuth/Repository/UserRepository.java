package CoBo.ChatbotAuth.Repository;

import CoBo.ChatbotAuth.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByRefreshToken(String refreshToken);
}
