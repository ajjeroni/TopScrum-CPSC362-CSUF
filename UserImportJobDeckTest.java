import java.util.UUID;

public class UserImportJobDeckTest {
    public static void main(String[] args) {
        // Create a user
        User sean = new User("Sean", "sean@example.com");

        // Create an import job and attach to user
        ImportJob job = new ImportJob("cards.csv", sean);
        job.attachTo(sean);

        // Create decks produced by the import
		UUID testUUID = UUID.randomUUID();
        Deck spanishDeck = new Deck("Spanish Basics", "Intro verbs", testUUID);
		UUID testUUID2 = UUID.randomUUID();
        Deck frenchDeck = new Deck("French Basics", "Intro verbs", testUUID2);

        // Attach decks to the job
        job.addDeck(spanishDeck);
        job.addDeck(frenchDeck);

        // --- Assertions / checks ---
        System.out.println("User jobs count: " + sean.getImportJobs().size()); // 1
        System.out.println("Job decks count: " + job.getDecks().size());       // 2
        System.out.println("Spanish deck import job: " + spanishDeck.getImportJob()); // job reference

        // Run the import
        job.runImport();

        // Equality check
        ImportJob duplicateJob = new ImportJob(job.getId(), job.getSource(),
                                               job.getCreatedAt(), job.getUpdatedAt(),
                                               sean, job.getDecks());
        System.out.println("Job equals duplicate? " + job.equals(duplicateJob)); // true

        // Detach one deck
        job.removeDeck(spanishDeck);
        System.out.println("\nAfter removing Spanish deck:");
        System.out.println("Job decks count: " + job.getDecks().size()); // 1
        System.out.println("Spanish deck import job: " + spanishDeck.getImportJob()); // null

        // Clear all decks
        job.clearDecks();
        System.out.println("\nAfter clearing all decks:");
        System.out.println("Job decks count: " + job.getDecks().size()); // 0

        // Detach from user
        job.detachFromUser();
        System.out.println("\nAfter detaching from user:");
        System.out.println("User jobs count: " + sean.getImportJobs().size()); // 0
        System.out.println("Job user: " + job.getUser()); // null
    }
}
