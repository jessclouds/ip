import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads and saves the task list on the hard disk.
 */
public class Storage {
    private final Path filePath;
    private final ArrayList<String> loadWarnings = new ArrayList<>();

    /**
     * Creates a storage object that writes to the given file.
     *
     * @param filePath path of the data file
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the data file.
     *
     * @return tasks reconstructed from the saved lines
     * @throws IOException if the data file cannot be read
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        loadWarnings.clear();

        if (Files.notExists(filePath)) {
            return tasks;
        }

        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                continue;
            }

            try {
                tasks.add(parseTask(line));
            } catch (IllegalArgumentException e) {
                loadWarnings.add("Skipped invalid data on line " + (i + 1) + ": " + e.getMessage());
            }
        }

        return tasks;
    }

    /**
     * Returns warnings generated while loading malformed data lines.
     *
     * @return an unmodifiable copy of the warnings
     */
    public List<String> getLoadWarnings() {
        return List.copyOf(loadWarnings);
    }

    /**
     * Reconstructs one task after validating its saved fields.
     *
     * @param line one line from the data file
     * @return the reconstructed task
     * @throws IllegalArgumentException if the line has an invalid format
     */
    private Task parseTask(String line) {
        String[] fields = line.split(" \\| ", -1);
        if (fields.length < 3) {
            throw new IllegalArgumentException("not enough fields");
        }
        if (!fields[1].equals("0") && !fields[1].equals("1")) {
            throw new IllegalArgumentException("completion status must be 0 or 1");
        }

        int expectedFieldCount;
        switch (fields[0]) {
        case "T":
            expectedFieldCount = 3;
            break;
        case "D":
            expectedFieldCount = 4;
            break;
        case "E":
            expectedFieldCount = 5;
            break;
        default:
            throw new IllegalArgumentException("unknown task type '" + fields[0] + "'");
        }

        if (fields.length != expectedFieldCount) {
            throw new IllegalArgumentException(
                    "wrong number of fields for a " + fields[0] + " task");
        }
        for (int i = 2; i < fields.length; i++) {
            if (fields[i].isBlank()) {
                throw new IllegalArgumentException("task details cannot be empty");
            }
        }

        Task task;
        switch (fields[0]) {
        case "T":
            task = new Todo(fields[2]);
            break;
        case "D":
            task = new Deadline(fields[2], fields[3]);
            break;
        case "E":
            task = new Event(fields[2], fields[3], fields[4]);
            break;
        default:
            throw new AssertionError("Task type was validated before task creation");
        }

        if (fields[1].equals("1")) {
            task.mark();
        }
        return task;
    }

    /**
     * Replaces the data file with the current tasks, creating its directory if needed.
     *
     * @param tasks tasks to save
     * @throws IOException if the tasks cannot be written
     */
    public void saveTasks(List<Task> tasks) throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        List<String> taskLines = tasks.stream()
                .map(Task::toDataString)
                .toList();
        Files.write(filePath, taskLines, StandardCharsets.UTF_8);
    }
}
