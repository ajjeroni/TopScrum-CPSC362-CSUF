import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;

//JSON Persistence
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainApp extends Application {

    // --- Domain state (minimal, grows over time) ---
    private User currentUser;            // will be set in initDomain()
    private List<Folder> folders = new ArrayList<>();

    // --- UI references ---
    private TreeView<String> navTree;
    private VBox detailsPanel;
    private Label status;

    @Override
    public void start(Stage primaryStage) {
        // Layout shell (keeps your design aspects)
        navTree = new TreeView<>(new TreeItem<>("No user yet"));
        navTree.setPrefWidth(180);
        navTree.setMaxWidth(260);
        navTree.setMinWidth(110);

        detailsPanel = new VBox();
        detailsPanel.setSpacing(10);

        status = new Label();
        status.setStyle("-fx-font-style: italic; -fx-text-fill: darkred;");

        BorderPane root = new BorderPane();
        root.setLeft(navTree);
        root.setCenter(detailsPanel);
        root.setBottom(status);

        Scene scene = new Scene(root, 600, 300);
        primaryStage.setTitle("Top Scrum Flash Card App");
        primaryStage.setMinWidth(250);
        primaryStage.setMinHeight(400);

        // Responsive sizing (reuse your scaling approach)
        final double baseFontSize = 12.0;
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double scale = width / 700.0;
            double fontSize = Math.max(12, Math.min(baseFontSize * scale, 16));
            double sharedWidth = Math.max(80, Math.min(150, 100 * scale));

            navTree.setPrefWidth(sharedWidth);
            detailsPanel.setStyle("-fx-font-size: " + fontSize + "px;");
        });

        // Step 1: initialize minimal domain
        initDomain();

        // Step 2: populate the sidebar from domain
        populateNavigationTree();

        // Step 3: selection handling (updates center panel)
        wireSelectionHandler();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Minimal domain seed (replace with real creation flows later)
    private void initDomain() {
    // Use the new overload
    currentUser = new User("Sean", "sean@example.com");

    // Create a folder → deck → card chain
    Folder f = new Folder(UUID.randomUUID(), "Programming", LocalDateTime.now());
    Deck d = new Deck(UUID.randomUUID(), "Java Basics", "Core syntax and types", LocalDateTime.now());
    Card c = new Card(UUID.randomUUID(), "What is a class?", "A blueprint for objects", LocalDateTime.now());

    // Respect associations
    d.addCard(c);
    f.addDeck(d);
    currentUser.addFolder(f);

    folders.add(f); // sidebar source
}

    // Build tree: User → Folders → Decks → Cards
    private void populateNavigationTree() {
        TreeItem<String> rootItem = new TreeItem<>(currentUser.getName());
        rootItem.setExpanded(true);

        // Special node for review history
        TreeItem<String> historyItem = new TreeItem<>("Review History");
        rootItem.getChildren().add(historyItem);

        for (Folder f : folders) {
            TreeItem<String> folderItem = new TreeItem<>("Folder: " + f.getName());
            folderItem.setExpanded(true);

            for (Deck d : f.getDecks()) {
                TreeItem<String> deckItem = new TreeItem<>("Deck: " + d.getTitle());
                deckItem.setExpanded(false);

                for (Card c : d.getCards()) {
                    TreeItem<String> cardItem = new TreeItem<>("Card: " + c.getFrontText());
                    deckItem.getChildren().add(cardItem);
                }
                folderItem.getChildren().add(deckItem);
            }
            rootItem.getChildren().add(folderItem);
        }
        navTree.setRoot(rootItem);
    }

    // Update center panel when a node is selected
    private void wireSelectionHandler() {
    // existing selection listener
    navTree.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
        detailsPanel.getChildren().clear();
        if (newItem == null) return;

        String value = newItem.getValue();
        if (value.startsWith("Folder: ")) {
            String name = value.substring("Folder: ".length());
            showFolderDetails(name);
        } else if (value.startsWith("Deck: ")) {
            String title = value.substring("Deck: ".length());
            showDeckDetails(title);
        } else if (value.startsWith("Card: ")) {
            String front = value.substring("Card: ".length());
            showCardDetails(front);
        } else if (value.equals("Review History")) {
            showUserReviewHistory();
        } else {
            showUserDetails();
        }
    });

    // add the cell factory here
    navTree.setCellFactory(tv -> {
        TreeCell<String> cell = new TreeCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
            }
        };

        ContextMenu menu = new ContextMenu();

        // Add Folder (on User node)
        MenuItem addFolder = new MenuItem("Add Folder");
        addFolder.setOnAction(e -> {
            if (cell.getItem() != null && cell.getItem().equals(currentUser.getName())) {
                Folder newFolder = new Folder(UUID.randomUUID(), "New Folder", LocalDateTime.now());
                currentUser.addFolder(newFolder);
                folders.add(newFolder);
                populateNavigationTree();
                status.setText("Folder added");
            }
        });

        // Add Deck (on Folder node)
        MenuItem addDeck = new MenuItem("Add Deck");
        addDeck.setOnAction(e -> {
            if (cell.getItem() != null && cell.getItem().startsWith("Folder: ")) {
                String folderName = cell.getItem().substring("Folder: ".length());
                Folder folder = folders.stream()
                                       .filter(f -> f.getName().equals(folderName))
                                       .findFirst().orElse(null);
                if (folder != null) {
                    Deck newDeck = new Deck(UUID.randomUUID(), "New Deck", "Description", LocalDateTime.now());
                    folder.addDeck(newDeck);
                    populateNavigationTree();
                    status.setText("Deck added to " + folderName);
                }
            }
        });

        // Add Card (on Deck node)
        MenuItem addCard = new MenuItem("Add Card");
        addCard.setOnAction(e -> {
            if (cell.getItem() != null && cell.getItem().startsWith("Deck: ")) {
                String deckTitle = cell.getItem().substring("Deck: ".length());
                Deck deck = folders.stream()
                                   .flatMap(f -> f.getDecks().stream())
                                   .filter(d -> d.getTitle().equals(deckTitle))
                                   .findFirst().orElse(null);
                if (deck != null) {
                    Card newCard = new Card(UUID.randomUUID(), "Front text", "Back text", LocalDateTime.now());
                    deck.addCard(newCard);
                    populateNavigationTree();
                    status.setText("Card added to " + deckTitle);
                }
            }
        });

        menu.getItems().addAll(addFolder, addDeck, addCard);
        cell.setContextMenu(menu);

        return cell;
    });
}

    // --- Detail renderers (minimal, we’ll expand later) ---
    private void showUserDetails() {
        Label h = new Label("User");
        Label name = new Label("Name: " + currentUser.getName());
        Label email = new Label("Email: " + currentUser.getEmail());
        detailsPanel.getChildren().addAll(h, name, email);
    }

    private void showFolderDetails(String name) {
        Folder folder = folders.stream().filter(f -> f.getName().equals(name)).findFirst().orElse(null);
        if (folder == null) return;
        Label h = new Label("Folder");
        Label n = new Label("Name: " + folder.getName());
        Label deckCount = new Label("Decks: " + folder.getDecks().size());
        detailsPanel.getChildren().addAll(h, n, deckCount);
    }

    private void showDeckDetails(String title) {
        Deck deck = folders.stream()
                .flatMap(f -> f.getDecks().stream())
                .filter(d -> d.getTitle().equals(title))
                .findFirst().orElse(null);
        if (deck == null) return;
        Label h = new Label("Deck");
        Label t = new Label("Title: " + deck.getTitle());
        Label desc = new Label("Description: " + deck.getDescription());
        Label cardCount = new Label("Cards: " + deck.getCards().size());
        detailsPanel.getChildren().addAll(h, t, desc, cardCount);
    }

    private void showCardDetails(String frontText) {
        Card card = folders.stream()
                .flatMap(f -> f.getDecks().stream())
                .flatMap(d -> d.getCards().stream())
                .filter(c -> c.getFrontText().equals(frontText))
                .findFirst().orElse(null);
        if (card == null) return;

        Label h = new Label("Card");

        TextField frontField = new TextField(card.getFrontText());
        TextField backField = new TextField(card.getBackText());

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            card.setFrontText(frontField.getText());
            card.setBackText(backField.getText());
            card.setUpdatedAt(LocalDateTime.now());
            populateNavigationTree();
            status.setText("Card updated");
        });

        // Review button to create a ReviewAttempt
        ComboBox<Quality> qualityBox = new ComboBox<>();
        qualityBox.getItems().addAll(Quality.values());
        qualityBox.setValue(Quality.GOOD);

        CheckBox correctBox = new CheckBox("Correct?");
        correctBox.setSelected(true);

        TextField responseField = new TextField("1500");
        responseField.setPromptText("Response time (ms)");

        Button reviewBtn = new Button("Review Card");
        reviewBtn.setOnAction(e -> {
            Quality q = qualityBox.getValue();
            boolean correct = correctBox.isSelected();
            int responseMs = Integer.parseInt(responseField.getText());

            ReviewAttempt attempt = new ReviewAttempt(q, responseMs, correct, card, currentUser);
            currentUser.addAttempt(attempt);
            card.addAttempt(attempt);
            status.setText("Review logged: " + attempt);

            // Refresh details to show updated attempts list
            showCardDetails(card.getFrontText());
        });

        //---layout---
        detailsPanel.getChildren().clear();
        detailsPanel.getChildren().addAll(h, frontField, backField, saveBtn, reviewBtn);
        // Show past attempts for this card
        VBox attemptsBox = new VBox();
        attemptsBox.getChildren().add(new Label("Past Attempts:"));

        for (ReviewAttempt attempt : card.getAttempts()) {
            Label attemptLabel = new Label(
                attempt.getTimestamp() + " | " +
                (attempt.isCorrect() ? "Correct" : "Incorrect") +
                " | " + attempt.getResponseMs() + "ms"
            );
            attemptsBox.getChildren().add(attemptLabel);
        }

        detailsPanel.getChildren().add(attemptsBox);

    }

    private void showUserReviewHistory() {
        detailsPanel.getChildren().clear();

        Label h = new Label("User Review History");
        detailsPanel.getChildren().add(h);

        VBox attemptsBox = new VBox();
        for (ReviewAttempt attempt : currentUser.getAttempts()) {
            Label attemptLabel = new Label(
                attempt.getTimestamp() + " | Card: " +
                (attempt.getCard() != null ? attempt.getCard().getFrontText() : "null") +
                " | " + (attempt.isCorrect() ? "Correct" : "Incorrect") +
                " | " + attempt.getResponseMs() + "ms" +
                " | Quality: " + attempt.getQuality()
            );
            attemptsBox.getChildren().add(attemptLabel);
        }
        detailsPanel.getChildren().add(attemptsBox);
    }

    private void saveDomainState() {
        try (FileWriter writer = new FileWriter("state.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(currentUser, writer);
        } catch (IOException e) {
            status.setText("Error saving state: " + e.getMessage());
        }
    }

    private void loadDomainState() {
        File file = new File("state.json");
        if (!file.exists()) {
            // Use your existing initDomain() to seed the app
            initDomain();
            return;
        }
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            currentUser = gson.fromJson(reader, User.class);
            folders = currentUser.getFolders();
        } catch (IOException e) {
            status.setText("Error loading state: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
