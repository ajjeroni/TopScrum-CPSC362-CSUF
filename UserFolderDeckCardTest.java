import java.util.UUID;

public class UserFolderDeckCardTest {
    public static void main(String[] args) {
        // Create root objects
        User user = new User("Sean", "sean@example.com");
        Folder folder = new Folder("Spanish Vocabulary");
		UUID testUUID = UUID.randomUUID();
        Deck deck = new Deck("Verbs", "Common Spanish verbs", testUUID);
        Card card = new Card("hablar", "to speak");
        Attachment attachment = new Attachment(AttachmentType.AUDIO, "audio/hablar.mp3");
        ExampleSentence example = new ExampleSentence("Yo quiero hablar contigo.");

        // Build associations
        user.addFolder(folder);
        folder.addDeck(deck);
        deck.addCard(card);
        card.addAttachment(attachment);
        card.addExampleSentence(example);

        // Validate chain
        System.out.println("User → Folder: " + (folder.getUser() == user));
        System.out.println("Folder → Deck: " + (deck.getFolder() == folder));
        System.out.println("Deck → Card: " + (card.getDeck() == deck));
        System.out.println("Card → Attachment: " + (attachment.getCard() == card));
        System.out.println("Card → ExampleSentence: " + (example.getCard() == card));

        // Extra: print names to confirm
        System.out.println("User: " + user.getName());
        System.out.println("Folder: " + folder.getName());
        System.out.println("Deck: " + deck.getTitle());
        System.out.println("Card: " + card.getFrontText() + " → " + card.getBackText());
        System.out.println("Attachment URL: " + attachment.getUrl());
        System.out.println("Example sentence: " + example.getSentence());
    }
}
