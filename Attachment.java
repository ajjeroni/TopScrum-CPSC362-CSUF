import java.util.UUID;

public class Attachment {
    // ---fields---
    private UUID id;
    private AttachmentType type;
    private String url;
    private String altText;

    // ---associations---
    private Card card;   // belongs to one Card

    // ---constructors---
    public Attachment(UUID id, AttachmentType type, String url, String altText, Card card) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.altText = altText != null ? altText : "";
        this.card = card;
    }

    // Convenience constructors (delegate to master)
    public Attachment(AttachmentType type, String url, String altText) {
        this(UUID.randomUUID(), type, url, altText, null);
    }

    public Attachment(AttachmentType type, String url) {
        this(UUID.randomUUID(), type, url, "", null);
    }

    public Attachment() {
        this(UUID.randomUUID(), AttachmentType.IMAGE, null, "", null);
    }

    // ---getters---
    public UUID getId() { return id; }
    public AttachmentType getType() { return type; }
    public String getUrl() { return url; }
    public String getAltText() { return altText; }
    public Card getCard() { return card; }

    // ---setters---
    public void setType(AttachmentType type) { this.type = type; }
    public void setUrl(String url) { this.url = url; }
    public void setAltText(String altText) { this.altText = altText; }
    public void setCard(Card card) { this.card = card; }

    // ---behavior---
    public boolean isImage() {
        return type == AttachmentType.IMAGE;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", altText='" + altText + '\'' +
                ", card=" + (card != null ? card.getId() : "null") +
                '}';
    }
}
