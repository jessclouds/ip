package mochi.task;

import java.time.LocalDateTime;

import mochi.DateTimeUtil;

/**
 * Represents a task that must be completed by a specific deadline.
 */

public class Deadline extends Task {
    private final LocalDateTime by;

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
