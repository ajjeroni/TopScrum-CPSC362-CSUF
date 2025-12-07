public class AttachmentTest {
    public static void main(String[] args) {
        Card card = new Card("hablar", "to speak");
        Attachment attachment = new Attachment(AttachmentType.AUDIO, "audio/hablar.mp3");

        // Link them
        card.addAttachment(attachment);

        // Validate both directions
        System.out.println("Card → Attachment: " + card.getAttachments().contains(attachment)); // true
        System.out.println("Attachment → Card: " + (attachment.getCard() == card));             // true

        // Extra check
        System.out.println("Attachment type: " + attachment.getType());
        System.out.println("Attachment URL: " + attachment.getUrl());
    }
}
