import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ImportJob {
    // ---fields---
    private UUID id;
    private String source;              // e.g., file path or URL
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ImportStatus status;

    // ---associations---
    private User user;                  // belongs to one User
    private List<Deck> decks;           // produces many Decks

    // ---constructors---
    public ImportJob(UUID id, String source, LocalDateTime createdAt,
                     LocalDateTime updatedAt, User user, List<Deck> decks) {
        this.id = id;
        this.source = source;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.decks = decks != null ? decks : new ArrayList<>();
    }
    public ImportJob(String source, User user) {
        this(UUID.randomUUID(), source, LocalDateTime.now(),
             LocalDateTime.now(), user, new ArrayList<>());
    }
    public ImportJob() {
        this(UUID.randomUUID(), null, LocalDateTime.now(),
             LocalDateTime.now(), null, new ArrayList<>());
    }

    // ---getters---
    public UUID getId() { return id; }
    public String getSource() { return source; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public User getUser() { return user; }
    public List<Deck> getDecks() { return decks; }

    // ---setters---
    public void setSource(String source) {
        this.source = source;
        this.updatedAt = LocalDateTime.now();
    }
    public void setUser(User user) {
        attachTo(user);
    }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ---Association Methods---
    public void addDeck(Deck deck) {
        if (deck != null && !decks.contains(deck)) {
            decks.add(deck);
            deck.setImportJob(this);
            updatedAt = LocalDateTime.now();
        }
    }
    public void removeDeck(Deck deck) {
        if (deck != null && decks.contains(deck)) {
            decks.remove(deck);
            deck.setImportJob(null); // break bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void clearDecks() {
        for (Deck deck : new ArrayList<>(decks)) {
            deck.setImportJob(null);
        }
        decks.clear();
        updatedAt = LocalDateTime.now();
    }
    public void attachTo(User user) {
        if (user != null && !user.getImportJobs().contains(this)) {
            user.addImportJob(this);
        }
        this.user = user;
    }
    public void detachFromUser() {
        if (user != null) {
            user.getImportJobs().remove(this);
            user = null;
        }
    }

    // ---behavior---
    public void runImport() {
        System.out.println("Running import from source: " + source);
        this.status = ImportStatus.COMPLETED;
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ImportJob{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + (user != null ? user.getId() : "null") +
                ", decks=" + decks +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImportJob)) return false;
        ImportJob other = (ImportJob) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
