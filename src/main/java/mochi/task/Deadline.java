package mochi.task;

import java.time.LocalDateTime;

import mochi.DateTimeUtil;

/**
 * Represents a task that must be completed by a specific deadline.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Creates a deadline with the given description and due date-time.
     *
     * @param description Description of the deadline.
     * @param by Date and time by which the task should be completed.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toDataString() {
        return formatDataString("D") + " | " + DateTimeUtil.formatForStorage(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + String.format(" (by: %s)", DateTimeUtil.formatForDisplay(by));
    }
}
