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

        // --- Buttons for actions ---
        Button userButton = new Button("Users");
        Button deckButton = new Button("Decks");
        Button cardButton = new Button("Cards");
        Button folderButton = new Button("Folder");
        Button reviewButton = new Button("Review");
        Button deviceButton = new Button("Device");

        Button[] buttons = {userButton, deckButton, cardButton, folderButton, reviewButton, deviceButton};

        // --- NEW: Device detection for platform-aware scaling ---
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
                baseFontSize = 14; // slightly larger for Retina displays
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

        // --- Review Status label ---
        Label reviewStatus = new Label();
        reviewStatus.setStyle("-fx-font-style: italic; -fx-text-fill: darkred;");

        // --- User creation ---
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

            new Alert(Alert.AlertType.INFORMATION,
                "Deck published: " + deck.getTitle()).showAndWait();
        });

        // --- Card creation ---
        cardButton.setOnAction(e -> {
            Card card = new Card(UUID.randomUUID(), "What is JVM?", "Java Virtual Machine",
                "Executes Java bytecode", LocalDateTime.now());
            card.setBackText("Executes Java bytecode");
            localRepo.save("card:" + card.getId(), card.toString());

            new Alert(Alert.AlertType.INFORMATION,
                "Card updated: " + card.getFrontText()).showAndWait();
        });

        // --- Folder management ---
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

        // --- Review Attempt ---
        reviewButton.setOnAction(e -> {
            if (currentAttempt == null) {
                currentAttempt = new ReviewAttempt(UUID.randomUUID(), LocalDateTime.now(),
                    null, Quality.EASY, 0, false);
                localRepo.save("review:" + currentAttempt.getId(), currentAttempt.toString());
                reviewStatus.setText("Review in progress...");
                reviewButton.setText("End Review");
            } else {
                currentAttempt.setEndedAt(LocalDateTime.now());
                localRepo.save("review:" + currentAttempt.getId(), currentAttempt.toString());
                reviewStatus.setText("Review ended.");
                currentAttempt = null;
                reviewButton.setText("Review Attempt");
            }
        });

        // --- Device detection ---
        deviceButton.setOnAction(e -> {
            Device d = new Device();
            d.updateLastSeen();
            localRepo.save("device:" + d.getId(), d.toString());
            new Alert(Alert.AlertType.INFORMATION,
                "Device detected: " + d.getPlatform()).showAndWait();
        });

        // --- Layout ---
        VBox buttonColumn = new VBox(10,
            userButton, deckButton, cardButton, folderButton,
            reviewButton, deviceButton);

        // Column width constraints
        buttonColumn.setPrefWidth(110);   // preferred width
        buttonColumn.setMaxWidth(220);    // cap maximum width
        buttonColumn.setMinWidth(90);    // reduce minimum left border

        BorderPane root = new BorderPane();
        root.setCenter(notesArea);
        root.setLeft(buttonColumn);
        root.setBottom(reviewStatus);

        Scene scene = new Scene(root, 600, 300);
        primaryStage.setTitle("Top Scrum Flash Card App");

        // Prevent shrinking below button size
        primaryStage.setMinWidth(250);
        primaryStage.setMinHeight(350);
        
        // --- Apply initial style and max size ---
        // --- Initial button style tied to column width ---
        for (Button b : buttons) {
            b.setStyle("-fx-font-weight: bold; -fx-font-size: " + baseFontSize + "px; "
                     + "-fx-padding: " + baseButtonPadding + "px " + (baseButtonPadding * 2) + "px;");
            b.setPrefWidth(buttonColumn.getPrefWidth());   // tie to column width
            b.setMaxWidth(Double.MAX_VALUE);               // allow expansion within VBox
            b.setPrefHeight(50);
            b.setMaxHeight(60);
            b.setWrapText(true);
        }
        // --- Responsive scaling with shared width for column and buttons ---
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();

            // Gentler scale factor
            double scale = width / 700.0;

            // Clamp font size between 12–16px
            double fontSize = baseFontSize * scale;
            fontSize = Math.max(12, Math.min(fontSize, 16));

            // Fixed smaller padding
            double padV = 5;
            double padH = 10;

            // Single shared width value for column and buttons
            double sharedWidth = Math.max(80, Math.min(150, 100 * scale));

            // Apply to column
            buttonColumn.setPrefWidth(sharedWidth);

            // Apply to all buttons
            for (Button b : buttons) {
                b.setStyle("-fx-font-weight: bold; -fx-font-size: " + fontSize + "px; "
                         + "-fx-padding: " + padV + "px " + padH + "px;");
                b.setPrefWidth(sharedWidth);
                b.setWrapText(true);
            }
        });
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
