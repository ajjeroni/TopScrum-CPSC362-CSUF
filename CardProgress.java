import java.util.UUID;
import java.time.LocalDateTime;

public class CardProgress {
    private UUID id;
    private LocalDateTime dueAt;
    private int intervalDays;
    private float ease;
    private int streak;
    private LocalDateTime lastReviewedAt;

    // Constructor
    public CardProgress(UUID id, LocalDateTime dueAt, int intervalDays,
                        float ease, int streak, LocalDateTime lastReviewedAt) {
        this.id = id;
        this.dueAt = dueAt;
        this.intervalDays = intervalDays;
        this.ease = ease;
        this.streak = streak;
        this.lastReviewedAt = lastReviewedAt;
    }

    // Getters
    public UUID getId() { return id; }
    public LocalDateTime getDueAt() { return dueAt; }
    public int getIntervalDays() { return intervalDays; }
    public float getEase() { return ease; }
    public int getStreak() { return streak; }
    public LocalDateTime getLastReviewedAt() { return lastReviewedAt; }

    // Setters
    public void setIntervalDays(int intervalDays) { this.intervalDays = intervalDays; }
    public void setEase(float ease) { this.ease = ease; }
    public void setStreak(int streak) { this.streak = streak; }
    public void setDueAt(LocalDateTime dueAt) { this.dueAt = dueAt; }
    public void setLastReviewedAt(LocalDateTime lastReviewedAt) { this.lastReviewedAt = lastReviewedAt; }

    // Example behavior: record a review attempt
    public void recordReview(boolean correct, int qualityScore) {
        this.lastReviewedAt = LocalDateTime.now();

        if (correct) {
            streak++;
            ease = Math.max(1.3f, ease + (0.1f - (5 - qualityScore) * 0.08f));
            intervalDays = (intervalDays == 0) ? 1 : intervalDays * 2;
            dueAt = LocalDateTime.now().plusDays(intervalDays);
        } else {
            streak = 0;
            intervalDays = 1;
            dueAt = LocalDateTime.now().plusDays(intervalDays);
        }
    }

    @Override
    public String toString() {
        return "CardProgress{" +
                "id=" + id +
                ", dueAt=" + dueAt +
                ", intervalDays=" + intervalDays +
                ", ease=" + ease +
                ", streak=" + streak +
                ", lastReviewedAt=" + lastReviewedAt +
                '}';
    }
}
