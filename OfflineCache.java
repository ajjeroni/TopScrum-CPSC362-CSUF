import java.util.UUID;
import java.time.LocalDateTime;

public class OfflineCache {
    private UUID id;
    private float sizeMB;
    private LocalDateTime lastUpdatedAt;

    // Constructor
    public OfflineCache(UUID id, float sizeMB, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.sizeMB = sizeMB;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    // Getters
    public UUID getId() { return id; }
    public float getSizeMB() { return sizeMB; }
    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }

    // Setters
    public void setSizeMB(float sizeMB) {
        this.sizeMB = sizeMB;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    // Example behavior
    public void clear() {
        this.sizeMB = 0.0f;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "OfflineCache{" +
                "id=" + id +
                ", sizeMB=" + sizeMB +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
