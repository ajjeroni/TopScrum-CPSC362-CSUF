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
    private List<CardProgress> progressRecords; // OfflineCache holds many CardProgress
    private Deck deck;
    private Device device;

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
    public Deck getDeck() { return deck; }
    public Device getDevice() { return device; }

    // ---setters---
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setDeck(Deck deck) { this.deck = deck; }
    public void setDevice(Device device) {
        this.device = device;
        if (device != null && !device.getOfflineCaches().contains(this)) {
            device.addOfflineCache(this); // maintain bidirectional link
        }
    }

    // ---Association Methods---
    public void addDeck(Deck deck) {
        if (deck != null && !decks.contains(deck)) {
            decks.add(deck);
            deck.setOfflineCache(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }

    public void removeDeck(Deck deck) {
        if (deck != null && decks.contains(deck)) {
            decks.remove(deck);
            deck.setOfflineCache(null); // break back-reference
            updatedAt = LocalDateTime.now();
        }
    }

    public void addProgress(CardProgress progress) {
        if (progress != null && !progressRecords.contains(progress)) {
            progressRecords.add(progress);
            progress.setOfflineCache(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }

    public void clearProgressRecords() {
        for (CardProgress progress : new ArrayList<>(progressRecords)) {
            progress.setOfflineCache(null); // break back-reference
        }
        progressRecords.clear();
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
