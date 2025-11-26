import java.util.*;
import java.nio.file.*;
import java.io.*;

public class LocalRepository {
    private final Map<String, String> storage = new HashMap<>();
    private final Path savePath;

    // Default constructor
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

    public void save(String key, String value) {
        storage.put(key, value);
    }

    public String find(String key) {
        return storage.get(key);
    }

    public void delete(String key) {
        storage.remove(key);
    }

    public List<String> listAll() {
        return new ArrayList<>(storage.values());
    }

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
}
