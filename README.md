# FlashCardApp – UML Flow Branch

This branch (`umlFlow`) is dedicated to implementing **class associations and flow** as demonstrated by the UML diagram.  
It focuses on:

- **Domain modeling**: mapping classes (`User`, `Folder`, `Deck`, `Card`, `ReviewAttempt`, `Quality`) to their relationships.
- **Association fidelity**: ensuring constructors, references, and flows match the UML design.
- **Traceability**: keeping code aligned with the diagram for clarity and maintainability.
- **Future integration**: preparing the groundwork for persistence, UI refinements, and runtime features.

## Compilation Requirements
To compile successfully, you must include:
- **JavaFX libraries** (for the UI components)
- **Gson library** (for JSON persistence and serialization)

Example compile command:
```bash
javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp gson-2.10.1.jar:. *.java

