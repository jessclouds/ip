package mochi.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import mochi.DateTimeUtil;
import mochi.MochiException;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Task;
import mochi.task.Todo;

/**
 * Converts raw user input into commands that Mochi can execute.
 */
public final class Parser {
    /** Identifies the action represented by a parsed command. */
    public enum CommandType {
        ADD, DELETE, MARK, UNMARK, LIST, BYE
    }

    /** Contains the action and argument produced by parsing one user command. */
    public static final class Command {
        private final CommandType type;
        private final Task task;
        private final int taskNumber;

        private Command(CommandType type, Task task, int taskNumber) {
            this.type = type;
            this.task = task;
            this.taskNumber = taskNumber;
        }

        public CommandType getType() {
            return type;
        }

        public Task getTask() {
            return task;
        }

        public int getTaskNumber() {
            return taskNumber;
        }
    }

    private Parser() {
    }

    /**
     * Parses one line of user input.
     *
     * @param input raw input from the user
     * @return a command containing the requested action and argument
     * @throws MochiException if the command or its arguments are invalid
     */
    public static Command parse(String input) throws MochiException {
        String command = input.trim();
        if (command.equals("bye")) {
            return new Command(CommandType.BYE, null, 0);
        }
        if (command.equals("list")) {
            return new Command(CommandType.LIST, null, 0);
        }

        String[] words = command.split("\\s+", 2);
        String commandWord = words[0];
        if (words.length < 2) {
            throw missingArgumentException(commandWord);
        }

        String arguments = words[1].trim();
        switch (commandWord) {
        case "mark":
            return new Command(CommandType.MARK, null, parseTaskNumber(arguments));
        case "unmark":
            return new Command(CommandType.UNMARK, null, parseTaskNumber(arguments));
        case "delete":
            return new Command(CommandType.DELETE, null, parseTaskNumber(arguments));
        case "todo":
            return new Command(CommandType.ADD, new Todo(arguments), 0);
        case "deadline":
            return new Command(CommandType.ADD, parseDeadline(arguments), 0);
        case "event":
            return new Command(CommandType.ADD, parseEvent(arguments), 0);
        default:
            throw unknownCommandException();
        }
    }

    private static int parseTaskNumber(String argument) throws MochiException {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            throw new MochiException("The task number must be a whole number.");
        }
    }

    private static Deadline parseDeadline(String details) throws MochiException {
        if (!details.contains(" /by ")) {
            throw new MochiException("Use this format: deadline DESCRIPTION /by DATE.");
        }
        String[] parts = details.split(" /by ", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new MochiException("A deadline needs both a description and a date.");
        }

        try {
            return new Deadline(description, DateTimeUtil.parse(by));
        } catch (DateTimeParseException e) {
            throw invalidDateTimeException();
        }
    }

    private static Event parseEvent(String details) throws MochiException {
        if (!details.contains(" /from ")) {
            throw new MochiException("Use this format: event DESCRIPTION /from START /to END.");
        }
        String[] fromParts = details.split(" /from ", 2);
        if (!fromParts[1].contains(" /to ")) {
            throw new MochiException("Use this format: event DESCRIPTION /from START /to END.");
        }

        String[] toParts = fromParts[1].split(" /to ", 2);
        String description = fromParts[0].trim();
        String from = toParts[0].trim();
        String to = toParts[1].trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new MochiException("An event needs a description, start, and end.");
        }

        try {
            LocalDateTime start = DateTimeUtil.parse(from);
            LocalDateTime end = DateTimeUtil.parse(to);
            return new Event(description, start, end);
        } catch (DateTimeParseException e) {
            throw invalidDateTimeException();
        } catch (IllegalArgumentException e) {
            throw new MochiException("An event must end after it starts.");
        }
    }

    private static MochiException missingArgumentException(String commandWord) {
        if (commandWord.equals("todo")
                || commandWord.equals("deadline")
                || commandWord.equals("event")) {
            return new MochiException(
                    "The description of a " + commandWord + " cannot be empty.");
        }
        if (commandWord.equals("mark")
                || commandWord.equals("unmark")
                || commandWord.equals("delete")) {
            return new MochiException("Please specify a task number to " + commandWord + ".");
        }
        return unknownCommandException();
    }

    private static MochiException invalidDateTimeException() {
        return new MochiException(
                "Use a valid date and time in the format "
                        + DateTimeUtil.INPUT_FORMAT_DESCRIPTION + ".");
    }

    private static MochiException unknownCommandException() {
        return new MochiException("I'm sorry, but I don't know what that means :-(");
    }
}
