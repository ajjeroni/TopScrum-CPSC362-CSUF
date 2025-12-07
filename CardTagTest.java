public class CardTagTest {
    public static void main(String[] args) {
        // Create a Card and a Tag
        Card card = new Card("comer", "to eat");
        Tag tag = new Tag("Spanish");

        // --- Add association from Card side ---
        card.addTag(tag);
        System.out.println("After card.addTag(tag):");
        System.out.println("Card → Tag: " + card.getTags().contains(tag)); // true
        System.out.println("Tag → Card: " + tag.getCards().contains(card)); // true

        // --- Remove association from Card side ---
        card.removeTag(tag);
        System.out.println("\nAfter card.removeTag(tag):");
        System.out.println("Card → Tag: " + card.getTags().contains(tag)); // false
        System.out.println("Tag → Card: " + tag.getCards().contains(card)); // false

        // --- Add association from Tag side ---
        tag.addCard(card);
        System.out.println("\nAfter tag.addCard(card):");
        System.out.println("Card → Tag: " + card.getTags().contains(tag)); // true
        System.out.println("Tag → Card: " + tag.getCards().contains(card)); // true

        // --- Remove association from Tag side ---
        tag.removeCard(card);
        System.out.println("\nAfter tag.removeCard(card):");
        System.out.println("Card → Tag: " + card.getTags().contains(tag)); // false
        System.out.println("Tag → Card: " + tag.getCards().contains(card)); // false
    }
}
