public class FolderTest {
    public static void main(String[] args) {
        // Create root folder
        Folder root = new Folder("Root");

        // Create subfolders
        Folder projects = new Folder("Projects");
        Folder personal = new Folder("Personal");

        // Add subfolders to root
        root.addChild(projects);
        root.addChild(personal);

        // Create nested subfolder
        Folder javaProject = new Folder("JavaProject");
        projects.addChild(javaProject);

        // Show hierarchy
        System.out.println("Root folder: " + root.getName());
        for (Folder sub : root.getChildren()) {
            System.out.println(" - Subfolder: " + sub.getName());
        }

        System.out.println("Projects children: " + projects.getChildren().size()); // 1
        System.out.println("JavaProject parent: " + javaProject.getParent().getName()); // Projects

        // Remove a subfolder
        root.removeChild(personal);

        System.out.println("\nAfter removing 'Personal':");
        System.out.println("Root children: " + root.getChildren().size()); // 1
        System.out.println("Personal parent: " + personal.getParent()); // null

        // Clear all children
        root.clearChildren();
        System.out.println("\nAfter clearing all children:");
        System.out.println("Root children: " + root.getChildren().size()); // 0
    }
}
