package mochi.task;
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

    /**
     * Converts this task into the line format used in the data file.
     *
     * @return the serialized task
     */
    public String toDataString() {
        return formatDataString("T");
    }

    /**
     * Formats the common fields shared by all saved task types.
     *
     * @param taskType single-letter code identifying the task type
     * @return the task type, completion status, and description
     */
    protected String formatDataString(String taskType) {
        return taskType + " | " + (status == TaskStatus.DONE ? "1" : "0")
                + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
