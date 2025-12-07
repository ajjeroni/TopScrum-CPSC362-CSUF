public class CardReviewAttemptQualityTest {
    public static void main(String[] args) {
        // Create a card
        Card card = new Card("comer", "to eat");

        // Create review attempts with different qualities
        ReviewAttempt attempt1 = new ReviewAttempt(Quality.GOOD, 1500, true, card, null);
        ReviewAttempt attempt2 = new ReviewAttempt(Quality.HARD, 3000, false, card, null);
        ReviewAttempt attempt3 = new ReviewAttempt(Quality.EASY, 800, true, card, null);

        // Link attempts to card
        card.addReviewAttempt(attempt1);
        card.addReviewAttempt(attempt2);
        card.addReviewAttempt(attempt3);

        // Show card state
        System.out.println("Card: " + card.getFrontText() + " → " + card.getBackText());
        System.out.println("ReviewAttempts count: " + card.getReviewAttempts().size());

        // Traverse attempts and print qualities
        for (ReviewAttempt attempt : card.getReviewAttempts()) {
            System.out.println("Attempt ID: " + attempt.getId()
                    + " | Quality: " + attempt.getQuality()
                    + " | Score: " + attempt.getQuality().getScore()
                    + " | Linked Card: " + attempt.getCard().getFrontText());
        }

        // Remove one attempt
        card.removeReviewAttempt(attempt2);

        System.out.println("\nAfter removing attempt2:");
        System.out.println("ReviewAttempts count: " + card.getReviewAttempts().size());
        for (ReviewAttempt attempt : card.getReviewAttempts()) {
            System.out.println("Remaining Attempt Quality: " + attempt.getQuality());
        }
    }
}
