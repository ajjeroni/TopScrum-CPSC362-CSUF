public class DeckTagTest {
    public static void main(String[] args) {
        Deck deck = new Deck("Verbs", "Common Spanish verbs");
        Tag tag = new Tag("Spanish");

        deck.addTag(tag);

        System.out.println("Deck → Tag: " + deck.getTags().contains(tag)); // true
        System.out.println("Tag → Deck: " + tag.getDecks().contains(deck)); // true
    }
}
