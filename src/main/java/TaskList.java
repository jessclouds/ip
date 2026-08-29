import java.util.ArrayList;
import java.util.List;

/**
 * Owns the task collection and provides operations that use user-facing task numbers.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    /** Deletes and returns the task with the given one-based number. */
    public Task delete(int taskNumber) throws MochiException {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    /** Marks and returns the task with the given one-based number. */
    public Task mark(int taskNumber) throws MochiException {
        Task task = get(taskNumber);
        task.mark();
        return task;
    }

    /** Unmarks and returns the task with the given one-based number. */
    public Task unmark(int taskNumber) throws MochiException {
        Task task = get(taskNumber);
        task.unmark();
        return task;
    }

    /** Returns the task with the given one-based number. */
    public Task get(int taskNumber) throws MochiException {
        validateTaskNumber(taskNumber);
        return tasks.get(taskNumber - 1);
    }

    public int size() {
        return tasks.size();
    }

    /** Returns a read-only snapshot for display or storage. */
    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    private void validateTaskNumber(int taskNumber) throws MochiException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new MochiException("There is no task numbered " + taskNumber + ".");
        }
    }
}
