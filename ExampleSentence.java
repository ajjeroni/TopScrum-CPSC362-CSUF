import java.util.UUID;
import java.time.LocalDateTime;

public class ExampleSentence {
    // ---fields---
    private UUID id;
    private String sentence;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ---associations---
    private Card card;   // belongs to one Card

    // ---constructors---
    public ExampleSentence(UUID id, String text, LocalDateTime createdAt, LocalDateTime updatedAt, Card card) {
        this.id = id;
        this.sentence = text;
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
    public String getSentence() { return sentence; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Card getCard() { return card; }

    // ---setters---
    public void setSentence(String sentence) {
        this.sentence = sentence;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCard(Card card) { this.card = card; }

    // ---behavior---
    public void printSentence() {
        System.out.println(sentence);
    }

    @Override
    public String toString() {
        return "ExampleSentence{" +
                "id=" + id +
                ", sentence='" + sentence + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", card=" + (card != null ? card.getId() : "null") +
                '}';
    }
}
