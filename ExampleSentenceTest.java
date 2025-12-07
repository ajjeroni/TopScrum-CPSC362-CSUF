public class ExampleSentenceTest {
    public static void main(String[] args) {
        // Create a Card
        Card card = new Card("comer", "to eat");

        // Create an ExampleSentence
        ExampleSentence example = new ExampleSentence("Yo quiero comer pizza.");

        // Link them
        card.addExampleSentence(example);

        // Validate both directions
        System.out.println("Card → ExampleSentence: " + card.getExampleSentences().contains(example)); // true
        System.out.println("ExampleSentence → Card: " + (example.getCard() == card));                  // true

        // Extra check
        System.out.println("Sentence text: " + example.getSentence());
    }
}
