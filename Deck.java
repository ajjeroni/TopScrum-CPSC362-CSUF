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

    // Associations
    private List<Card> cards;
    private ImportJob importJob;   // belongs to one ImportJob
    private OfflineCache offlineCache;   // belongs to one OfflineCache
    private List<Tag> tags = new ArrayList<>();
    private List<DeckShare> shares = new ArrayList<>();
    private Folder folder; // back-reference to Folder
    private User user;

    // ---constructors---
    public Deck(UUID id, String title, String description,
                boolean isPublic, LocalDateTime createdAt,
                LocalDateTime updatedAt, List<Card> cards) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.cards = cards != null ? cards : new ArrayList<>();
    }

    public Deck(UUID id, String title, String description, LocalDateTime createdAt) {
        this(id, title, description, false, createdAt, createdAt, new ArrayList<>());
    }

    public Deck(String title, String description) {
        this(UUID.randomUUID(), title, description, false,
             LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
    }

    public Deck() {
        this(UUID.randomUUID(), null, null, false,
             LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
    }

    // ---getters---
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isPublic() { return isPublic; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Card> getCards() { return cards; }
    public ImportJob getImportJob() { return importJob; }
    public OfflineCache getOfflineCache() { return offlineCache; }
    public List<Tag> getTags() { return tags; }
    public List<DeckShare> getShares() { return shares; }
    public Folder getFolder() { return folder; }
    public User getUser() { return user; }
    // Convenience: collect all attempts across cards
    public List<ReviewAttempt> getAllReviewAttempts() {
        List<ReviewAttempt> allAttempts = new ArrayList<>();
        for (Card card : cards) {
            allAttempts.addAll(card.getReviewAttempts());
        }
        return allAttempts;
    }

    // ---setters---
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

    public void setImportJob(ImportJob importJob) {
        this.importJob = importJob;
    }

    public void setOfflineCache(OfflineCache offlineCache) {
        this.offlineCache = offlineCache;
        if (offlineCache != null) {
            offlineCache.setDeck(this);
        }
    }
    public void setFolder(Folder folder) {
        this.folder = folder;
    }
    public void setUser(User user) { this.user = user; }

    // ---Associations Methods---
    public void addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            tag.addDeck(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void addShare(DeckShare share) {
        if (!shares.contains(share)) {
            shares.add(share);
            share.setDeck(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void removeShare(DeckShare share) {
        if (shares.contains(share)) {
            shares.remove(share);
            share.setDeck(null); // break back-reference
            if (share.getUser() != null) {
                share.getUser().getShares().remove(share);
                share.setUser(null);
            }
            updatedAt = LocalDateTime.now();
        }
    }
    public void addCard(Card card) {
        if (!cards.contains(card)) {
            cards.add(card);
            card.setDeck(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void removeCard(Card card) {
        if (cards.contains(card)) {
            cards.remove(card);
            card.setDeck(null);

            // Cascade: clear review attempts
            card.clearReviewAttempts();

            // Cascade: clear progress records
            for (CardProgress progress : new ArrayList<>(card.getProgressRecords())) {
                card.removeProgress(progress);
                if (progress.getUser() != null) {
                    progress.getUser().removeProgress(progress);
                }
            }

            updatedAt = LocalDateTime.now();
        }
    }
    // ---behavior---
    public void publish() {
        this.isPublic = true;
        this.updatedAt = LocalDateTime.now();
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
