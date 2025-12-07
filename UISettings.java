import java.util.UUID;

public class UISettings {
    private UUID id;
    private Theme theme;
    private float fontScale;
    private boolean useSystemTheme;

    // Master constructor
    public UISettings(UUID id, Theme theme, float fontScale, boolean useSystemTheme) {
        this.id = id;
        this.theme = theme;
        this.fontScale = fontScale;
        this.useSystemTheme = useSystemTheme;
    }

    // Convenience constructors (all delegate to master)
    public UISettings(Theme theme, float fontScale, boolean useSystemTheme) {
        this(UUID.randomUUID(), theme, fontScale, useSystemTheme);
    }
    public UISettings(boolean useSystemTheme, int fontSize) {
        this(UUID.randomUUID(), Theme.SYSTEM, (float) fontSize, useSystemTheme);
    }
    public UISettings(Theme theme) {
        this(UUID.randomUUID(), theme, 1.0f, true);
    }

    public UISettings() {
        this(UUID.randomUUID(), Theme.SYSTEM, 1.0f, true);
    }

    // Getters
    public UUID getId() { return id; }
    public Theme getTheme() { return theme; }
    public float getFontScale() { return fontScale; }
    public boolean isUseSystemTheme() { return useSystemTheme; }

    // Setters
    public void setTheme(Theme theme) { this.theme = theme; }
    public void setFontScale(float fontScale) { this.fontScale = fontScale; }
    public void setUseSystemTheme(boolean useSystemTheme) { this.useSystemTheme = useSystemTheme; }
    public void setDarkMode(boolean enabled) {
        this.theme = enabled ? Theme.DARK : Theme.LIGHT;
        this.useSystemTheme = false; // optional: override system theme
    }
    // Example behavior
    public void toggleSystemTheme() {
        this.useSystemTheme = !this.useSystemTheme;
    }
    // Convenience check
    public boolean isDarkMode() {
        return this.theme == Theme.DARK;
    }
    @Override
    public String toString() {
        return "UISettings{" +
                "id=" + id +
                ", theme=" + theme +
                ", fontScale=" + fontScale +
                ", useSystemTheme=" + useSystemTheme +
                '}';
    }
}
