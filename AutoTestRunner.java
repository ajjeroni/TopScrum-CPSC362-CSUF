import java.io.*;
import java.time.LocalDateTime;

public class AutoTestRunner {
    public static void main(String[] args) throws Exception {
        File dir = new File("."); // current directory with compiled .class files
        File[] files = dir.listFiles((d, name) -> name.endsWith("Test.class"));

        if (files == null) {
            System.out.println("No test classes found.");
            return;
        }

        // Ensure logs folder exists
        File logsDir = new File("TestLogs");
        if (!logsDir.exists()) {
            logsDir.mkdirs(); // create logs/ automatically
        }

        for (File f : files) {
            String className = f.getName().replace(".class", "");
            runTestAndLog(className, logsDir);
        }
    }

    private static void runTestAndLog(String className, File logsDir) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("java", className);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.lineSeparator());
        }
        process.waitFor();

        // Write to logs/ClassName.txt
        File logFile = new File(logsDir, className + ".txt");
        try (PrintWriter out = new PrintWriter(new FileWriter(logFile, true))) {
            out.println("=== Run at " + LocalDateTime.now() + " ===");
            out.print(output.toString());
            out.println();
        }

        System.out.println("Logged output of " + className + " to " + logFile.getPath());
    }
}
