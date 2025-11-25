import java.util.UUID;
import java.time.LocalDateTime;

public class ReviewAttempt {
    private UUID id;
    private LocalDateTime timestamp;
    private LocalDateTime endedAt;   // ✅ new field
    private Quality quality;
    private int responseMs;
    private boolean correct;

    // Full constructor: 6 inputs
    public ReviewAttempt(UUID id, LocalDateTime timestamp, LocalDateTime endedAt,
                         Quality quality, int responseMs, boolean correct) {
        this.id = id;
        this.timestamp = timestamp;
        this.endedAt = endedAt;
        this.quality = quality;
        this.responseMs = responseMs;
        this.correct = correct;
    }

    // Constructor: 5 inputs (auto id)
    public ReviewAttempt(LocalDateTime timestamp, LocalDateTime endedAt,
                         Quality quality, int responseMs, boolean correct) {
        this.id = UUID.randomUUID();
        this.timestamp = timestamp;
        this.endedAt = endedAt;
        this.quality = quality;
        this.responseMs = responseMs;
        this.correct = correct;
    }

    // Constructor: 4 inputs (auto id, endedAt set later)
    public ReviewAttempt(LocalDateTime timestamp, Quality quality,
                         int responseMs, boolean correct) {
        this.id = UUID.randomUUID();
        this.timestamp = timestamp;
        this.endedAt = null; // can be set later
        this.quality = quality;
        this.responseMs = responseMs;
        this.correct = correct;
    }

    // Constructor: 3 inputs (auto id + timestamp, endedAt set later)
    public ReviewAttempt(Quality quality, int responseMs, boolean correct) {
        this.id = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
        this.endedAt = null;
        this.quality = quality;
        this.responseMs = responseMs;
        this.correct = correct;
    }

    // Getters
    public UUID getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public LocalDateTime getEndedAt() { return endedAt; }   // ✅ new getter
    public Quality getQuality() { return quality; }
    public int getResponseMs() { return responseMs; }
    public boolean isCorrect() { return correct; }

    // Setters
    public void setQuality(Quality quality) { this.quality = quality; }
    public void setResponseMs(int responseMs) { this.responseMs = responseMs; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; } // ✅ new setter

    // Example behavior
    public boolean wasFastResponse() {
        return responseMs < 2000; // under 2 seconds considered fast
    }

    @Override
    public String toString() {
        return "ReviewAttempt{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", endedAt=" + endedAt +   // ✅ include endedAt
                ", quality=" + quality +
                ", responseMs=" + responseMs +
                ", correct=" + correct +
                '}';
    }
}
