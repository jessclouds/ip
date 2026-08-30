package mochi.storage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Task;
import mochi.task.Todo;

/**
 * Tests persistence and reconstruction of task data.
 */
public class StorageTest {
    @TempDir
    private Path tempDirectory;

    @Test
    public void loadTasks_missingFile_returnsEmptyListWithoutWarnings() throws IOException {
        Storage storage = new Storage(tempDirectory.resolve("data").resolve("duke.txt"));

        ArrayList<Task> tasks = storage.loadTasks();

        assertAll(
                () -> assertTrue(tasks.isEmpty()),
                () -> assertTrue(storage.getLoadWarnings().isEmpty())
        );
    }

    @Test
    public void loadTasks_validFile_reconstructsTypesDetailsAndStatuses() throws IOException {
        Path filePath = tempDirectory.resolve("duke.txt");
        Files.write(filePath, List.of(
                "T | 1 | read book",
                "D | 0 | return book | 2026-06-06 1800",
                "E | 0 | meeting | 2026-08-06 1400 | 2026-08-06 1600"
        ), StandardCharsets.UTF_8);
        Storage storage = new Storage(filePath);

        ArrayList<Task> tasks = storage.loadTasks();

        assertAll(
                () -> assertEquals(3, tasks.size()),
                () -> assertInstanceOf(Todo.class, tasks.get(0)),
                () -> assertEquals("[T][X] read book", tasks.get(0).toString()),
                () -> assertInstanceOf(Deadline.class, tasks.get(1)),
                () -> assertEquals("[D][ ] return book (by: Jun 06 2026, 6:00 PM)",
                        tasks.get(1).toString()),
                () -> assertInstanceOf(Event.class, tasks.get(2)),
                () -> assertEquals(
                        "[E][ ] meeting (from: Aug 06 2026, 2:00 PM "
                                + "to: Aug 06 2026, 4:00 PM)",
                        tasks.get(2).toString()),
                () -> assertTrue(storage.getLoadWarnings().isEmpty())
        );
    }

    @Test
    public void loadTasks_partlyCorruptedFile_skipsInvalidLinesAndRecordsWarnings()
            throws IOException {
        Path filePath = tempDirectory.resolve("duke.txt");
        Files.write(filePath, List.of(
                "T | 0 | valid task",
                "X | 0 | unknown task",
                "D | 2 | invalid status | 2026-06-06 1800",
                "D | 0 | impossible date | 2026-02-30 1800",
                "E | 0 | backwards | 2026-08-06 1600 | 2026-08-06 1400",
                "",
                "D | 0 | valid deadline | 2026-06-06 1800"
        ), StandardCharsets.UTF_8);
        Storage storage = new Storage(filePath);

        ArrayList<Task> tasks = storage.loadTasks();

        assertAll(
                () -> assertEquals(2, tasks.size()),
                () -> assertEquals("[T][ ] valid task", tasks.get(0).toString()),
                () -> assertEquals(
                        "[D][ ] valid deadline (by: Jun 06 2026, 6:00 PM)",
                        tasks.get(1).toString()),
                () -> assertEquals(4, storage.getLoadWarnings().size())
        );
    }

    @Test
    public void loadTasks_calledAgain_clearsPreviousWarnings() throws IOException {
        Path filePath = tempDirectory.resolve("duke.txt");
        Files.writeString(filePath, "invalid line", StandardCharsets.UTF_8);
        Storage storage = new Storage(filePath);
        storage.loadTasks();
        assertEquals(1, storage.getLoadWarnings().size());

        Files.writeString(filePath, "T | 0 | valid task\n", StandardCharsets.UTF_8);
        storage.loadTasks();

        assertTrue(storage.getLoadWarnings().isEmpty());
    }
}
