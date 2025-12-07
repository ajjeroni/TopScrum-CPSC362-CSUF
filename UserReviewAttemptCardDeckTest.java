public class UserReviewAttemptCardDeckTest {
    public static void main(String[] args) {
        User user = new User("Sean", "sean@example.com");
        Deck deck = new Deck("Spanish Basics", "Intro verbs");

        Card card1 = new Card("comer", "to eat");
        Card card2 = new Card("beber", "to drink");

        deck.addCard(card1);
        deck.addCard(card2);

        // Use Quality constants from your enum
        ReviewAttempt attempt1 = new ReviewAttempt(Quality.GOOD, 1500, true, card1, user);
        ReviewAttempt attempt2 = new ReviewAttempt(Quality.HARD, 3000, false, card2, user);

        user.addReviewAttempt(attempt1);
        user.addReviewAttempt(attempt2);
        card1.addReviewAttempt(attempt1);
        card2.addReviewAttempt(attempt2);

        System.out.println("User attempts: " + user.getReviewAttempts().size()); // 2
        System.out.println("Deck attempts: " + deck.getAllReviewAttempts().size()); // 2

        deck.removeCard(card1);

        System.out.println("\nAfter removing card1:");
        System.out.println("Deck cards: " + deck.getCards().size()); // 1
        System.out.println("User attempts: " + user.getReviewAttempts().size()); // 1
        System.out.println("Deck attempts: " + deck.getAllReviewAttempts().size()); // 1

        attempt2.detach();

        System.out.println("\nAfter detaching attempt2:");
        System.out.println("User attempts: " + user.getReviewAttempts().size()); // 0
        System.out.println("Card2 attempts: " + card2.getReviewAttempts().size()); // 0
    }
}
