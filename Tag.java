import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Tag {
    // ---fields---
    private UUID id;
    private String name;

    // ---associations---
    private List<Deck> decks = new ArrayList<>();   // Tag can belong to many Decks
    private List<Card> cards = new ArrayList<>();   // Tag can belong to many Cards

    // ---constructors---
    public Tag(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
        this(UUID.randomUUID(), name);
    }

    public Tag() {
        this(UUID.randomUUID(), null);
    }

    // ---getters---
    public UUID getId() { return id; }
    public String getName() { return name; }
    public List<Deck> getDecks() { return decks; }
    public List<Card> getCards() { return cards; }

    // ---setters---
    public void setName(String name) { this.name = name; }

    // ---Association Methods---
    public void addDeck(Deck deck) {
        if (!decks.contains(deck)) {
            decks.add(deck);
            if (!deck.getTags().contains(this)) {
                deck.getTags().add(this); // maintain bidirectional link
            }
        }
    }

    public void addCard(Card card) {
        if (!cards.contains(card)) {
            cards.add(card);
            if (!card.getTags().contains(this)) {
                card.getTags().add(this); // maintain bidirectional link
            }
        }
    }

    // ---behavior---
    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", decks=" + decks +
                ", cards=" + cards +
                '}';
    }
}
