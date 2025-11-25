import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;
import java.time.LocalDateTime;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Writing space
        TextArea outputArea = new TextArea();
        outputArea.setEditable(true);

        // Common button style (larger font + padding)
        String buttonStyle = "-fx-font-weight: bold;-fx-font-size: 12px; -fx-padding: 15px 30px;";

        // Buttons
        Button userButton = new Button("Create User");
        Button deckButton = new Button("Publish Deck");
        Button cardButton = new Button("Update Card");
        Button folderButton = new Button("Rename Folder");
        Button reviewButton = new Button("End Review Attempt");
        Button deviceButton = new Button("Rename Device");

        // Apply style to all buttons
        userButton.setStyle(buttonStyle);
        deckButton.setStyle(buttonStyle);
        cardButton.setStyle(buttonStyle);
        folderButton.setStyle(buttonStyle);
        reviewButton.setStyle(buttonStyle);
        deviceButton.setStyle(buttonStyle);

        // Button actions
        userButton.setOnAction(e -> {
            User user = new User(
                UUID.randomUUID(),
                "Sean",
                "sean@example.com",
                LocalDateTime.now(),
                LocalDateTime.now(),
                false, true, true, false
            );
            user.promoteToPowerUser();
            outputArea.appendText("Created User:\n" + user + "\n\n");
        });

        deckButton.setOnAction(e -> {
            Deck deck = new Deck(
                UUID.randomUUID(),
                "Java Basics",
                "Introductory deck",
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
            );
            deck.publish();
            outputArea.appendText("Published Deck:\n" + deck + "\n\n");
        });

        cardButton.setOnAction(e -> {
            Card card = new Card(
                UUID.randomUUID(),
                "What is JVM?",
                "Java Virtual Machine",
                "Executes Java bytecode",
                LocalDateTime.now()
            );
            card.setBackText("Executes Java bytecode");
            outputArea.appendText("Updated Card:\n" + card + "\n\n");
        });

        folderButton.setOnAction(e -> {
            Folder folder = new Folder(
                UUID.randomUUID(),
                "Programming Decks",
                LocalDateTime.now(),
                LocalDateTime.now()
            );
            folder.setName("Updated Folder Name");
            outputArea.appendText("Renamed Folder:\n" + folder + "\n\n");
        });

        reviewButton.setOnAction(e -> {
            ReviewAttempt attempt = new ReviewAttempt(
                UUID.randomUUID(),          // id
                LocalDateTime.now(),        // timestamp
                null,                       // endedAt (set later)
                Quality.EASY,               // use actual enum constant
                1500,                       // responseMs
                true                        // correct
            );
            attempt.setEndedAt(LocalDateTime.now().plusMinutes(30));
            outputArea.appendText("Ended Review Attempt:\n" + attempt + "\n\n");
        });

        deviceButton.setOnAction(e -> {
            Device device = new Device(
                UUID.randomUUID(),
                "Sean's Laptop",
                LocalDateTime.now()
            );
            device.setPlatform("Sean's Updated Laptop");
            outputArea.appendText("Renamed Device:\n" + device + "\n\n");
        });

        // Layout
        // Column of buttons on the side
        VBox buttonColumn = new VBox(10, userButton, deckButton, cardButton, folderButton, reviewButton, deviceButton);

        BorderPane root = new BorderPane();
        root.setCenter(outputArea);
        root.setLeft(buttonColumn); 

        // Scene
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setTitle("Domain Tester");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
