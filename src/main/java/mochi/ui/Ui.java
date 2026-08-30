package mochi.ui;

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
     * Shows confirmation that a task was added.
     *
     * @param task Task that was added.
     * @param taskCount Number of tasks after the addition.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showSeparator();
    }

    /**
     * Shows confirmation that a task was deleted.
     *
     * @param task Task that was deleted.
     * @param taskCount Number of tasks after the deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showSeparator();
    }

    /**
     * Shows confirmation that a task was marked complete.
     *
     * @param task Task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        showSeparator();
    }

    /**
     * Shows confirmation that a task was marked incomplete.
     *
     * @param task Task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        showSeparator();
    }

    /**
     * Shows every task with its one-based task number.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.getTasks().get(i));
        }
        showSeparator();
    }

    /**
     * Shows Mochi's farewell message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        showSeparator();
    }

    /**
     * Shows an invalid-command error.
     *
     * @param error Error to display.
     */
    public void showError(MochiException error) {
        System.out.println(error);
        showSeparator();
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
     * Shows a message when the task list cannot be saved.
     */
    public void showSavingError() {
        System.out.println("OOPS!!! I couldn't save the task list to the data file.");
    }
}
