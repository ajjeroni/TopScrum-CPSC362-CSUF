import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.UUID;
import java.time.LocalDateTime;

public class MainApp extends Application {

    private ReviewAttempt currentAttempt;
    private LocalRepository localRepo = new LocalRepository();

    @Override
    public void start(Stage primaryStage) {
        try {
            localRepo.loadAllFromFile();
        } catch (Exception ex) {
            System.err.println("No saved data loaded: " + ex.getMessage());
        }

        // --- Notes area (user can type freely here) ---
        TextArea notesArea = new TextArea();
        notesArea.setEditable(true);
        notesArea.setPromptText("Write your notes here...");

        String buttonStyle = "-fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 15px 30px;";

        // --- Buttons for actions ---
        Button userButton = new Button("Create User");
        Button deckButton = new Button("Publish Deck");
        Button cardButton = new Button("Update Card");
        Button folderButton = new Button("Manage Folder");
        Button reviewButton = new Button("Review Attempt");
        Button deviceButton = new Button("Detect Device");

        for (Button b : new Button[]{userButton, deckButton, cardButton, folderButton, reviewButton, deviceButton}) {
            b.setStyle(buttonStyle);
        }

        // --- NEW: Status label for review state (non-blocking) ---
        Label reviewStatus = new Label();
        reviewStatus.setStyle("-fx-font-style: italic; -fx-text-fill: darkred;");

        // --- User creation (modal dialog is fine here) ---
        userButton.setOnAction(e -> {
            Dialog<User> dialog = new Dialog<>();
            dialog.setTitle("Create User");
            dialog.setHeaderText("Enter User Information");

            ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

            TextField nameField = new TextField();
            TextField emailField = new TextField();
            GridPane grid = new GridPane();
            grid.setHgap(10); grid.setVgap(10);
            grid.add(new Label("Name:"), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("Email:"), 0, 1);
            grid.add(emailField, 1, 1);
            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(button -> {
                if (button == createButtonType) {
                    return new User(UUID.randomUUID(), nameField.getText(), emailField.getText(),
                        LocalDateTime.now(), LocalDateTime.now(), false, true, true, false);
                }
                return null;
            });

            dialog.showAndWait().ifPresent(user -> {
                user.promoteToPowerUser();
                localRepo.save("user:" + user.getId(), user.toString());
                new Alert(Alert.AlertType.INFORMATION, user.toString()).showAndWait();
            });
        });

        // --- Deck creation ---
        deckButton.setOnAction(e -> {
            Deck deck = new Deck(UUID.randomUUID(), "Java Basics", "Introductory deck",
                false, LocalDateTime.now(), LocalDateTime.now());
            deck.publish();
            localRepo.save("deck:" + deck.getId(), deck.toString());

            // Show only the deck title
            new Alert(Alert.AlertType.INFORMATION,
                "Deck published: " + deck.getTitle()).showAndWait();
        });

        // --- Card creation ---
        cardButton.setOnAction(e -> {
            Card card = new Card(UUID.randomUUID(), "What is JVM?", "Java Virtual Machine",
                "Executes Java bytecode", LocalDateTime.now());
            card.setBackText("Executes Java bytecode");
            localRepo.save("card:" + card.getId(), card.toString());

            // Show only the question text
            new Alert(Alert.AlertType.INFORMATION,
                "Card updated: " + card.getFrontText()).showAndWait();
        });

        // --- Folder management (modal dialog is fine here) ---
        folderButton.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Manage Folder");
            dialog.setHeaderText("Save or Recall a Folder");

            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            ButtonType recallButtonType = new ButtonType("Recall", ButtonBar.ButtonData.OTHER);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, recallButtonType, ButtonType.CANCEL);

            TextField nameField = new TextField();
            nameField.setPromptText("Folder Name");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.add(new Label("Name:"), 0, 0);
            grid.add(nameField, 1, 0);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(button -> button);

            dialog.showAndWait().ifPresent(button -> {
                String name = nameField.getText();
                if (button == saveButtonType) {
                    localRepo.save("folder:" + name, "Folder: " + name);
                    new Alert(Alert.AlertType.INFORMATION, "Folder saved: " + name).showAndWait();
                } else if (button == recallButtonType) {
                    String found = localRepo.find("folder:" + name);
                    if (found != null) {
                        new Alert(Alert.AlertType.INFORMATION, "Folder recalled: " + found).showAndWait();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "No folder found with name: " + name).showAndWait();
                    }
                }
            });
        });

        // --- Review Attempt (non-blocking, uses status label instead of Alert) ---
        reviewButton.setOnAction(e -> {
            if (currentAttempt == null) {
                currentAttempt = new ReviewAttempt(UUID.randomUUID(), LocalDateTime.now(),
                    null, Quality.EASY, 0, false);
                localRepo.save("review:" + currentAttempt.getId(), currentAttempt.toString());
                reviewStatus.setText("Review in progress..."); // update label instead of blocking popup
                reviewButton.setText("End Review");
            } else {
                currentAttempt.setEndedAt(LocalDateTime.now());
                localRepo.save("review:" + currentAttempt.getId(), currentAttempt.toString());
                reviewStatus.setText("Review ended."); // update label instead of blocking popup
                currentAttempt = null;
                reviewButton.setText("Review Attempt");
            }
        });

        // --- Device (auto-detects platform, modal popup is fine here) ---
        deviceButton.setOnAction(e -> {
            Device device = new Device(); // auto-detects platform
            device.updateLastSeen();
            localRepo.save("device:" + device.getId(), device.toString());

            // Show only the platform info
            new Alert(Alert.AlertType.INFORMATION,
                "Device detected: " + device.getPlatform()).showAndWait();
        });

        // --- Layout ---
        VBox buttonColumn = new VBox(10,
            userButton, deckButton, cardButton, folderButton,
            reviewButton, deviceButton);

        BorderPane root = new BorderPane();
        root.setCenter(notesArea);       // notes area in center
        root.setLeft(buttonColumn);      // buttons on the left
        root.setBottom(reviewStatus);    // review status label at bottom

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setTitle("Domain Tester");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            localRepo.saveAllToFile();
        } catch (Exception ex) {
            System.err.println("Failed to save data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
