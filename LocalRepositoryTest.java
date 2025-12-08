import java.time.LocalDateTime;
import java.util.UUID;

public class LocalRepositoryTest {
    public static void main(String[] args) {
        LocalRepository repo = new LocalRepository();

        // --- Create a User and save it ---
        String userId = UUID.randomUUID().toString();
        String userKey = "user:" + userId;
        String userValue = "User{id=" + userId
                + ", name='Sean', email='sean@example.com', createdAt=" + LocalDateTime.now()
                + ", updatedAt=" + LocalDateTime.now()
                + ", isPowerUser=true}";

        repo.save(userKey, userValue);
        System.out.println("Saved user -> " + userValue);

        try {
            // Persist to file
            repo.saveAllToFile();
            System.out.println("Saved all to file.");

            // Reload into a fresh repository
            LocalRepository reloaded = new LocalRepository();
            reloaded.loadAllFromFile();

            // Recall the user
            String recalled = reloaded.find(userKey);
            System.out.println("Recalled user -> " + recalled);
        } catch (Exception ex) {
            System.err.println("Persistence test failed: " + ex.getMessage());
        }
    }
}
