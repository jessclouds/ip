package mochi;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests command execution across the parser, task list, and storage components.
 */
public class MochiTest {
    @TempDir
    private Path tempDirectory;

    @Test
    public void getResponse_taskWorkflow_returnsResponsesAndPersistsChanges() throws IOException {
        Path filePath = tempDirectory.resolve("data").resolve("duke.txt");
        Mochi mochi = new Mochi(filePath);

        String addResponse = mochi.getResponse("todo read book");
        String markResponse = mochi.getResponse("mark 1");
        String listResponse = mochi.getResponse("list");
        String findResponse = mochi.getResponse("find book");
        String unmarkResponse = mochi.getResponse("unmark 1");
        String deleteResponse = mochi.getResponse("delete 1");

        assertAll(
                () -> assertTrue(addResponse.contains("[T][ ] read book")),
                () -> assertTrue(markResponse.contains("[T][X] read book")),
                () -> assertTrue(listResponse.contains("1.[T][X] read book")),
                () -> assertTrue(findResponse.contains("1.[T][X] read book")),
                () -> assertTrue(unmarkResponse.contains("[T][ ] read book")),
                () -> assertTrue(deleteResponse.contains("[T][ ] read book")),
                () -> assertTrue(Files.readAllLines(filePath, StandardCharsets.UTF_8).isEmpty())
        );
    }

    @Test
    public void constructor_existingData_commandsUseLoadedTasks() throws IOException {
        Path filePath = tempDirectory.resolve("duke.txt");
        Files.write(filePath, List.of("T | 1 | loaded task"), StandardCharsets.UTF_8);

        Mochi mochi = new Mochi(filePath);

        assertTrue(mochi.getResponse("list").contains("1.[T][X] loaded task"));
    }

    @Test
    public void getResponse_invalidCommand_returnsErrorWithoutExiting() {
        Mochi mochi = new Mochi(tempDirectory.resolve("duke.txt"));

        String response = mochi.getResponse("unknown command");

        assertAll(
                () -> assertEquals(
                        "OOPS!!! I'm sorry, but I don't know what that means :-(", response),
                () -> assertFalse(mochi.isExitRequested())
        );
    }

    @Test
    public void getResponse_byeCommand_requestsExit() {
        Mochi mochi = new Mochi(tempDirectory.resolve("duke.txt"));

        String response = mochi.getResponse("bye");

        assertAll(
                () -> assertEquals("Bye. Hope to see you again soon!", response),
                () -> assertTrue(mochi.isExitRequested())
        );
    }

    @Test
    public void getResponse_storageCannotSave_returnsWarningAndSuccessfulResponse()
            throws IOException {
        Path blockingFile = tempDirectory.resolve("not-a-directory");
        Files.writeString(blockingFile, "blocking file", StandardCharsets.UTF_8);
        Mochi mochi = new Mochi(blockingFile.resolve("duke.txt"));

        String response = mochi.getResponse("todo read book");

        assertAll(
                () -> assertTrue(response.startsWith(
                        "OOPS!!! I couldn't save the task list to the data file."
                                + System.lineSeparator())),
                () -> assertTrue(response.contains("[T][ ] read book"))
        );
    }
}
