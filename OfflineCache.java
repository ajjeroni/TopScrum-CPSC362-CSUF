import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class OfflineCache {
    // ---fields---
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ---associations---
    private List<Deck> decks;                // OfflineCache holds many Decks
    private List<CardProgress> progressRecords; // NEW: OfflineCache holds many CardProgress

    // ---constructors---
    public OfflineCache(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt,
                        List<Deck> decks, List<CardProgress> progressRecords) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.decks = decks != null ? decks : new ArrayList<>();
        this.progressRecords = progressRecords != null ? progressRecords : new ArrayList<>();
    }

    public OfflineCache() {
        this(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(),
             new ArrayList<>(), new ArrayList<>());
    }

    // ---getters---
    public UUID getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Deck> getDecks() { return decks; }
    public List<CardProgress> getProgressRecords() { return progressRecords; }

    // ---setters---
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ---Association Methods---
    public void addDeck(Deck deck) {
        decks.add(deck);
        deck.setOfflineCache(this); // maintain bidirectional link
        updatedAt = LocalDateTime.now();
    }

    public void addProgress(CardProgress progress) {
        progressRecords.add(progress);
        progress.setOfflineCache(this); // maintain bidirectional link
        updatedAt = LocalDateTime.now();
    }

    // ---behavior---
    public void refreshCache() {
        System.out.println("Refreshing offline cache...");
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "OfflineCache{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", decks=" + decks +
                ", progressRecords=" + progressRecords +
                '}';
    }
}
