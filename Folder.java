import java.util.UUID;
import java.time.LocalDateTime;

public class Folder {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;

    // Constructor
    public Folder(UUID id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setName(String name) { this.name = name; }

    // Example behavior
    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
