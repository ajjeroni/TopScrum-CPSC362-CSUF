import java.util.UUID;
import java.time.LocalDateTime;

public class Device {
    private UUID id;
    private String platform;
    private LocalDateTime lastSeenAt;

    // Constructor
    public Device(UUID id, String platform, LocalDateTime lastSeenAt) {
        this.id = id;
        this.platform = platform;
        this.lastSeenAt = lastSeenAt;
    }

    // Getters
    public UUID getId() { return id; }
    public String getPlatform() { return platform; }
    public LocalDateTime getLastSeenAt() { return lastSeenAt; }

    // Setters
    public void setPlatform(String platform) { this.platform = platform; }
    public void setLastSeenAt(LocalDateTime lastSeenAt) { this.lastSeenAt = lastSeenAt; }

    // Example behavior
    public void updateLastSeen() {
        this.lastSeenAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", platform='" + platform + '\'' +
                ", lastSeenAt=" + lastSeenAt +
                '}';
    }
}
