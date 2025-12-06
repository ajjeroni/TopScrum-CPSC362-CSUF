import java.util.UUID;
import java.time.LocalDateTime;

public class CardProgress {
    // ---fields---
    private UUID id;
    private LocalDateTime dueAt;
    private int intervalDays;
    private float ease;
    private int streak;
    private LocalDateTime lastReviewedAt;

    // ---associations---
    private Card card;             // belongs to one Card
    private OfflineCache offlineCache; // NEW: belongs to one OfflineCache

    // ---constructors---
    // Master constructor
    public CardProgress(UUID id, Card card, LocalDateTime dueAt, int intervalDays,
                        float ease, int streak, LocalDateTime lastReviewedAt, OfflineCache offlineCache) {
        this.id = id;
        this.card = card;
        this.dueAt = dueAt;
        this.intervalDays = intervalDays;
        this.ease = ease;
        this.streak = streak;
        this.lastReviewedAt = lastReviewedAt;
        this.offlineCache = offlineCache;
    }

    // Convenience constructors (delegate to master)
    public CardProgress(Card card, LocalDateTime dueAt) {
        this(UUID.randomUUID(), card, dueAt, 1, 2.5f, 0, null, null);
    }

    public CardProgress(Card card) {
        this(UUID.randomUUID(), card, LocalDateTime.now(), 1, 2.5f, 0, null, null);
    }

    public CardProgress() {
        this(UUID.randomUUID(), null, LocalDateTime.now(), 1, 2.5f, 0, null, null);
    }

    // ---getters---
    public UUID getId() { return id; }
    public LocalDateTime getDueAt() { return dueAt; }
    public int getIntervalDays() { return intervalDays; }
    public float getEase() { return ease; }
    public int getStreak() { return streak; }
    public LocalDateTime getLastReviewedAt() { return lastReviewedAt; }
    public Card getCard() { return card; }
    public OfflineCache getOfflineCache() { return offlineCache; }   // NEW

    // ---setters---
    public void setIntervalDays(int intervalDays) { this.intervalDays = intervalDays; }
    public void setEase(float ease) { this.ease = ease; }
    public void setStreak(int streak) { this.streak = streak; }
    public void setDueAt(LocalDateTime dueAt) { this.dueAt = dueAt; }
    public void setLastReviewedAt(LocalDateTime lastReviewedAt) { this.lastReviewedAt = lastReviewedAt; }
    public void setCard(Card card) { this.card = card; }
    public void setOfflineCache(OfflineCache offlineCache) { this.offlineCache = offlineCache; } // NEW

    // ---behavior---
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
                ", card=" + (card != null ? card.getId() : "null") +
                ", offlineCache=" + (offlineCache != null ? offlineCache.getId() : "null") +
                '}';
    }
}
