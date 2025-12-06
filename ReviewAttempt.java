import java.util.UUID;
import java.time.LocalDateTime;

public class ReviewAttempt {
    private UUID id;
    private LocalDateTime timestamp;
    private LocalDateTime endedAt;
    private Quality quality;
    private int responseMs;
    private boolean correct;
    private Card card;
    private User user;

    // Master constructor
    public ReviewAttempt(UUID id, LocalDateTime timestamp, LocalDateTime endedAt,
                         Quality quality, int responseMs, boolean correct,
                         Card card, User user) {
        this.id = id;
        this.timestamp = timestamp;
        this.endedAt = endedAt;
        this.quality = quality;
        this.responseMs = responseMs;
        this.correct = correct;
        this.card = card;
        this.user = user;
    }

    // Convenience constructors (delegate to master)
    public ReviewAttempt(LocalDateTime timestamp, LocalDateTime endedAt,
                         Quality quality, int responseMs, boolean correct,
                         Card card, User user) {
        this(UUID.randomUUID(), timestamp, endedAt, quality, responseMs, correct, card, user);
    }

    public ReviewAttempt(LocalDateTime timestamp, Quality quality,
                         int responseMs, boolean correct,
                         Card card, User user) {
        this(UUID.randomUUID(), timestamp, null, quality, responseMs, correct, card, user);
    }

    public ReviewAttempt(Quality quality, int responseMs, boolean correct,
                         Card card, User user) {
        this(UUID.randomUUID(), LocalDateTime.now(), null, quality, responseMs, correct, card, user);
    }

    public ReviewAttempt() {
        this(UUID.randomUUID(), LocalDateTime.now(), null, null, 0, false, null, null);
    }

    // Getters
    public UUID getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public LocalDateTime getEndedAt() { return endedAt; }
    public Quality getQuality() { return quality; }
    public int getResponseMs() { return responseMs; }
    public boolean isCorrect() { return correct; }
    public Card getCard() { return card; }
    public User getUser() { return user; }

    // Setters
    public void setQuality(Quality quality) { this.quality = quality; }
    public void setResponseMs(int responseMs) { this.responseMs = responseMs; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; }
    public void setCard(Card card) { this.card = card; }
    public void setUser(User user) { this.user = user; }

    // Example behavior
    public boolean wasFastResponse() {
        return responseMs < 2000; // under 2 seconds considered fast
    }

    @Override
    public String toString() {
        return "ReviewAttempt{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", endedAt=" + endedAt +
                ", quality=" + quality +
                ", responseMs=" + responseMs +
                ", correct=" + correct +
                '}';
    }
}
