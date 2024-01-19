package CoBo.ChatbotAuth.Repository;

import CoBo.ChatbotAuth.Data.Entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
}
