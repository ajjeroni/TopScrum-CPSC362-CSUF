import java.util.UUID;
import java.time.LocalDateTime;

public class DeckShare {
    private UUID id;
    private ShareRole role;
    private String linkToken;
    private LocalDateTime createdAt;

    // Constructor
    public DeckShare(UUID id, ShareRole role, String linkToken, LocalDateTime createdAt) {
        this.id = id;
        this.role = role;
        this.linkToken = linkToken;
        this.createdAt = createdAt;
    }

    // Getters
    public UUID getId() { return id; }
    public ShareRole getRole() { return role; }
    public String getLinkToken() { return linkToken; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setRole(ShareRole role) { this.role = role; }
    public void setLinkToken(String linkToken) { this.linkToken = linkToken; }

    // Example behavior
    public boolean canEdit() {
        return role == ShareRole.EDITOR || role == ShareRole.OWNER;
    }

    @Override
    public String toString() {
        return "DeckShare{" +
                "id=" + id +
                ", role=" + role +
                ", linkToken='" + linkToken + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
