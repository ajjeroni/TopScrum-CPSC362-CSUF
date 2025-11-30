import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

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
    private List<Folder> folders;
    private List<ReviewAttempt> attempts = new ArrayList<>();

    public void addAttempt(ReviewAttempt attempt) {
        attempts.add(attempt);
    }

    public List<ReviewAttempt> getAttempts() {
        return attempts;
    }

    // Constructor: 9 inputs (full control)
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

    // Constructor: 2 inputs (name + email, auto id, timestamps now, flags false)
    public User(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.isPowerUser = false;
        this.isLanguageLearner = false;
        this.isOrganizer = false;
        this.isCommuter = false;
        this.folders = new ArrayList<>();
    }

    // Constructor: 1 input (name only, auto id/email null, timestamps now, flags false)
    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.isPowerUser = false;
        this.isLanguageLearner = false;
        this.isOrganizer = false;
        this.isCommuter = false;
        this.folders = new ArrayList<>();
    }

    // Constructor: no-arg (auto id, null name/email, timestamps now, flags false)
    public User() {
        this.id = UUID.randomUUID();
        this.name = null;
        this.email = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.isPowerUser = false;
        this.isLanguageLearner = false;
        this.isOrganizer = false;
        this.isCommuter = false;
        this.folders = new ArrayList<>();
    }

    // Add a folder to this user
    public void addFolder(Folder folder) {
        this.folders.add(folder);
        this.updatedAt = LocalDateTime.now();
    }

    public List<Folder> getFolders() {
        return folders;
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
