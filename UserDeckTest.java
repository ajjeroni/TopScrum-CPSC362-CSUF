import java.util.UUID;

public class UserDeckTest {
    public static void main(String[] args) {
		UUID testUUID = UUID.randomUUID();
        User user = new User("Sean", "sean@example.com");
        Deck deck = new Deck("Verbs", "Common Spanish verbs", testUUID);

        user.addDeck(deck);

        System.out.println("User → Deck: " + user.getDecks().contains(deck)); // true
        System.out.println("Deck → User: " + (deck.getUser() == user));       // true
    }
}
