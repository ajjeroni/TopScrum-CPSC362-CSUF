import java.util.UUID;
import java.time.LocalDateTime;

public class DeckShare {
    // ---fields---
    private UUID id;
    private ShareRole role;
    private String linkToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ---associations---
    private Deck deck;   // belongs to one Deck
    private User user;   // belongs to one User

    // ---constructors---
    public DeckShare(UUID id, ShareRole role, String linkToken, LocalDateTime createdAt, Deck deck, User user) {
        this.id = id;
        this.role = role;
        this.linkToken = linkToken;
        this.createdAt = createdAt;
        this.deck = deck;
        this.user = user;
    }

    // Convenience constructors
    public DeckShare(ShareRole role, String linkToken, Deck deck, User user) {
        this(UUID.randomUUID(), role, linkToken, LocalDateTime.now(), deck, user);
    }

    public DeckShare(ShareRole role, Deck deck, User user) {
        this(UUID.randomUUID(), role, null, LocalDateTime.now(), deck, user);
    }

    public DeckShare() {
        this(UUID.randomUUID(), null, null, LocalDateTime.now(), null, null);
    }

    // ---getters---
    public UUID getId() { return id; }
    public ShareRole getRole() { return role; }
    public String getLinkToken() { return linkToken; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Deck getDeck() { return deck; }
    public User getUser() { return user; }

    // ---setters---
    public void setRole(ShareRole role) {
        this.role = role;
        this.updatedAt = LocalDateTime.now();
    }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setLinkToken(String linkToken) { this.linkToken = linkToken; }
    public void setDeck(Deck deck) { this.deck = deck; }
    public void setUser(User user) { this.user = user; }

    // ---helpers---
    public void attachTo(Deck deck, User user) {
        if (deck != null && !deck.getShares().contains(this)) {
            deck.addShare(this);
        }
        if (user != null && !user.getShares().contains(this)) {
            user.addShare(this);
        }
        this.deck = deck;
        this.user = user;
    }

    public void detach() {
        if (deck != null) {
            deck.getShares().remove(this);
            deck = null;
        }
        if (user != null) {
            user.getShares().remove(this);
            user = null;
        }
        this.updatedAt = LocalDateTime.now(); // 🔹 optional timestamp update
    }

    // ---behavior---
    public boolean canEdit() {
        return role == ShareRole.EDITOR || role == ShareRole.OWNER;
    }
    public boolean isOwner() {
        return role == ShareRole.OWNER;
    }

    @Override
    public String toString() {
        return "DeckShare{" +
                "id=" + id +
                ", role=" + role +
                ", linkToken='" + linkToken + '\'' +
                ", createdAt=" + createdAt +
                ", deck=" + (deck != null ? deck.getId() : "null") +
                ", user=" + (user != null ? user.getId() : "null") +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeckShare)) return false;
        DeckShare other = (DeckShare) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
