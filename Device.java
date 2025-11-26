import java.util.UUID;
import java.time.LocalDateTime;
import java.net.InetAddress;

public class Device {
    private UUID id;
    private String platform;
    private LocalDateTime lastSeenAt;

    // Full constructor
    public Device(UUID id, String platform, LocalDateTime lastSeenAt) {
        this.id = id;
        this.platform = platform;
        this.lastSeenAt = lastSeenAt;
    }

    // Constructor: auto id
    public Device(String platform, LocalDateTime lastSeenAt) {
        this.id = UUID.randomUUID();
        this.platform = platform;
        this.lastSeenAt = lastSeenAt;
    }

    // Constructor: auto lastSeenAt
    public Device(UUID id, String platform) {
        this.id = id;
        this.platform = platform;
        this.lastSeenAt = LocalDateTime.now();
    }

    // Constructor: auto id + auto lastSeenAt
    public Device(String platform) {
        this.id = UUID.randomUUID();
        this.platform = platform;
        this.lastSeenAt = LocalDateTime.now();
    }

    // NEW: No-arg constructor that auto-detects platform
    public Device() {
        this.id = UUID.randomUUID();
        this.platform = detectPlatform();
        this.lastSeenAt = LocalDateTime.now();
    }

    private String detectPlatform() {
        try {
            String os = System.getProperty("os.name");
            String version = System.getProperty("os.version");
            String arch = System.getProperty("os.arch");
            String host = InetAddress.getLocalHost().getHostName();
            return host + " - " + os + " " + version + " (" + arch + ")";
        } catch (Exception e) {
            return System.getProperty("os.name") + " " + System.getProperty("os.version");
        }
    }

    // Getters
    public UUID getId() { return id; }
    public String getPlatform() { return platform; }
    public LocalDateTime getLastSeenAt() { return lastSeenAt; }

    // Setters
    public void setPlatform(String platform) { this.platform = platform; }
    public void setLastSeenAt(LocalDateTime lastSeenAt) { this.lastSeenAt = lastSeenAt; }

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
