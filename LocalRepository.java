import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.time.LocalDateTime;


public class LocalRepository {
    private final Map<String, String> storage = new HashMap<>();
    private final Path savePath;

    // Default constructor: saves to ~/.domainTester/data.txt
    public LocalRepository() {
        String userHome = System.getProperty("user.home");
        this.savePath = Paths.get(userHome, ".domainTester", "data.txt");
        initDirectory();
    }

    // Overloaded constructor: caller provides custom path
    public LocalRepository(Path customPath) {
        this.savePath = customPath;
        initDirectory();
    }

    private void initDirectory() {
        try {
            Files.createDirectories(savePath.getParent());
        } catch (Exception e) {
            throw new RuntimeException("Could not create save directory", e);
        }
    }

    // --- basic operations ---
    public void save(String key, String value) { storage.put(key, value); }
    public String find(String key) { return storage.get(key); }
    public void delete(String key) { storage.remove(key); }

    public List<String> listAll() {
        return new ArrayList<>(storage.values());
    }

    // --- persistence ---
    public void saveAllToFile() throws IOException {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<String, String> entry : storage.entrySet()) {
            lines.add(entry.getKey() + "|" + entry.getValue());
        }
        Files.write(savePath, lines);
    }

    public void loadAllFromFile() throws IOException {
        if (!Files.exists(savePath)) return;
        List<String> lines = Files.readAllLines(savePath);
        for (String line : lines) {
            String[] parts = line.split("\\|", 2);
            if (parts.length == 2) {
                storage.put(parts[0], parts[1]);
            }
        }
    }

    // --- user parsing ---
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        for (Map.Entry<String, String> entry : storage.entrySet()) {
            if (entry.getKey().startsWith("user:")) {
                User u = parseUser(entry.getValue());
                if (u != null) {
                    users.add(u);
                }
            }
        }
        return users;
    }

    private User parseUser(String raw) {
        try {
            // Example toString: User{id=..., name='Sean', email='sean@example.com', ...}
            String idStr   = raw.replaceAll(".*id=([a-f0-9\\-]+).*", "$1");
            String name    = raw.replaceAll(".*name='([^']+)'.*", "$1");
            String email   = raw.replaceAll(".*email='([^']+)'.*", "$1");

            UUID id = UUID.fromString(idStr);

            // Requires a constructor in User like: public User(UUID id, String name, String email)
            return new User(id, name, email);
        } catch (Exception e) {
            System.err.println("Failed to parse user: " + raw);
            return null;
        }
    }

    // --- deck parsing ---
    public List<Deck> findAllDecks() {
        List<Deck> decks = new ArrayList<>();
        for (Map.Entry<String, String> entry : storage.entrySet()) {
            if (entry.getKey().startsWith("deck:")) {
                Deck d = parseDeck(entry.getValue());
                if (d != null) {
                    decks.add(d);
                }
            }
        }
        return decks;
    }

    private Deck parseDeck(String raw) {
        try {
            // Example toString: Deck{id=..., ownerId=..., title='...', description='...', isPublic=..., createdAt=..., updatedAt=...}
            String idStr       = raw.replaceAll(".*id=([a-f0-9\\-]+).*", "$1");
            String ownerIdStr  = raw.replaceAll(".*ownerId=([a-f0-9\\-]+).*", "$1");
            String title       = raw.replaceAll(".*title='([^']*)'.*", "$1");
            String description = raw.replaceAll(".*description='([^']*)'.*", "$1");
            String createdStr  = raw.replaceAll(".*createdAt=([^,}]+).*", "$1");
            String updatedStr  = raw.replaceAll(".*updatedAt=([^,}]+).*", "$1");

            UUID id       = UUID.fromString(idStr);
            UUID ownerId  = UUID.fromString(ownerIdStr);
            LocalDateTime createdAt = LocalDateTime.parse(createdStr);
            LocalDateTime updatedAt = LocalDateTime.parse(updatedStr);

            return new Deck(id, ownerId, title, description, false, createdAt, updatedAt, new ArrayList<>());
        } catch (Exception e) {
            System.err.println("Failed to parse deck: " + raw);
            return null;
        }
    }

    // --- card parsing ---
    public List<Card> findAllCards() {
        List<Card> cards = new ArrayList<>();
        for (Map.Entry<String, String> entry : storage.entrySet()) {
            if (entry.getKey().startsWith("card:")) {
                Card c = parseCard(entry.getValue());
                if (c != null) {
                    cards.add(c);
                }
            }
        }
        return cards;
    }

    private Card parseCard(String raw) {
        try {
            // Example raw: Card{id=..., frontText='Question', backText='Answer', ...}
            String front = raw.replaceAll(".*frontText='([^']*)'.*", "$1");
            String back  = raw.replaceAll(".*backText='([^']*)'.*", "$1");
            return new Card(front, back);
        } catch (Exception e) {
            return null;
        }
    }

    // --- getters ---
    public Path getSavePath() {
        return savePath;
    }
}
