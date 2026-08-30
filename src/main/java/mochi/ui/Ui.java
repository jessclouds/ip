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

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showWelcome() {
        System.out.println(SEPARATOR);
        System.out.print(BANNER);
        System.out.println("Hello! I'm Mochi.");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);
    }

    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showSeparator();
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showSeparator();
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        showSeparator();
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        showSeparator();
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.getTasks().get(i));
        }
        showSeparator();
    }

    /**
     * Shows all tasks that match a search keyword.
     *
     * @param matchingTasks Tasks whose descriptions contain the keyword.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println((i + 1) + "." + matchingTasks.get(i));
        }
        showSeparator();
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        showSeparator();
    }

    public void showError(MochiException error) {
        System.out.println(error);
        showSeparator();
    }

    public void showLoadingError() {
        System.out.println(
                "OOPS!!! I couldn't read the data file. Starting with an empty task list.");
    }

    public void showLoadingWarning(String warning) {
        System.out.println("WARNING: " + warning);
    }

    public void showSavingError() {
        System.out.println("OOPS!!! I couldn't save the task list to the data file.");
    }
}
