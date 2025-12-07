import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Folder {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Deck> decks;
    private List<Folder> children;   // recursive hierarchy
    private User user;          // back-reference to User
    private Folder parent;      // back-reference to parent Folder

    // Master constructor
    public Folder(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt,
                  List<Deck> decks, List<Folder> children) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.decks = decks != null ? decks : new ArrayList<>();
        this.children = children != null ? children : new ArrayList<>();
    }

    // Convenience constructors (delegate to master)
    public Folder(UUID id, String name, LocalDateTime createdAt) {
        this(id, name, createdAt, createdAt, new ArrayList<>(), new ArrayList<>()
);
    }

    public Folder(UUID id, String name) {
        this(id, name, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new ArrayList<>()
);
    }

    public Folder(String name) {
        this(UUID.randomUUID(), name, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new ArrayList<>()
);
    }

    public Folder() {
        this(UUID.randomUUID(), null, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new ArrayList<>()
);
    }

    // Association methods
    public void addDeck(Deck deck) {
        if (!decks.contains(deck)) {
            decks.add(deck);
            deck.setFolder(this); // back-reference
            updatedAt = LocalDateTime.now();
        }
    }
    public void addChild(Folder child) {
        if (!children.contains(child)) {
            children.add(child);
            child.setParent(this); // back-reference
            updatedAt = LocalDateTime.now();
        }
    }
    public void removeChild(Folder child) {
        if (children.contains(child)) {
            children.remove(child);
            child.setParent(null); // break back-reference
            updatedAt = LocalDateTime.now();
        }
    }
    public void clearChildren() {
        for (Folder child : new ArrayList<>(children)) {
            child.setParent(null);
        }
        children.clear();
        updatedAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Deck> getDecks() { return decks; }
    public List<Folder> getChildren() { return children; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // Setters
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
    public Folder getParent() { return parent; }
    public void setParent(Folder parent) { this.parent = parent; }

    // Example behavior
    public void rename(String newName) {
        this.name = newName;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", decks=" + decks +
                ", children=" + children +
                '}';
    }
}
