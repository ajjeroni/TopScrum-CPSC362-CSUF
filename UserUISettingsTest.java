import java.util.UUID;
import java.time.LocalDateTime;

// Import your domain classes
// (adjust package names if you’ve organized them into packages)
public class UserUISettingsTest {
    public static void main(String[] args) {
        // Create a user
        User u = new User("Sean", "sean@example.com");

        // Create UISettings (example: dark mode + font size)
        UISettings settings = new UISettings(true, 14);

        // Assign settings to user
        u.setSettings(settings);

        // Verify association
        System.out.println("User name: " + u.getName());
        System.out.println("User settings: DarkMode=" + u.getSettings().isDarkMode()
                           + ", FontSize=" + u.getSettings().getFontScale());

        // Update settings
        settings.setDarkMode(false);
        settings.setFontScale(16);

        // Verify update is visible through user
        System.out.println("Updated User settings: DarkMode=" + u.getSettings().isDarkMode()
                           + ", FontSize=" + u.getSettings().getFontScale());
    }
}
