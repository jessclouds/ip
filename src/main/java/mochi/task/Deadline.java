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
     * @param description description of the deadline
     * @param by date and time by which the task should be completed
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Converts this deadline into the line format used in the data file.
     *
     * @return serialized deadline
     */
    @Override
    public String toDataString() {
        return formatDataString("D") + " | " + DateTimeUtil.formatForStorage(by);
    }

    /**
     * Returns this deadline in its user-facing display format.
     *
     * @return formatted deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + String.format(" (by: %s)", DateTimeUtil.formatForDisplay(by));
    }
}
