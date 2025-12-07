import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Card {
    // ---fields---
    private UUID id;
    private String frontText;
    private String backText;
    private String hint;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Deck deck;   // belongs to one Deck

    // ---associations---
    private List<ReviewAttempt> reviewAttempts = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();
    private List<ExampleSentence> exampleSentences = new ArrayList<>();
    private List<CardProgress> progressRecords = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>(); //Card has many Tags

    // ---constructors---
    public Card(UUID id, String frontText, String backText, String hint,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.frontText = frontText;
        this.backText = backText;
        this.hint = hint != null ? hint : "";
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reviewAttempts = new ArrayList<>();
        this.attachments = new ArrayList<>();
        this.exampleSentences = new ArrayList<>();
        this.progressRecords = new ArrayList<>();
    }

    public Card(UUID id, String frontText, String backText, LocalDateTime createdAt) {
        this(id, frontText, backText, "", createdAt, createdAt);
    }

    public Card(UUID id, String frontText, String backText, String hint, LocalDateTime createdAt) {
        this(id, frontText, backText, hint, createdAt, createdAt);
    }

    public Card(String frontText, String backText) {
        this(UUID.randomUUID(), frontText, backText, "", LocalDateTime.now(), LocalDateTime.now());
    }

    public Card(String frontText, String backText, String hint) {
        this(UUID.randomUUID(), frontText, backText, hint, LocalDateTime.now(), LocalDateTime.now());
    }

    public Card() {
        this(UUID.randomUUID(), null, null, "", LocalDateTime.now(), LocalDateTime.now());
    }

    // ---getters---
    public UUID getId() { return id; }
    public String getFrontText() { return frontText; }
    public String getBackText() { return backText; }
    public String getHint() { return hint; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Deck getDeck() { return deck; }
    public List<ReviewAttempt> getReviewAttempts() { return reviewAttempts; }
    public List<Attachment> getAttachments() { return attachments; }
    public List<ExampleSentence> getExampleSentences() { return exampleSentences; }
    public List<CardProgress> getProgressRecords() { return progressRecords; }
    public List<Tag> getTags() { return tags; }

    // ---setters---
    public void setFrontText(String frontText) {
        this.frontText = frontText;
        this.updatedAt = LocalDateTime.now();
    }

    public void setBackText(String backText) {
        this.backText = backText;
        this.updatedAt = LocalDateTime.now();
    }

    public void setHint(String hint) {
        this.hint = hint;
        this.updatedAt = LocalDateTime.now();
    }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setDeck(Deck deck) { this.deck = deck; }

    // ---Association Methods---
    public void addReviewAttempt(ReviewAttempt attempt) {
        if (!reviewAttempts.contains(attempt)) {
            reviewAttempts.add(attempt);
            attempt.setCard(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void removeReviewAttempt(ReviewAttempt attempt) {
        if (reviewAttempts.contains(attempt)) {
            reviewAttempts.remove(attempt);
            attempt.setCard(null);
            updatedAt = LocalDateTime.now();
        }
    }
    public void addAttachment(Attachment attachment) {
        if (!attachments.contains(attachment)) {
            attachments.add(attachment);
            attachment.setCard(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void clearReviewAttempts() {
        for (ReviewAttempt attempt : new ArrayList<>(reviewAttempts)) {
            attempt.setCard(null);
            if (attempt.getUser() != null) {
                attempt.getUser().removeReviewAttempt(attempt);
            }
        }
        reviewAttempts.clear();
        updatedAt = LocalDateTime.now();
    }
    public void addExampleSentence(ExampleSentence sentence) {
        if (!exampleSentences.contains(sentence)) {
            exampleSentences.add(sentence);
            sentence.setCard(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void addProgress(CardProgress progress) {
        if (!progressRecords.contains(progress)) {
            progressRecords.add(progress);
            progress.setCard(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void removeProgress(CardProgress progress) {
        if (progressRecords.contains(progress)) {
            progressRecords.remove(progress);
            progress.setCard(null); // break bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            tag.addCard(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void removeTag(Tag tag) {
        if (tags.contains(tag)) {
            tags.remove(tag);
            tag.getCards().remove(this); // break bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }

    // ---behavior---
    public void flip() {
        System.out.println("Front: " + frontText);
        System.out.println("Back: " + backText);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", frontText='" + frontText + '\'' +
                ", backText='" + backText + '\'' +
                ", hint='" + hint + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
