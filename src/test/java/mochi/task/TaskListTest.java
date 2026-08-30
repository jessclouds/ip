package mochi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests operations on the task collection.
 */
public class TaskListTest {
    @Test
    public void find_keywordInDescriptions_returnsMatchesInOriginalOrder() {
        Todo readBook = new Todo("read book");
        Deadline returnBook = new Deadline(
                "return book", LocalDateTime.of(2026, 6, 6, 18, 0));
        Event meeting = new Event(
                "project meeting",
                LocalDateTime.of(2026, 8, 6, 14, 0),
                LocalDateTime.of(2026, 8, 6, 16, 0));
        TaskList tasks = new TaskList(List.of(readBook, returnBook, meeting));

        assertEquals(List.of(readBook, returnBook), tasks.find("book"));
    }

    @Test
    public void find_keywordAbsent_returnsEmptyList() {
        TaskList tasks = new TaskList(List.of(new Todo("read book")));

        assertTrue(tasks.find("meeting").isEmpty());
    }

    @Test
    public void find_keywordOnlyInFormattedDate_returnsEmptyList() {
        Deadline deadline = new Deadline(
                "return book", LocalDateTime.of(2026, 6, 6, 18, 0));
        TaskList tasks = new TaskList(List.of(deadline));

        assertTrue(tasks.find("Jun").isEmpty());
    }
}
