import java.util.UUID;
import java.time.LocalDateTime;

public class ReviewAttempt {
    private UUID id;
    private LocalDateTime timestamp;
    private Quality quality;
    private int responseMs;
    private boolean correct;

    // Constructor
    public ReviewAttempt(UUID id, LocalDateTime timestamp, Quality quality,
                         int responseMs, boolean correct) {
        this.id = id;
        this.timestamp = timestamp;
        this.quality = quality;
        this.responseMs = responseMs;
        this.correct = correct;
    }

    // Getters
    public UUID getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Quality getQuality() { return quality; }
    public int getResponseMs() { return responseMs; }
    public boolean isCorrect() { return correct; }

    // Setters
    public void setQuality(Quality quality) { this.quality = quality; }
    public void setResponseMs(int responseMs) { this.responseMs = responseMs; }
    public void setCorrect(boolean correct) { this.correct = correct; }

    // Example behavior
    public boolean wasFastResponse() {
        return responseMs < 2000; // under 2 seconds considered fast
    }

    @Override
    public String toString() {
        return "ReviewAttempt{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", quality=" + quality +
                ", responseMs=" + responseMs +
                ", correct=" + correct +
                '}';
    }
}
