import java.util.UUID;
import java.time.LocalDateTime;

public class ImportJob {
    private UUID id;
    private ImportSource source;
    private ImportStatus status;
    private LocalDateTime createdAt;
    private String log;

    // Constructor
    public ImportJob(UUID id, ImportSource source, ImportStatus status,
                     LocalDateTime createdAt, String log) {
        this.id = id;
        this.source = source;
        this.status = status;
        this.createdAt = createdAt;
        this.log = log;
    }

    // Getters
    public UUID getId() { return id; }
    public ImportSource getSource() { return source; }
    public ImportStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getLog() { return log; }

    // Setters
    public void setSource(ImportSource source) { this.source = source; }
    public void setStatus(ImportStatus status) { this.status = status; }
    public void setLog(String log) { this.log = log; }

    // Example behavior
    public void appendLog(String message) {
        this.log += "\n" + message;
    }

    public void markCompleted() {
        this.status = ImportStatus.COMPLETED;
        appendLog("Import completed successfully at " + LocalDateTime.now());
    }

    public void markFailed(String reason) {
        this.status = ImportStatus.FAILED;
        appendLog("Import failed: " + reason + " at " + LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "ImportJob{" +
                "id=" + id +
                ", source=" + source +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", log='" + log + '\'' +
                '}';
    }
}
