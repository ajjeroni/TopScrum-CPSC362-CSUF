public class UserAuthCredentialTest {
    public static void main(String[] args) {
        // Create a user
        User user = new User("Sean", "sean@example.com");

        // Create a credential
        AuthCredential credential = new AuthCredential(AuthProvider.GOOGLE, "sean123");

        // Add credential to user
        user.addCredential(credential);

        // Print results
        System.out.println("User credentials count: " + user.getCredentials().size());
        System.out.println("Credential belongs to user: " + credential.getUser().getName());

        // Simple pass/fail checks
        if (user.getCredentials().contains(credential) && credential.getUser() == user) {
            System.out.println("Association test PASSED");
        } else {
            System.out.println("Association test FAILED");
        }
    }
}
