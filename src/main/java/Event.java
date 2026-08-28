import java.time.LocalDateTime;

/**
 * Represents a task occurring during a specified period.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        if (!to.isAfter(from)) {
            throw new IllegalArgumentException("event end must be after its start");
        }
        this.from = from;
        this.to = to;
    }

    @Override
    public String toDataString() {
        return formatDataString("E") + " | " + DateTimeUtil.formatForStorage(from)
                + " | " + DateTimeUtil.formatForStorage(to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + String.format(" (from: %s to: %s)",
                        DateTimeUtil.formatForDisplay(from), DateTimeUtil.formatForDisplay(to));
    }
}
