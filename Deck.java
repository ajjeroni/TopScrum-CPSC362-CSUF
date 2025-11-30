import java.util.UUID;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    private UUID id;
    private String title;
    private String description;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Card> cards;   // <-- add this field

    // Constructor: 6 inputs
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

    // Constructor: 4 inputs (auto isPublic=false, updatedAt=createdAt)
    public Deck(UUID id, String title, String description, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublic = false;              // default
        this.createdAt = createdAt;
        this.updatedAt = createdAt;         // keep consistent
        this.cards = new ArrayList<>();
    }

    // Constructor: 3 inputs (title + description, auto id, timestamps now, isPublic=false)
    public Deck(String title, String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.isPublic = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.cards = new ArrayList<>();
    }

    // Constructor: no-arg (auto id, null fields, timestamps now, isPublic=false)
    public Deck() {
        this.id = UUID.randomUUID();
        this.title = null;
        this.description = null;
        this.isPublic = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.cards = new ArrayList<>();
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

    // Add a card to this deck
    public void addCard(Card card) {
        this.cards.add(card);
        this.updatedAt = LocalDateTime.now();
    }

    // Get all cards
    public List<Card> getCards() {
        return cards;
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
                ", cards=" + cards +
                '}';
    }
}
