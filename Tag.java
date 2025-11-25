import java.util.UUID;

public class Tag {
    private UUID id;
    private String name;

    // Constructor
    public Tag(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }

    // Setters
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
