import java.util.UUID;

public class DeckDeckShareUserTest {
    public static void main(String[] args) {
        // Create users and a deck
        User sean = new User("Sean", "sean@example.com");
        User alex = new User("Alex", "alex@example.com");
		UUID testUUID = UUID.randomUUID();
        Deck spanishDeck = new Deck("Spanish Basics", "Intro verbs", testUUID);

        // Create shares with different roles
        DeckShare ownerShare = new DeckShare(ShareRole.OWNER, spanishDeck, sean);
        DeckShare editorShare = new DeckShare(ShareRole.EDITOR, spanishDeck, alex);

        // Attach both shares
        ownerShare.attachTo(spanishDeck, sean);
        editorShare.attachTo(spanishDeck, alex);

        // --- Assertions / checks ---
        System.out.println("Deck shares count: " + spanishDeck.getShares().size()); // 2
        System.out.println("Sean shares count: " + sean.getShares().size());       // 1
        System.out.println("Alex shares count: " + alex.getShares().size());       // 1

        // Role checks
        System.out.println("Owner can edit? " + ownerShare.canEdit()); // true
        System.out.println("Owner isOwner? " + ownerShare.isOwner());  // true
        System.out.println("Editor can edit? " + editorShare.canEdit()); // true
        System.out.println("Editor isOwner? " + editorShare.isOwner());  // false

        // Equality check (same id means equal)
        DeckShare duplicateOwner = new DeckShare(ownerShare.getId(), ShareRole.OWNER, null,
                                                 ownerShare.getCreatedAt(), spanishDeck, sean);
        System.out.println("Owner equals duplicate? " + ownerShare.equals(duplicateOwner)); // true

        // Detach one share
        editorShare.detach();
        System.out.println("\nAfter detaching Alex:");
        System.out.println("Deck shares count: " + spanishDeck.getShares().size()); // 1
        System.out.println("Alex shares count: " + alex.getShares().size());        // 0

        // Detach the other share
        ownerShare.detach();
        System.out.println("\nAfter detaching Sean:");
        System.out.println("Deck shares count: " + spanishDeck.getShares().size()); // 0
        System.out.println("Sean shares count: " + sean.getShares().size());        // 0
    }
}
