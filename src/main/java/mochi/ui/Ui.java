package mochi.ui;

import java.util.List;
import java.util.Scanner;

import mochi.MochiException;
import mochi.task.Task;
import mochi.task.TaskList;

/**
 * Handles all console input and output for Mochi.
 */
public class Ui {
    private static final String SEPARATOR =
            "____________________________________________________________";
    private static final String BANNER = " __  __            _     _ \n"
            + "|  \\/  | ___   ___| |__ (_)\n"
            + "| |\\/| |/ _ \\ / __| '_ \\| |\n"
            + "| |  | | (_) | (__| | | | |\n"
            + "|_|  |_|\\___/ \\___|_| |_|_|\n";

    private final Scanner scanner;

    /**
     * Creates a console user interface that reads from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the next command entered by the user.
     *
     * @return The trimmed command text.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Shows Mochi's greeting and banner.
     */
    public void showWelcome() {
        System.out.println(SEPARATOR);
        System.out.print(BANNER);
        System.out.println("Hello! I'm Mochi.");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);
    }

    /**
     * Shows a separator between responses.
     */
    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    /**
     * Shows a response followed by a separator.
     *
     * @param response Response to display.
     */
    public void showResponse(String response) {
        System.out.println(response);
        showSeparator();
    }

    /**
     * Returns confirmation that a task was added.
     *
     * @param task Task that was added.
     * @param taskCount Number of tasks after the addition.
     * @return Confirmation of the addition.
     */
    public String getTaskAddedResponse(Task task, int taskCount) {
        return "Got it. I've added this task:" + System.lineSeparator()
                + "  " + task + System.lineSeparator()
                + "Now you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns confirmation that a task was deleted.
     *
     * @param task Task that was deleted.
     * @param taskCount Number of tasks after the deletion.
     * @return Confirmation of the deletion.
     */
    public String getTaskDeletedResponse(Task task, int taskCount) {
        return "Noted. I've removed this task:" + System.lineSeparator()
                + "  " + task + System.lineSeparator()
                + "Now you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns confirmation that a task was marked complete.
     *
     * @param task Task that was marked.
     * @return Confirmation of the status change.
     */
    public String getTaskMarkedResponse(Task task) {
        return "Nice! I've marked this task as done:" + System.lineSeparator()
                + "  " + task;
    }

    /**
     * Returns confirmation that a task was marked incomplete.
     *
     * @param task Task that was unmarked.
     * @return Confirmation of the status change.
     */
    public String getTaskUnmarkedResponse(Task task) {
        return "OK, I've marked this task as not done yet:" + System.lineSeparator()
                + "  " + task;
    }

    /**
     * Returns every task with its one-based task number.
     *
     * @param tasks Tasks to display.
     * @return Formatted task list.
     */
    public String getTaskListResponse(TaskList tasks) {
        StringBuilder response = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            response.append(System.lineSeparator())
                    .append(i + 1)
                    .append(".")
                    .append(tasks.getTasks().get(i));
        }
        return response.toString();
    }

    /**
     * Returns all tasks that match a search keyword.
     *
     * @param matchingTasks Tasks whose descriptions contain the keyword.
     * @return Formatted list of matching tasks.
     */
    public String getMatchingTasksResponse(List<Task> matchingTasks) {
        StringBuilder response = new StringBuilder(
                "Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            response.append(System.lineSeparator())
                    .append(i + 1)
                    .append(".")
                    .append(matchingTasks.get(i));
        }
        return response.toString();
    }

    /**
     * Returns Mochi's farewell message.
     *
     * @return Farewell message.
     */
    public String getGoodbyeResponse() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns an invalid-command error.
     *
     * @param error Error to display.
     * @return Error message.
     */
    public String getErrorResponse(MochiException error) {
        return error.toString();
    }

    /**
     * Shows a message when the data file cannot be loaded.
     */
    public void showLoadingError() {
        System.out.println(
                "OOPS!!! I couldn't read the data file. Starting with an empty task list.");
    }

    /**
     * Shows a warning about one malformed line in the data file.
     *
     * @param warning Warning text to display.
     */
    public void showLoadingWarning(String warning) {
        System.out.println("WARNING: " + warning);
    }

    /**
     * Returns a message when the task list cannot be saved.
     *
     * @return Saving error message.
     */
    public String getSavingErrorResponse() {
        return "OOPS!!! I couldn't save the task list to the data file.";
    }
}
