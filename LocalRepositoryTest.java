import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class LocalRepositoryTest {
    public static void main(String[] args) throws Exception {
        // --- User save/recall ---
        LocalRepository userRepo = new LocalRepository(
            Paths.get(System.getProperty("user.home"), ".domainTester", "users.txt")
        );
        User u = new User("Sean", "sean@example.com");
        userRepo.save("user:" + u.getId(), u.toString());
        userRepo.saveAllToFile();

        userRepo.loadAllFromFile();
        List<User> users = userRepo.findAllUsers();
        System.out.println("Users recalled: " + users);

        // --- Deck save/recall ---
        LocalRepository deckRepo = new LocalRepository(
            Paths.get(System.getProperty("user.home"), ".domainTester", "decks.txt")
        );
        Deck d = new Deck("Algorithms", "Study deck", u.getId());
        deckRepo.save("deck:" + d.getId(), d.toString());
        deckRepo.saveAllToFile();

        deckRepo.loadAllFromFile();
        List<Deck> decks = deckRepo.findAllDecks();
        System.out.println("Decks recalled: " + decks);

        // --- Card save/recall ---
        LocalRepository cardRepo = new LocalRepository(
            Paths.get(System.getProperty("user.home"), ".domainTester", "cards.txt")
        );
        Card c = new Card("Front text", "Back text");
        cardRepo.save("card:" + c.getId(), c.toString());
        cardRepo.saveAllToFile();

        cardRepo.loadAllFromFile();
        List<Card> cards = cardRepo.findAllCards();
        System.out.println("Cards recalled: " + cards);
    }
}
