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

    // Associations
    private UISettings settings;
    private List<AuthCredential> credentials;
    private List<Folder> folders;
    private List<Deck> decks;
    private List<CardProgress> progressRecords;
    private List<ReviewAttempt> attempts;
    private List<DeckShare> shares;
    private List<ImportJob> importJobs;
    private List<Device> devices;
    private List<ReviewAttempt> reviewAttempts = new ArrayList<>();

    // Master constructor
    public User(UUID id, String name, String email,
                LocalDateTime createdAt, LocalDateTime updatedAt,
                boolean isPowerUser, boolean isLanguageLearner,
                boolean isOrganizer, boolean isCommuter,
                UISettings settings,
                List<AuthCredential> credentials,
                List<Folder> folders,
                List<Deck> decks,
                List<CardProgress> progressRecords,
                List<ReviewAttempt> attempts,
                List<DeckShare> shares,
                List<ImportJob> importJobs,
                List<Device> devices) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isPowerUser = isPowerUser;
        this.isLanguageLearner = isLanguageLearner;
        this.isOrganizer = isOrganizer;
        this.isCommuter = isCommuter;
        this.settings = settings;
        this.credentials = credentials != null ? credentials : new ArrayList<>();
        this.folders = folders != null ? folders : new ArrayList<>();
        this.decks = decks != null ? decks : new ArrayList<>();
        this.progressRecords = progressRecords != null ? progressRecords : new ArrayList<>();
        this.attempts = attempts != null ? attempts : new ArrayList<>();
        this.shares = shares != null ? shares : new ArrayList<>();
        this.importJobs = importJobs != null ? importJobs : new ArrayList<>();
        this.devices = devices != null ? devices : new ArrayList<>();
    }

    public User(String name, String email) {
        this(UUID.randomUUID(), name, email,
             LocalDateTime.now(), LocalDateTime.now(),
             false, false, false, false,
             null,
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>());
    }

    public User(String name) {
        this(UUID.randomUUID(), name, null,
             LocalDateTime.now(), LocalDateTime.now(),
             false, false, false, false,
             null,
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>());
    }

    public User() {
        this(UUID.randomUUID(), null, null,
             LocalDateTime.now(), LocalDateTime.now(),
             false, false, false, false,
             null,
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>());
    }

    public User(String name, String email, UISettings settings) {
        this(UUID.randomUUID(), name, email,
             LocalDateTime.now(), LocalDateTime.now(),
             false, false, false, false,
             settings,
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>());
    }

    public User(String name, String email, List<AuthCredential> credentials) {
        this(UUID.randomUUID(), name, email,
             LocalDateTime.now(), LocalDateTime.now(),
             false, false, false, false,
             null,
             credentials, new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
             new ArrayList<>(), new ArrayList<>());
    }

    // Association methods
    public UISettings getSettings() { return settings; }
    public List<AuthCredential> getCredentials() { return credentials; }
    public List<Folder> getFolders() { return folders; }
    public List<Deck> getDecks() { return decks; }
    public List<CardProgress> getProgressRecords() { return progressRecords; }
    public List<ReviewAttempt> getAttempts() { return attempts; }
    public List<DeckShare> getShares() { return shares; }
    public List<ImportJob> getImportJobs() { return importJobs; }
    public List<Device> getDevices() { return devices; }

    public void addCredential(AuthCredential credential) { credentials.add(credential); }
    public void addFolder(Folder folder) { folders.add(folder); updatedAt = LocalDateTime.now(); }
    public void addDeck(Deck deck) { decks.add(deck); updatedAt = LocalDateTime.now(); }
    public void addProgress(CardProgress progress) { progressRecords.add(progress); }
    public void addAttempt(ReviewAttempt attempt) { attempts.add(attempt); }
    public void addShare(DeckShare share) {
        if (!shares.contains(share)) {
            shares.add(share);
            share.setUser(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void addImportJob(ImportJob job) { importJobs.add(job); }
    public void addDevice(Device device) { devices.add(device); }
    public void addReviewAttempt(ReviewAttempt attempt) {
        if (!reviewAttempts.contains(attempt)) {
            reviewAttempts.add(attempt);
            attempt.setUser(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
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
    public List<ReviewAttempt> getReviewAttempts() { return reviewAttempts; }

    // Setters
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
