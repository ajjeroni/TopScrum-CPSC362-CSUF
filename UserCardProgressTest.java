public class UserCardProgressTest {
    public static void main(String[] args) {
        // Create a User and two Cards
        User user = new User("Sean", "sean@example.com");
        Card card1 = new Card("comer", "to eat");
        Card card2 = new Card("beber", "to drink");

        // Create two CardProgress records
        CardProgress progress1 = new CardProgress(card1);
        CardProgress progress2 = new CardProgress(card2);

        // Wire associations
        user.addProgress(progress1);
        card1.addProgress(progress1);

        user.addProgress(progress2);
        card2.addProgress(progress2);

        System.out.println("=== Initial State ===");
        System.out.println("User → Progress count: " + user.getProgressRecords().size()); // 2
        System.out.println("Progress1 active? " + progress1.isActive()); // true
        System.out.println("Progress2 active? " + progress2.isActive()); // true

        // Deactivate only progress1
        progress1.deactivate();
        System.out.println("\n=== After Deactivating Progress1 ===");
        System.out.println("Progress1 active? " + progress1.isActive()); // false
        System.out.println("Progress2 active? " + progress2.isActive()); // true

        // Reactivate all progress records via User
        user.reactivateProgressRecords();
        System.out.println("\n=== After Reactivating All ===");
        System.out.println("Progress1 active? " + progress1.isActive()); // true
        System.out.println("Progress2 active? " + progress2.isActive()); // true

        // Cascade removal (hard delete all progress)
        user.clearProgressRecords();
        System.out.println("\n=== After Cascade Removal ===");
        System.out.println("User → Progress count: " + user.getProgressRecords().size()); // 0
        System.out.println("Card1 → Progress count: " + card1.getProgressRecords().size()); // 0
        System.out.println("Card2 → Progress count: " + card2.getProgressRecords().size()); // 0
        System.out.println("Progress1 → User: " + (progress1.getUser() == null)); // true
        System.out.println("Progress2 → User: " + (progress2.getUser() == null)); // true
    }
}
