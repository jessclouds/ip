import java.util.Scanner;

/**
 * Starts the Mochi chatbot application.
 */


public class Mochi {
    public static void main(String[] args) {
        String banner = " __  __            _     _ \n"
                + "|  \\/  | ___   ___| |__ (_)\n"
                + "| |\\/| |/ _ \\ / __| '_ \\| |\n"
                + "| |  | | (_) | (__| | | | |\n"
                + "|_|  |_|\\___/ \\___|_| |_|_|\n";
        String separator = "____________________________________________________________";

        System.out.println(separator);
        System.out.print(banner);
        System.out.println("Hello! I'm Mochi.");
        System.out.println("What can I do for you?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int n = 0;
        while (true) {
            String command = scanner.nextLine().trim();
            System.out.println(separator);

            String[] words = command.trim().split("\\s+", 2);
            try {
                if (words.length < 2) {
                    if (command.equals("todo")
                            || command.equals("deadline")
                            || command.equals("event")) {
                        throw new MochiException(
                                "The description of a " + words[0]
                                        + " cannot be empty.");
                    }

                    if (command.equals("mark")
                            || command.equals("unmark")) {
                        throw new MochiException(
                                "Please specify a task number to " + words[0] + ".");
                    }
                }

                if (words[0].equals("mark")) {
                    int target;

                    try {
                        target = Integer.parseInt(words[1]);
                    } catch (NumberFormatException e) {
                        throw new MochiException(
                                "The task number must be a whole number.");
                    }

                    if (target < 1 || target > n) {
                        throw new MochiException(
                                "There is no task numbered " + target + ".");
                    }

                    System.out.println("Nice! I've marked this task as done:");
                    Task t = tasks[target - 1];
                    t.mark();
                    System.out.println("  " + t);
                    System.out.println(separator);
                    continue;
                }

                if (words[0].equals("unmark")) {
                    int target;

                    try {
                        target = Integer.parseInt(words[1]);
                    } catch (NumberFormatException e) {
                        throw new MochiException(
                                "The task number must be a whole number.");
                    }

                    if (target < 1 || target > n) {
                        throw new MochiException(
                                "There is no task numbered " + target + ".");
                    }

                    System.out.println("OK, I've marked this task as not done yet:");
                    Task t = tasks[target - 1];
                    t.unmark();
                    System.out.println("  " + t);
                    System.out.println(separator);
                    continue;
                }

                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(separator);
                    break;
                }

                if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < n; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                    System.out.println(separator);
                    continue; // bc only exits when use says bye
                }

                Task newTask = null;

                if (command.startsWith("todo ")) {
                    String description = command.substring("todo ".length()).trim();
                    newTask = new Todo(description);
                } else if (command.startsWith("deadline ")) {
                    String details = command.substring("deadline ".length());

                    if (!details.contains(" /by ")) {
                        throw new MochiException(
                                "Use this format: deadline DESCRIPTION /by DATE.");
                    }

                    String[] parts = details.split(" /by ", 2);

                    String description = parts[0].trim();

                    String by = parts[1].trim();

                    if (description.isEmpty() || by.isEmpty()) {
                        throw new MochiException(
                                "A deadline needs both a description and a date.");
                    }

                    newTask = new Deadline(description, by);
                } else if (command.startsWith("event ")) {
                    String details = command.substring("event ".length());


                    if (!details.contains(" /from ")) {
                        throw new MochiException(
                                "Use this format: event DESCRIPTION /from START /to END.");
                    }

                    String[] fromParts = details.split(" /from ", 2);


                    if (!fromParts[1].contains(" /to ")) {
                        throw new MochiException(
                                "Use this format: event DESCRIPTION /from START /to END.");
                    }

                    String[] toParts = fromParts[1].split(" /to ", 2);

                    String description = fromParts[0].trim();
                    String from = toParts[0].trim();
                    String to = toParts[1].trim();

                    if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new MochiException(
                                "An event needs a description, start, and end.");
                    }


                    newTask = new Event(description, from, to);
                }

                if (newTask != null) {
                    tasks[n] = newTask;

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);

                    n++;
                    System.out.println("Now you have " + n + " tasks in the list.");
                    System.out.println(separator);
                    continue;
                }
                throw new MochiException(
                        "I'm sorry, but I don't know what that means :-("
                );
            } catch (MochiException e) {
                System.out.println(e.toString());
                System.out.println(separator);
                continue;
            }
        }
    }
}
