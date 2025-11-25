import java.util.UUID;
import java.time.LocalDateTime;

public class Card {
    private UUID id;
    private String frontText;
    private String backText;
    private String hint;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor
    public Card(UUID id, String frontText, String backText, String hint,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.frontText = frontText;
        this.backText = backText;
        this.hint = hint;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public UUID getId() { return id; }
    public String getFrontText() { return frontText; }
    public String getBackText() { return backText; }
    public String getHint() { return hint; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setFrontText(String frontText) {
        this.frontText = frontText;
        this.updatedAt = LocalDateTime.now();
    }

    public void setBackText(String backText) {
        this.backText = backText;
        this.updatedAt = LocalDateTime.now();
    }

    public void setHint(String hint) {
        this.hint = hint;
        this.updatedAt = LocalDateTime.now();
    }

    // Example behavior
    public void flip() {
        System.out.println("Front: " + frontText);
        System.out.println("Back: " + backText);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", frontText='" + frontText + '\'' +
                ", backText='" + backText + '\'' +
                ", hint='" + hint + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
