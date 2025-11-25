import java.util.UUID;

public class Attachment {
    private UUID id;
    private AttachmentType type;
    private String url;
    private String altText;

    // Constructor
    public Attachment(UUID id, AttachmentType type, String url, String altText) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.altText = altText;
    }

    // Getters
    public UUID getId() { return id; }
    public AttachmentType getType() { return type; }
    public String getUrl() { return url; }
    public String getAltText() { return altText; }

    // Setters
    public void setType(AttachmentType type) { this.type = type; }
    public void setUrl(String url) { this.url = url; }
    public void setAltText(String altText) { this.altText = altText; }

    // Example behavior
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
                '}';
    }
}
