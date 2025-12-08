import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.Optional;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class UI extends Application {

    private ReviewAttempt currentAttempt;
    private User currentUser;
    // --- repositories ---
    private LocalRepository userRepo = new LocalRepository(
        Paths.get(System.getProperty("user.home"), ".domainTester", "users.txt")
    );
    private LocalRepository deckRepo = new LocalRepository(
        Paths.get(System.getProperty("user.home"), ".domainTester", "decks.txt")
    );
    private LocalRepository cardRepo = new LocalRepository(
        Paths.get(System.getProperty("user.home"), ".domainTester", "cards.txt")
    );
    private LocalRepository reviewRepo = new LocalRepository(
	Paths.get(System.getProperty("user.home"), ".domainTester", "reviews.txt")
    );

    // --- session state ---
    private Deck currentDeck;
    private Card currentCard;
    private CardView viewMode = CardView.BOTH;
    private int currentCardIndex = -1;

	// --- card editor areas
    private TextArea frontArea;
    private TextArea backArea;

    @Override
    public void start(Stage primaryStage) {
	try {
	    userRepo.loadAllFromFile();
	    deckRepo.loadAllFromFile();
	    cardRepo.loadAllFromFile();
	    reviewRepo.loadAllFromFile();
	} catch (Exception ex) {
	    System.err.println("Failed to load data: " + ex.getMessage());
	}
	// --- Re‑attach decks to users ---
	List<User> users = userRepo.findAllUsers();
	List<Deck> decks = deckRepo.findAllDecks();

	for (User u : users) {
	    for (Deck d : decks) {
		if (d.getOwnerId().equals(u.getId())) {
		    u.addDeck(d);
		    d.setUser(u); // maintain back-reference
		}
	    }
	}
        // --- Card editor areas ---
		frontArea = new TextArea();
		frontArea.setPromptText("Front text...");

		backArea = new TextArea();
		backArea.setPromptText("Back text...");

		VBox cardEditor = new VBox(10, frontArea, backArea);

        // --- Buttons ---
        Button userButton   = new Button("Users");
        Button deckButton   = new Button("Decks");
        Button cardButton   = new Button("Cards");
        Button folderButton = new Button("Folders");
        Button reviewButton = new Button("Review");
	Label reviewStatus = new Label();
	reviewStatus.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-style: italic;");
        Button saveCardButton = new Button("Save Card");
	Button removeCardButton = new Button("Remove Card");

        VBox reviewBox = new VBox(5, reviewButton, reviewStatus);

	VBox buttonColumn = new VBox(10,
	    userButton, deckButton, cardButton, folderButton, reviewBox);

		// --- Device detection for platform-aware scaling ---
		Device device = new Device();
		String platform = device.getPlatform();

		double baseFontSize;
		double baseButtonPadding;

		switch (platform.toLowerCase()) {
			case "windows":
				baseFontSize = 12;
				baseButtonPadding = 15;
				break;
			case "mac":
				baseFontSize = 14;
				baseButtonPadding = 18;
				break;
			case "linux":
				baseFontSize = 12;
				baseButtonPadding = 15;
				break;
			default:
				baseFontSize = 12;
				baseButtonPadding = 15;
		}

        // --- Button Styles ---
        for (Button b : new Button[]{userButton, deckButton, cardButton, folderButton, reviewButton}) {
			b.setStyle("-fx-font-weight: bold; -fx-font-size: " + baseFontSize + "px; "
					 + "-fx-padding: " + baseButtonPadding + "px " + (baseButtonPadding * 2) + "px;");
			b.setPrefWidth(buttonColumn.getPrefWidth());
			b.setMaxWidth(Double.MAX_VALUE);
			b.setPrefHeight(50);
			b.setMaxHeight(60);
			b.setWrapText(true);
		}

	// --- User button ---
	userButton.setOnAction(e -> {
	    // Collect existing user names
	    List<User> savedUsers = userRepo.findAllUsers();
	    List<String> userNames = savedUsers.stream()
					       .map(User::getName)
					       .toList();

	    // Editable ComboBox
	    ComboBox<String> combo = new ComboBox<>();
	    combo.getItems().addAll(userNames);
	    combo.setEditable(true); // allows typing new names

	    Dialog<String> dialog = new Dialog<>();
	    dialog.setTitle("Open or Create User");
	    dialog.setHeaderText("Select an existing user or type a new name");
	    dialog.getDialogPane().setContent(combo);
	    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

	    dialog.setResultConverter(button -> {
		if (button == ButtonType.OK) {
		    return combo.getEditor().getText();
		}
		return null;
	    });

	    dialog.showAndWait().ifPresent(name -> {
		User found = savedUsers.stream()
				       .filter(u -> name.equalsIgnoreCase(u.getName()))
				       .findFirst()
				       .orElse(null);

		if (found != null) {
		    currentUser = found;
		    new Alert(Alert.AlertType.INFORMATION,
			"User ready: " + currentUser.getName()).showAndWait();
		} else {
		    // Prevent duplicate names
		    boolean exists = savedUsers.stream()
					       .anyMatch(u -> u.getName().equalsIgnoreCase(name));
		    if (exists) {
			new Alert(Alert.AlertType.WARNING,
			    "A user with that name already exists.").showAndWait();
			return;
		    }

		    User u = new User(name, "");
		    currentUser = u;
		    try {
			userRepo.save("user:" + u.getId(), u.toString());
			userRepo.saveAllToFile();
		    } catch (IOException ex) {
			System.err.println("Failed to save user: " + ex.getMessage());
		    }
		    new Alert(Alert.AlertType.INFORMATION,
			"User created: " + currentUser.getName()).showAndWait();
		}
	    });
	});

	// --- Deck button ---
	deckButton.setOnAction(e -> {
	    // If no user, redirect to Users first
	    if (currentUser == null) {
		userButton.fire();   // reuse Users button logic
	    }
	    if (currentUser == null) return; // still null → stop

	    // Now continue with deck logic
	    List<Deck> savedDecks = currentUser.getDecks();
	    List<String> deckNames = savedDecks.stream()
					       .map(Deck::getTitle)
					       .toList();

	    ComboBox<String> combo = new ComboBox<>();
	    combo.getItems().addAll(deckNames);
	    combo.setEditable(true);

	    Dialog<String> dialog = new Dialog<>();
	    dialog.setTitle("Open or Create Deck");
	    dialog.setHeaderText("Select an existing deck or type a new name");
	    dialog.getDialogPane().setContent(combo);
	    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

	    dialog.setResultConverter(button -> {
		if (button == ButtonType.OK) {
		    return combo.getEditor().getText();
		}
		return null;
	    });

	    dialog.showAndWait().ifPresent(deckName -> {
		Deck found = savedDecks.stream()
				       .filter(d -> deckName.equalsIgnoreCase(d.getTitle()))
				       .findFirst()
				       .orElse(null);

		if (found != null) {
		    currentDeck = found;
		} else {
		    boolean exists = savedDecks.stream()
					       .anyMatch(d -> d.getTitle().equalsIgnoreCase(deckName));
		    if (exists) {
			new Alert(Alert.AlertType.WARNING,
			    "A deck with that name already exists.").showAndWait();
			return;
		    }

		    Deck d = new Deck(deckName, "", currentUser.getId());
		    currentUser.addDeck(d);
		    currentDeck = d;

		    try {
			deckRepo.save("deck:" + d.getId(), d.toString());
			deckRepo.saveAllToFile();
			userRepo.save("user:" + currentUser.getId(), currentUser.toString());
			userRepo.saveAllToFile();
		    } catch (IOException ex) {
			System.err.println("Failed to save deck/user: " + ex.getMessage());
		    }
		}
	    });
	});

	// --- Card button ---
	cardButton.setOnAction(e -> {
	    // Ensure user exists
	    if (currentUser == null) {
		userButton.fire();
	    }
	    if (currentUser == null) return;

	    // Ensure deck exists
	    if (currentDeck == null) {
		deckButton.fire();
	    }
	    if (currentDeck == null) return;

	    // Always create a new card
	    Card c = new Card("New Card", "");
	    currentDeck.addCard(c);
	    currentCard = c;
	    currentCardIndex = currentDeck.getCards().size() - 1;

	    try {
		// Persist card
		cardRepo.save("card:" + c.getId(), c.toString());
		cardRepo.saveAllToFile();

		// Persist deck (so its card list is updated)
		deckRepo.save("deck:" + currentDeck.getId(), currentDeck.toString());
		deckRepo.saveAllToFile();

		// Persist user (so their deck list is updated)
		userRepo.save("user:" + currentUser.getId(), currentUser.toString());
		userRepo.saveAllToFile();
	    } catch (IOException ex) {
		System.err.println("Failed to save card/deck/user: " + ex.getMessage());
	    }

	    // Update UI
	    frontArea.setText(currentCard.getFrontText());
	    backArea.setText(currentCard.getBackText());
	});
	// --- Save button ---
	saveCardButton.setOnAction(e -> {
	    if (currentUser == null) {
		new Alert(Alert.AlertType.WARNING,
		    "Please select or create a user first.").showAndWait();
		return;
	    }
	    if (currentDeck == null) {
		new Alert(Alert.AlertType.WARNING,
		    "Please select or create a deck first.").showAndWait();
		return;
	    }
	    if (currentCard == null) {
		new Alert(Alert.AlertType.WARNING,
		    "No card selected. Please create or open a card first.").showAndWait();
		return;
	    }

	    // Update card with current text
	    currentCard.setFrontText(frontArea.getText());
	    currentCard.setBackText(backArea.getText());

	    try {
		// Persist card
		cardRepo.save("card:" + currentCard.getId(), currentCard.toString());
		cardRepo.saveAllToFile();

		// Persist deck (so its card list is updated)
		deckRepo.save("deck:" + currentDeck.getId(), currentDeck.toString());
		deckRepo.saveAllToFile();

		// Persist user (so their deck list is updated)
		userRepo.save("user:" + currentUser.getId(), currentUser.toString());
		userRepo.saveAllToFile();

		new Alert(Alert.AlertType.INFORMATION,
		    "Card saved successfully!").showAndWait();
	    } catch (IOException ex) {
		System.err.println("Failed to save card/deck/user: " + ex.getMessage());
		new Alert(Alert.AlertType.ERROR,
		    "Failed to save card. See console for details.").showAndWait();
	    }
	});
	// --- Remove Card button ---
	removeCardButton.setOnAction(e -> {
	    if (currentUser == null || currentDeck == null || currentCard == null) {
		new Alert(Alert.AlertType.WARNING,
		    "No card selected to remove.").showAndWait();
		return;
	    }

	    // Confirmation dialog
	    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	    confirm.setTitle("Confirm Removal");
	    confirm.setHeaderText("Remove Card");
	    confirm.setContentText("Are you sure you want to remove this card?");

	    Optional<ButtonType> result = confirm.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
		try {
		    // Remove card from deck
		    currentDeck.getCards().remove(currentCard);

		    // Remove from cardRepo
		    cardRepo.delete("card:" + currentCard.getId());
		    cardRepo.saveAllToFile();

		    // Update deck and user repos
		    deckRepo.save("deck:" + currentDeck.getId(), currentDeck.toString());
		    deckRepo.saveAllToFile();

		    userRepo.save("user:" + currentUser.getId(), currentUser.toString());
		    userRepo.saveAllToFile();

		    // Clear current card selection
		    currentCard = null;
		    currentCardIndex = -1;
		    frontArea.clear();
		    backArea.clear();

		    new Alert(Alert.AlertType.INFORMATION,
			"Card removed successfully!").showAndWait();
		} catch (IOException ex) {
		    System.err.println("Failed to remove card: " + ex.getMessage());
		    new Alert(Alert.AlertType.ERROR,
			"Failed to remove card. See console for details.").showAndWait();
		}
	    }
	});

	// --- Folder button ---
	folderButton.setOnAction(e -> {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Repository files are located at:\n\n");
	    sb.append("Users: ").append(userRepo.getSavePath().toAbsolutePath().toString()).append("\n");
	    sb.append("Decks: ").append(deckRepo.getSavePath().toAbsolutePath().toString()).append("\n");
	    sb.append("Cards: ").append(cardRepo.getSavePath().toAbsolutePath().toString()).append("\n");

	    new Alert(Alert.AlertType.INFORMATION, sb.toString()).showAndWait();
	});

	// --- Review button ---
	reviewButton.setOnAction(e -> {
	    try {
		if (currentAttempt == null) {
		    currentAttempt = new ReviewAttempt(Quality.EASY, 0, false, null, null);
		    reviewRepo.save("review:" + currentAttempt.getId(), currentAttempt.toString());
		    reviewRepo.saveAllToFile();

		    reviewStatus.setText("Review started...");
		} else {
		    currentAttempt.setEndedAt(LocalDateTime.now());
		    reviewRepo.save("review:" + currentAttempt.getId(), currentAttempt.toString());
		    reviewRepo.saveAllToFile();
		    currentAttempt = null;

		    reviewStatus.setText("Review ended...");
		}

		// Clear after 5 seconds
		PauseTransition pause = new PauseTransition(Duration.seconds(5));
		pause.setOnFinished(ev -> reviewStatus.setText(""));
		pause.play();

	    } catch (IOException ex) {
		System.err.println("Failed to save review: " + ex.getMessage());
	    }
	});

                // --- Layout ---
        BorderPane root = new BorderPane();

        // Navigation + Save row
        Button prevButton = new Button("◀");
        Button nextButton = new Button("▶");
        HBox notesButtons = new HBox(10, prevButton, saveCardButton, removeCardButton, nextButton);
		root.setBottom(notesButtons);

        root.setCenter(cardEditor);
        root.setLeft(buttonColumn);

        // --- Navigation handlers ---
        prevButton.setOnAction(e -> {
            if (currentDeck == null || currentDeck.getCards().isEmpty()) return;
            if (currentCardIndex <= 0) currentCardIndex = currentDeck.getCards().size() - 1;
            else currentCardIndex--;
            currentCard = currentDeck.getCards().get(currentCardIndex);
            frontArea.setText(currentCard.getFrontText());
            backArea.setText(currentCard.getBackText());
        });

        nextButton.setOnAction(e -> {
            if (currentDeck == null || currentDeck.getCards().isEmpty()) return;
            currentCardIndex = (currentCardIndex + 1) % currentDeck.getCards().size();
            currentCard = currentDeck.getCards().get(currentCardIndex);
            frontArea.setText(currentCard.getFrontText());
            backArea.setText(currentCard.getBackText());
        });

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Top Scrum Flash Card App");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(250);
        primaryStage.setMinHeight(400);
    }

    // --- Helper method to open or create a user ---
    private void showUserDialog() {
	TextInputDialog userDialog = new TextInputDialog();
	userDialog.setTitle("Open or Create User");
	userDialog.setHeaderText("Enter your name");
	userDialog.setContentText("User name:");

	userDialog.showAndWait().ifPresent(name -> {
	    if (currentUser == null || !name.equalsIgnoreCase(currentUser.getName())) {
		User u = new User(name, "");
		currentUser = u;
		userRepo.save("user:" + u.getId(), u.toString());
	    }

	    new Alert(Alert.AlertType.INFORMATION,
		"User ready: " + currentUser.getName()).showAndWait();
	});
    }

    @Override
    public void stop() {
	try {
	    userRepo.saveAllToFile();
	    deckRepo.saveAllToFile();
	    cardRepo.saveAllToFile();
	    reviewRepo.saveAllToFile(); // only if you added reviews
	} catch (Exception ex) {
	    System.err.println("Failed to save data: " + ex.getMessage());
	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
