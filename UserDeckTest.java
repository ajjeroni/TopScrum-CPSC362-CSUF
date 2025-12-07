public class UserDeckTest {
    public static void main(String[] args) {
        User user = new User("Sean", "sean@example.com");
        Deck deck = new Deck("Verbs", "Common Spanish verbs");

        user.addDeck(deck);

        System.out.println("User → Deck: " + user.getDecks().contains(deck)); // true
        System.out.println("Deck → User: " + (deck.getUser() == user));       // true
    }
}
