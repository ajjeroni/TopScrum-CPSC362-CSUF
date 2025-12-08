import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isPowerUser;
    private boolean isLanguageLearner;
    private boolean isOrganizer;
    private boolean isCommuter;

    // Associations
    private UISettings settings;
    private List<AuthCredential> credentials;
    private List<Folder> folders = new ArrayList<>();
    private List<Deck> decks = new ArrayList<>();
    private List<CardProgress> progressRecords = new ArrayList<>();
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

    public User(UUID id, String name, String email) {
        this(id, name, email,
             LocalDateTime.now(), LocalDateTime.now(),
             false, false, false, false,
             null,
             new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
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
    public List<Device> getDevices() { return devices; }// Association methods
    public void addCredential(AuthCredential credential) {
        if (!credentials.contains(credential)) {
            credentials.add(credential);
            credential.setUser(this); // back-reference
            updatedAt = LocalDateTime.now();
        }
    }
    public void addFolder(Folder folder) {
        if (!folders.contains(folder)) {
            folders.add(folder);
            folder.setUser(this); // back-reference
            updatedAt = LocalDateTime.now();
        }
    }
    public void addDeck(Deck deck) {
        if (!decks.contains(deck)) {
            decks.add(deck);
            deck.setUser(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void addProgress(CardProgress progress) {
        if (!progressRecords.contains(progress)) {
            progressRecords.add(progress);
            progress.setUser(this); // maintain bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
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
    public void removeReviewAttempt(ReviewAttempt attempt) {
        if (reviewAttempts.contains(attempt)) {
            reviewAttempts.remove(attempt);
            attempt.setUser(null); // break bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void clearReviewAttempts() {
        for (ReviewAttempt attempt : new ArrayList<>(reviewAttempts)) {
            attempt.setUser(null);
            if (attempt.getCard() != null) {
                attempt.getCard().getReviewAttempts().remove(attempt);
                attempt.setCard(null);
            }
        }
        reviewAttempts.clear();
        updatedAt = LocalDateTime.now();
    }
    public void removeProgress(CardProgress progress) {
        if (progressRecords.contains(progress)) {
            progressRecords.remove(progress);
            progress.setUser(null); // break bidirectional link
            updatedAt = LocalDateTime.now();
        }
    }
    public void deactivateProgressRecords() {
        for (CardProgress progress : progressRecords) {
            progress.deactivate(); // mark inactive instead of removing
        }
    }
    public void reactivateProgressRecords() {
        for (CardProgress progress : progressRecords) {
            progress.reactivate();
        }
    }
    public void clearProgressRecords() {
        for (CardProgress progress : new ArrayList<>(progressRecords)) {
            // break bidirectional links
            progress.setUser(null);
            if (progress.getCard() != null) {
                progress.getCard().getProgressRecords().remove(progress);
                progress.setCard(null);
            }
        }
        progressRecords.clear();
        updatedAt = LocalDateTime.now();
    }
    public void removeShare(DeckShare share) {
        if (shares.contains(share)) {
            shares.remove(share);
            share.setUser(null); // break back-reference
            if (share.getDeck() != null) {
                share.getDeck().getShares().remove(share);
                share.setDeck(null);
            }
            updatedAt = LocalDateTime.now();
        }
    }

    public void clearShares() {
        for (DeckShare share : new ArrayList<>(shares)) {
            share.setUser(null);
            if (share.getDeck() != null) {
                share.getDeck().getShares().remove(share);
                share.setDeck(null);
            }
        }
        shares.clear();
        updatedAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getNotes() { return notes; }
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
    public void setNotes(String notes) { this.notes = notes; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setSettings(UISettings settings) {
        this.settings = settings;
        this.updatedAt = LocalDateTime.now(); // optional: keep timestamps fresh
    }

    // Example behavior
    public void promoteToPowerUser() {
        this.isPowerUser = true;
        this.updatedAt = LocalDateTime.now();
    }
    public void clearNotes() { this.notes = ""; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isPowerUser=" + isPowerUser +
                ", isLanguageLearner=" + isLanguageLearner +
                ", isOrganizer=" + isOrganizer +
                ", isCommuter=" + isCommuter +
                '}';
    }
}
