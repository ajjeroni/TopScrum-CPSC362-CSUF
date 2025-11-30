import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Folder {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Deck> decks;   // <-- add this field

    // Full constructor: 4 inputs
    public Folder(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.decks = new ArrayList<>();
    }

    // Constructor: 3 inputs (auto updatedAt = createdAt)
    public Folder(UUID id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.decks = new ArrayList<>();
    }

    // Constructor: 2 inputs (id + name, auto createdAt/updatedAt)
    public Folder(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.decks = new ArrayList<>();
    }

    // Constructor: 1 input (name only, auto id, timestamps now)
    public Folder(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.decks = new ArrayList<>();
    }

    // Add a deck to this folder
    public void addDeck(Deck deck) {
        this.decks.add(deck);
        this.updatedAt = LocalDateTime.now();
    }

    // Get all decks
    public List<Deck> getDecks() {
        return decks;
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    // Example behavior
    public void rename(String newName) {
        this.name = newName;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", decks=" + decks +
                '}';
    }
}
