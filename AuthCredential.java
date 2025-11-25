import java.util.UUID;

public class AuthCredential {
    private UUID id;
    private AuthProvider provider;
    private String providerUserId;

    // Constructor
    public AuthCredential(UUID id, AuthProvider provider, String providerUserId) {
        this.id = id;
        this.provider = provider;
        this.providerUserId = providerUserId;
    }

    // Getters
    public UUID getId() { return id; }
    public AuthProvider getProvider() { return provider; }
    public String getProviderUserId() { return providerUserId; }

    // Setters (optional, depending on immutability preference)
    public void setProvider(AuthProvider provider) { this.provider = provider; }
    public void setProviderUserId(String providerUserId) { this.providerUserId = providerUserId; }

    @Override
    public String toString() {
        return "AuthCredential{" +
                "id=" + id +
                ", provider=" + provider +
                ", providerUserId='" + providerUserId + '\'' +
                '}';
    }
}
