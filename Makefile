# Compiler and runtime
JAVAC = javac
JAVA  = java

# Path to JavaFX SDK (adjust if yours is elsewhere)
JFXPATH = /opt/javafx-sdk-25.0.1/lib

# Main class (use fully qualified name if in a package)
MAIN = MainApp

# Sources and classes
SOURCES = $(wildcard *.java)
CLASSES = $(SOURCES:.java=.class)

# Default target: compile everything
all: $(CLASSES)

# Compile rule with JavaFX module path
%.class: %.java
	$(JAVAC) --module-path $(JFXPATH) \
	         --add-modules javafx.controls,javafx.fxml,javafx.graphics \
	         $<

# Run target with JavaFX flags
run: all
	$(JAVA) --module-path $(JFXPATH) \
	        --add-modules javafx.controls,javafx.fxml,javafx.graphics \
	        --enable-native-access=javafx.graphics \
	        $(MAIN)

# Clean up
clean:
	rm -f *.class
