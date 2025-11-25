import java.util.UUID;
import java.time.LocalDateTime;

public class Device {
    private UUID id;
    private String platform;
    private LocalDateTime lastSeenAt;

    // Full constructor: 3 inputs (id, platform, lastSeenAt)
    public Device(UUID id, String platform, LocalDateTime lastSeenAt) {
        this.id = id;
        this.platform = platform;
        this.lastSeenAt = lastSeenAt;
    }

    // Constructor: 2 inputs (auto id, platform, lastSeenAt provided)
    public Device(String platform, LocalDateTime lastSeenAt) {
        this.id = UUID.randomUUID();
        this.platform = platform;
        this.lastSeenAt = lastSeenAt;
    }

    // Constructor: 2 inputs (id provided, auto lastSeenAt)
    public Device(UUID id, String platform) {
        this.id = id;
        this.platform = platform;
        this.lastSeenAt = LocalDateTime.now();
    }

    // Constructor: 1 input (auto id + auto lastSeenAt, platform provided)
    public Device(String platform) {
        this.id = UUID.randomUUID();
        this.platform = platform;
        this.lastSeenAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }
    public String getPlatform() { return platform; }
    public LocalDateTime getLastSeenAt() { return lastSeenAt; }

    // Setters
    public void setPlatform(String platform) { this.platform = platform; }
    public void setLastSeenAt(LocalDateTime lastSeenAt) { this.lastSeenAt = lastSeenAt; }

    // Example behavior: update lastSeenAt to current time
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
