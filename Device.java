import java.util.UUID;
import java.time.LocalDateTime;
import java.net.InetAddress;
import java.util.List;
import java.util.ArrayList;

public class Device {
    // ---fields---
    private UUID id;
    private String platform;
    private LocalDateTime lastSeenAt;

    // ---associations---
    private List<SyncState> syncStates;   // NEW: Device has many SyncStates

    // ---constructors---
    // Master constructor
    public Device(UUID id, String platform, LocalDateTime lastSeenAt, List<SyncState> syncStates) {
        this.id = id;
        this.platform = platform;
        this.lastSeenAt = lastSeenAt;
        this.syncStates = syncStates != null ? syncStates : new ArrayList<>();
    }

    // Convenience constructors (delegate to master)
    public Device(String platform, LocalDateTime lastSeenAt) {
        this(UUID.randomUUID(), platform, lastSeenAt, new ArrayList<>());
    }

    public Device(UUID id, String platform) {
        this(id, platform, LocalDateTime.now(), new ArrayList<>());
    }

    public Device(String platform) {
        this(UUID.randomUUID(), platform, LocalDateTime.now(), new ArrayList<>());
    }

    public Device() {
        this(UUID.randomUUID(), detectPlatform(), LocalDateTime.now(), new ArrayList<>());
    }

    // ---helpers---
    // Platform detection helper
    private static String detectPlatform() {
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

    // ---getters---
    public UUID getId() { return id; }
    public String getPlatform() { return platform; }
    public LocalDateTime getLastSeenAt() { return lastSeenAt; }
    public List<SyncState> getSyncStates() { return syncStates; }   // NEW

    // ---setters---
    public void setPlatform(String platform) { this.platform = platform; }
    public void setLastSeenAt(LocalDateTime lastSeenAt) { this.lastSeenAt = lastSeenAt; }

    // ---Association Methods---
    public void addSyncState(SyncState syncState) {
        syncStates.add(syncState);
        syncState.setDevice(this); // maintain bidirectional link
    }

    // ---behavior---
    public void updateLastSeen() {
        this.lastSeenAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", platform='" + platform + '\'' +
                ", lastSeenAt=" + lastSeenAt +
                ", syncStates=" + syncStates +
                '}';
    }
}
