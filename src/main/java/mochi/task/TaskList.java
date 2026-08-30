package mochi.task;

import java.util.ArrayList;
import java.util.List;

import mochi.MochiException;

/**
 * Owns the task collection and provides operations that use user-facing task numbers.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing the supplied tasks.
     *
     * @param tasks Initial tasks to copy into the list.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes and returns the task with the given one-based number.
     *
     * @param taskNumber One-based position of the task.
     * @return The deleted task.
     * @throws MochiException If the task number is outside the list.
     */
    public Task delete(int taskNumber) throws MochiException {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Marks and returns the task with the given one-based number.
     *
     * @param taskNumber One-based position of the task.
     * @return The marked task.
     * @throws MochiException If the task number is outside the list.
     */
    public Task mark(int taskNumber) throws MochiException {
        Task task = get(taskNumber);
        task.mark();
        return task;
    }

    /**
     * Unmarks and returns the task with the given one-based number.
     *
     * @param taskNumber One-based position of the task.
     * @return The unmarked task.
     * @throws MochiException If the task number is outside the list.
     */
    public Task unmark(int taskNumber) throws MochiException {
        Task task = get(taskNumber);
        task.unmark();
        return task;
    }

    /**
     * Returns the task with the given one-based number.
     *
     * @param taskNumber One-based position of the task.
     * @return The selected task.
     * @throws MochiException If the task number is outside the list.
     */
    public Task get(int taskNumber) throws MochiException {
        validateTaskNumber(taskNumber);
        return tasks.get(taskNumber - 1);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The task count.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a read-only snapshot for display or storage.
     *
     * @return An immutable copy of the tasks.
     */
    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    private void validateTaskNumber(int taskNumber) throws MochiException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new MochiException("There is no task numbered " + taskNumber + ".");
        }
    }
}
