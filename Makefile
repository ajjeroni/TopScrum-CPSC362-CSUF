# Makefile for Java project

# Compiler
JAVAC = javac
JAVA  = java

# Main class (adjust if yours is in a package)
MAIN = MainApp

# Find all .java files in current directory
SOURCES = $(wildcard *.java)

# Replace .java with .class
CLASSES = $(SOURCES:.java=.class)

# Default target: build everything
all: $(CLASSES)

# Rule: how to build .class from .java
%.class: %.java
	$(JAVAC) $<

# Run the program
run: all
	$(JAVA) $(MAIN)

# Clean up compiled files
clean:
	rm -f *.class
