# Compiler
JAVAC = javac
JAVA  = java

# Source files (all .java in current dir)
SOURCES = $(wildcard *.java)

# Class files
CLASSES = $(SOURCES:.java=.class)

# Default target: compile everything
all: $(CLASSES)

# Rule to compile .java to .class
%.class: %.java
	$(JAVAC) $<

# Clean up compiled files
clean:
	rm -f *.class
