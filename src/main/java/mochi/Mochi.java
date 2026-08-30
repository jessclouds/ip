package mochi;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import mochi.parser.Parser;
import mochi.storage.Storage;
import mochi.task.Task;
import mochi.task.TaskList;
import mochi.ui.Ui;

/**
 * Coordinates Mochi's user interface, parser, task list, and storage.
 */
public class Mochi {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final List<String> loadingWarnings;
    private final boolean loadingFailed;

    /** Creates Mochi and loads its saved tasks. */
    public Mochi(Path filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        TaskList loadedTasks;
        List<String> warnings;
        boolean loadFailed;
        try {
            loadedTasks = new TaskList(storage.loadTasks());
            warnings = storage.getLoadWarnings();
            loadFailed = false;
        } catch (IOException e) {
            loadedTasks = new TaskList();
            warnings = new ArrayList<>();
            loadFailed = true;
        }
        tasks = loadedTasks;
        loadingWarnings = warnings;
        loadingFailed = loadFailed;
    }

    /** Runs the command loop until the user exits. */
    public void run() {
        ui.showWelcome();
        showLoadingMessages();

        while (true) {
            String input = ui.readCommand();
            ui.showSeparator();

            try {
                Parser.Command command = Parser.parse(input);
                if (command.getType() == Parser.CommandType.BYE) {
                    ui.showGoodbye();
                    return;
                }
                execute(command);
            } catch (MochiException e) {
                ui.showError(e);
            }
        }
    }

    private void execute(Parser.Command command) throws MochiException {
        switch (command.getType()) {
        case LIST:
            ui.showTaskList(tasks);
            break;
        case ADD:
            tasks.add(command.getTask());
            saveTasks();
            ui.showTaskAdded(command.getTask(), tasks.size());
            break;
        case DELETE:
            Task deletedTask = tasks.delete(command.getTaskNumber());
            saveTasks();
            ui.showTaskDeleted(deletedTask, tasks.size());
            break;
        case MARK:
            Task markedTask = tasks.mark(command.getTaskNumber());
            saveTasks();
            ui.showTaskMarked(markedTask);
            break;
        case UNMARK:
            Task unmarkedTask = tasks.unmark(command.getTaskNumber());
            saveTasks();
            ui.showTaskUnmarked(unmarkedTask);
            break;
        case FIND:
            ui.showMatchingTasks(tasks.find(command.getKeyword()));
            break;
        case BYE:
            throw new AssertionError("The bye command is handled before execution");
        default:
            throw new AssertionError("Unsupported command type");
        }
    }

    private void showLoadingMessages() {
        if (loadingFailed) {
            ui.showLoadingError();
        }
        for (String warning : loadingWarnings) {
            ui.showLoadingWarning(warning);
        }
    }

    private void saveTasks() {
        try {
            storage.saveTasks(tasks.getTasks());
        } catch (IOException e) {
            ui.showSavingError();
        }
    }

    public static void main(String[] args) {
        new Mochi(Path.of("data", "duke.txt")).run();
    }
}
