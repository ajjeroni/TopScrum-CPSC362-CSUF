public class DeviceOfflineCacheDeckCardProgressTest {
    public static void main(String[] args) {
        // --- Test Device ↔ OfflineCache ↔ Deck ---
        Device phone = new Device("Android Phone");
        OfflineCache cache = new OfflineCache();
        cache.setDevice(phone); // simulate association

        Deck vocabDeck = new Deck("Spanish Vocabulary", "Basic words");
        cache.addDeck(vocabDeck);

        System.out.println("Cache decks count: " + cache.getDecks().size()); // expect 1
        System.out.println("Deck offlineCache: " + vocabDeck.getOfflineCache()); // expect cache reference

        cache.removeDeck(vocabDeck);
        System.out.println("Cache decks count after removal: " + cache.getDecks().size()); // expect 0
        System.out.println("Deck offlineCache after removal: " + vocabDeck.getOfflineCache()); // expect null

        // --- Test Device ↔ OfflineCache ↔ CardProgress ---
        Card holaCard = new Card("Hola", "Hello");
        CardProgress progress = new CardProgress(holaCard);
        cache.addProgress(progress);

        System.out.println("\nCache progress count: " + cache.getProgressRecords().size()); // expect 1
        System.out.println("Progress offlineCache: " + progress.getOfflineCache()); // expect cache reference

        cache.clearProgressRecords();
        System.out.println("Cache progress count after clear: " + cache.getProgressRecords().size()); // expect 0
        System.out.println("Progress offlineCache after clear: " + progress.getOfflineCache()); // expect null
    }
}
