import java.util.UUID;
import java.time.LocalDateTime;

public class User {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isPowerUser;
    private boolean isLanguageLearner;
    private boolean isOrganizer;
    private boolean isCommuter;

    // Constructor
    public User(UUID id, String name, String email,
                LocalDateTime createdAt, LocalDateTime updatedAt,
                boolean isPowerUser, boolean isLanguageLearner,
                boolean isOrganizer, boolean isCommuter) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isPowerUser = isPowerUser;
        this.isLanguageLearner = isLanguageLearner;
        this.isOrganizer = isOrganizer;
        this.isCommuter = isCommuter;
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isPowerUser() { return isPowerUser; }
    public boolean isLanguageLearner() { return isLanguageLearner; }
    public boolean isOrganizer() { return isOrganizer; }
    public boolean isCommuter() { return isCommuter; }

    // Setters (optional, depending on immutability preference)
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Example behavior
    public void promoteToPowerUser() {
        this.isPowerUser = true;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isPowerUser=" + isPowerUser +
                ", isLanguageLearner=" + isLanguageLearner +
                ", isOrganizer=" + isOrganizer +
                ", isCommuter=" + isCommuter +
                '}';
    }
}
