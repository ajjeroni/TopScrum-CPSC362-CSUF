import java.util.UUID;
import java.time.LocalDateTime;

public class Deck {
    private UUID id;
    private String title;
    private String description;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor
    public Deck(UUID id, String title, String description,
                boolean isPublic, LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isPublic() { return isPublic; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
        this.updatedAt = LocalDateTime.now();
    }

    // Example behavior
    public void publish() {
        this.isPublic = true;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
