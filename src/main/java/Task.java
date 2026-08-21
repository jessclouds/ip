/**
 * Represents a task and whether it has been completed.
 */
public class Task {
    /**
     * Represents the completion status of a task.
     */
    private enum TaskStatus {
        NOT_DONE(" "),
        DONE("X");

        private final String icon;

        TaskStatus(String icon) {
            this.icon = icon;
        }

        public String getIcon() {
            return icon;
        }
    }

    protected String description;
    private TaskStatus status;

    public Task(String description) {
        this.description = description;
        this.status = TaskStatus.NOT_DONE;
    }

    public String getStatusIcon() {
        return status.getIcon();
    }

    public String getDescription() {
        return description;
    }

    public void mark() {
        status = TaskStatus.DONE;
    }

    public void unmark() {
        status = TaskStatus.NOT_DONE;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
