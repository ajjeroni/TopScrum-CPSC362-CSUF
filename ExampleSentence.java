import java.util.UUID;
import java.time.LocalDateTime;

public class ExampleSentence {
    // ---fields---
    private UUID id;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ---associations---
    private Card card;   // belongs to one Card

    // ---constructors---
    public ExampleSentence(UUID id, String text, LocalDateTime createdAt, LocalDateTime updatedAt, Card card) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.card = card;
    }

    // Convenience constructors (delegate to master)
    public ExampleSentence(String text) {
        this(UUID.randomUUID(), text, LocalDateTime.now(), LocalDateTime.now(), null);
    }

    public ExampleSentence() {
        this(UUID.randomUUID(), null, LocalDateTime.now(), LocalDateTime.now(), null);
    }

    // ---getters---
    public UUID getId() { return id; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Card getCard() { return card; }

    // ---setters---
    public void setText(String text) {
        this.text = text;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCard(Card card) { this.card = card; }  // ✅ now available

    // ---behavior---
    public void printSentence() {
        System.out.println(text);
    }

    @Override
    public String toString() {
        return "ExampleSentence{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", card=" + (card != null ? card.getId() : "null") +
                '}';
    }
}
