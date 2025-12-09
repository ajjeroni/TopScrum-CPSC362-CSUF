import java.util.UUID;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Card {
    // ---fields---
    private UUID id;
    private UUID deckId; // ID of the owning Deck
    private String frontText;
    private String backText;
    private String hint;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Deck deck;   // belongs to one Deck

    // ---associations---
    private List<ReviewAttempt> reviewAttempts = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();
    private List<ExampleSentence> exampleSentences = new ArrayList<>();
    private List<CardProgress> progressRecords = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    // ---constructors---
    public Card(UUID id, UUID deckId, String frontText, String backText, String hint,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.deckId = deckId;
        this.frontText = frontText;
        this.backText = backText;
        this.hint = hint != null ? hint : "";
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Card(UUID deckId, String frontText, String backText) {
        this(UUID.randomUUID(), deckId, frontText, backText, "", LocalDateTime.now(), LocalDateTime.now());
    }

    public Card(UUID deckId, String frontText, String backText, String hint) {
        this(UUID.randomUUID(), deckId, frontText, backText, hint, LocalDateTime.now(), LocalDateTime.now());
    }

    public Card(UUID id, String frontText, String backText, LocalDateTime createdAt) {
        this(id, null, frontText, backText, "", createdAt, createdAt);
    }

    public Card(UUID id, String frontText, String backText, String hint, LocalDateTime createdAt) {
        this(id, null, frontText, backText, hint, createdAt, createdAt);
    }

    public Card(String frontText, String backText) {
        this(UUID.randomUUID(), null, frontText, backText, "", LocalDateTime.now(), LocalDateTime.now());
    }

    public Card(String frontText, String backText, String hint) {
        this(UUID.randomUUID(), null, frontText, backText, hint, LocalDateTime.now(), LocalDateTime.now());
    }

    public Card() {
        this(UUID.randomUUID(), null, "", "", "", LocalDateTime.now(), LocalDateTime.now());
    }

    // ---getters---
    public UUID getId() { return id; }
    public UUID getDeckId() { return deckId; }
    public String getFrontText() { return frontText; }
    public String getBackText() { return backText; }
    public String getHint() { return hint; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Deck getDeck() { return deck; }

    // ---setters---
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
    public void setDeck(Deck deck) { this.deck = deck; }
    public void setDeckId(UUID deckId) { this.deckId = deckId; }

    // ---persistence helpers---
    @Override
    public String toString() {
        return id + "|" + deckId + "|" + frontText + "|" + backText + "|" + hint + "|" + createdAt + "|" + updatedAt;
    }

    public static Card fromString(String line) {
        String[] parts = line.split("\\|");
        UUID id = UUID.fromString(parts[0]);
        UUID deckId = UUID.fromString(parts[1]);
        String front = parts[2];
        String back = parts[3];
        String hint = parts[4];
        LocalDateTime createdAt = LocalDateTime.parse(parts[5]);
        LocalDateTime updatedAt = LocalDateTime.parse(parts[6]);
        return new Card(id, deckId, front, back, hint, createdAt, updatedAt);
    }
}
