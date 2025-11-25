import java.util.UUID;

public class UISettings {
    private UUID id;
    private Theme theme;
    private float fontScale;
    private boolean useSystemTheme;

    // Constructor
    public UISettings(UUID id, Theme theme, float fontScale, boolean useSystemTheme) {
        this.id = id;
        this.theme = theme;
        this.fontScale = fontScale;
        this.useSystemTheme = useSystemTheme;
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

    // Example behavior
    public void toggleSystemTheme() {
        this.useSystemTheme = !this.useSystemTheme;
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
