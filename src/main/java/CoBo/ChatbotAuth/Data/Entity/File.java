package CoBo.ChatbotAuth.Data.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class File {

    @Id
    private Integer id;

    private String name;

    private String path;

    private Integer size;

    private LocalDateTime createdAt;

    @Builder
    public File(Integer id, String name, String path, Integer size, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.size = size;
        this.createdAt = createdAt;
    }
}
