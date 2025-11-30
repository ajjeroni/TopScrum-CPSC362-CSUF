import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Card {
    private UUID id;
    private String frontText;
    private String backText;
    private String hint;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReviewAttempt> attempts = new ArrayList<>();

    public void addAttempt(ReviewAttempt attempt) {
        attempts.add(attempt);
    }

    public List<ReviewAttempt> getAttempts() {
        return attempts;
    }

    // Constructor: 6 inputs (full control)
    public Card(UUID id, String frontText, String backText, String hint,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.frontText = frontText;
        this.backText = backText;
        this.hint = hint;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor: 4 inputs (id + front/back + createdAt, auto hint="", updatedAt=createdAt)
    public Card(UUID id, String frontText, String backText, LocalDateTime createdAt) {
        this.id = id;
        this.frontText = frontText;
        this.backText = backText;
        this.hint = "";                  // default empty
        this.createdAt = createdAt;
        this.updatedAt = createdAt;      // keep consistent
    }

    // Constructor: 5 inputs (auto updatedAt = createdAt)
    public Card(UUID id, String frontText, String backText, String hint,
                LocalDateTime createdAt) {
        this.id = id;
        this.frontText = frontText;
        this.backText = backText;
        this.hint = hint;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;   // keep consistent
    }

    // Constructor: 3 inputs (front/back only, auto id, hint empty, timestamps now)
    public Card(String frontText, String backText) {
        this.id = UUID.randomUUID();
        this.frontText = frontText;
        this.backText = backText;
        this.hint = "";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // Constructor: 4 inputs (front/back + hint, auto id, timestamps now)
    public Card(String frontText, String backText, String hint) {
        this.id = UUID.randomUUID();
        this.frontText = frontText;
        this.backText = backText;
        this.hint = hint;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
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

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
