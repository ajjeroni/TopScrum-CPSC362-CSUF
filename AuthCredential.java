import java.util.UUID;

public class AuthCredential {
    private UUID id;
    private AuthProvider provider;
    private String providerUserId;
    private User user; // back-reference

    // Master constructor
    public AuthCredential(UUID id, AuthProvider provider, String providerUserId) {
        this.id = id;
        this.provider = provider;
        this.providerUserId = providerUserId;
    }

    // Convenience constructors (delegate to master)
    public AuthCredential(AuthProvider provider, String providerUserId) {
        this(UUID.randomUUID(), provider, providerUserId);
    }

    public AuthCredential(AuthProvider provider) {
        this(UUID.randomUUID(), provider, null);
    }

    public AuthCredential() {
        this(UUID.randomUUID(), null, null);
    }

    // Getters
    public UUID getId() { return id; }
    public AuthProvider getProvider() { return provider; }
    public String getProviderUserId() { return providerUserId; }
    public User getUser() { return user; }

    // Setters
    public void setProvider(AuthProvider provider) { this.provider = provider; }
    public void setProviderUserId(String providerUserId) { this.providerUserId = providerUserId; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "AuthCredential{" +
                "id=" + id +
                ", provider=" + provider +
                ", providerUserId='" + providerUserId + '\'' +
                ", user=" + (user != null ? user.getName() : "null") +
                '}';
    }
}
