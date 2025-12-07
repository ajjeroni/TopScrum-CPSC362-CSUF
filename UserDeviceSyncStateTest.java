public class UserDeviceSyncStateTest {
    public static void main(String[] args) {
        // Create a user
        User sean = new User("Sean", "sean@example.com");

        // Create a device and attach to user
        Device phone = new Device("Android Phone");
        sean.addDevice(phone);

        // Create sync states using the no-arg constructor
        SyncState initialSync = new SyncState();
        initialSync.markSynced(1L); // simulate first sync

        SyncState failedSync = new SyncState();
        failedSync.incrementDirtyCount(); // simulate unsynced changes
        failedSync.markSynced(2L);        // simulate sync attempt

        // Attach sync states to device
        phone.addSyncState(initialSync);
        phone.addSyncState(failedSync);

        // --- Assertions / checks ---
        System.out.println("User devices count: " + sean.getDevices().size()); // 1
        System.out.println("Device sync states count: " + phone.getSyncStates().size()); // 2

        System.out.println("Initial sync version: " + initialSync.getVersion()); // 1
        System.out.println("Failed sync version: " + failedSync.getVersion());   // 2

        // Remove one sync state
        phone.removeSyncState(initialSync);
        System.out.println("\nAfter removing initial sync:");
        System.out.println("Device sync states count: " + phone.getSyncStates().size()); // 1
        System.out.println("Initial sync device: " + initialSync.getDevice()); // null

        // Clear all sync states
        phone.clearSyncStates();
        System.out.println("\nAfter clearing all sync states:");
        System.out.println("Device sync states count: " + phone.getSyncStates().size()); // 0
    }
}
