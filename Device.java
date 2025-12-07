import java.util.UUID;
import java.time.LocalDateTime;
import java.net.InetAddress;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Device {
    // ---fields---
    private UUID id;
    private String platform;
    private LocalDateTime lastSeenAt;

    // ---associations---
    private List<SyncState> syncStates;   // NEW: Device has many SyncStates
    private List<OfflineCache> offlineCaches = new ArrayList<>();

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
    public List<SyncState> getSyncStates() {
        return Collections.unmodifiableList(syncStates);
    }

    // ---setters---
    public void setPlatform(String platform) { this.platform = platform; }
    public void setLastSeenAt(LocalDateTime lastSeenAt) { this.lastSeenAt = lastSeenAt; }

    // ---Association Methods---
    public void addSyncState(SyncState syncState) {
        if (syncState != null && !syncStates.contains(syncState)) {
            syncStates.add(syncState);
            syncState.setDevice(this);
            updateLastSeen();
        }
    }
    public void removeSyncState(SyncState syncState) {
        if (syncState != null && syncStates.contains(syncState)) {
            syncStates.remove(syncState);
            syncState.setDevice(null); // break back-reference
            updateLastSeen();          // refresh timestamp on remove
        }
    }

    public void clearSyncStates() {
        for (SyncState state : new ArrayList<>(syncStates)) {
            state.setDevice(null);
        }
        syncStates.clear();
        updateLastSeen();              // refresh timestamp on clear
    }
    public void addOfflineCache(OfflineCache cache) {
        if (cache != null && !offlineCaches.contains(cache)) {
            offlineCaches.add(cache);
            cache.setDevice(this);
        }
    }

    public void removeOfflineCache(OfflineCache cache) {
        if (offlineCaches.contains(cache)) {
            offlineCaches.remove(cache);
            cache.setDevice(null);
        }
    }

    public List<OfflineCache> getOfflineCaches() {
        return offlineCaches;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device other = (Device) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
