import java.util.UUID;
import java.time.LocalDateTime;

public class SyncState {
    private UUID id;
    private LocalDateTime lastSyncedAt;
    private long version;
    private int dirtyCount;
    private Device device;   // belongs to one Device

    // ---constructors---
    // Explicit constructor
    public SyncState(UUID id, LocalDateTime lastSyncedAt, long version, int dirtyCount) {
        this.id = id;
        this.lastSyncedAt = lastSyncedAt;
        this.version = version;
        this.dirtyCount = dirtyCount;
    }

    // Convenience no-arg constructor
    public SyncState() {
        this(UUID.randomUUID(), LocalDateTime.now(), 0L, 0);
    }

    // ---getters---
    public UUID getId() { return id; }
    public LocalDateTime getLastSyncedAt() { return lastSyncedAt; }
    public long getVersion() { return version; }
    public int getDirtyCount() { return dirtyCount; }
    public Device getDevice() { return device; }

    // ---setters---
    public void setLastSyncedAt(LocalDateTime lastSyncedAt) { this.lastSyncedAt = lastSyncedAt; }
    public void setVersion(long version) { this.version = version; }
    public void setDirtyCount(int dirtyCount) { this.dirtyCount = dirtyCount; }
    public void setDevice(Device device) { this.device = device; }

    // ---behaviors---
    public void markSynced(long newVersion) {
        this.lastSyncedAt = LocalDateTime.now();
        this.version = newVersion;
        this.dirtyCount = 0;
    }

    public void incrementDirtyCount() {
        this.dirtyCount++;
    }

    @Override
    public String toString() {
        return "SyncState{" +
                "id=" + id +
                ", lastSyncedAt=" + lastSyncedAt +
                ", version=" + version +
                ", dirtyCount=" + dirtyCount +
                '}';
    }
}
