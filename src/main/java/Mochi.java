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
            String command = scanner.nextLine(); // creates string so use .equals
            System.out.println(separator);

            String[] words = command.trim().split("\\s+");

            if (words[0].equals("mark")) {
                System.out.println("Nice! I've marked this task as done:");
                int target = Integer.parseInt(words[1]);
                Task t = tasks[target - 1];
                t.mark();
                System.out.println("  [" + t.getStatusIcon() + "] " + t.getDescription());
                System.out.println(separator);
                continue;
            }

            if (words[0].equals("unmark")) {
                System.out.println("OK, I've marked this task as not done yet:");
                int target = Integer.parseInt(words[1]);
                Task t = tasks[target - 1];
                t.unmark();
                System.out.println("  [" + t.getStatusIcon() + "] " + t.getDescription());
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
                    String listing = (i + 1) + "." +
                            "[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription();
                    System.out.println(listing);
                }
                System.out.println(separator);
                continue; // bc only exits when use says bye
            }

            tasks[n] = new Task(command);

            System.out.println("added: " + tasks[n].getDescription());
            n++;
            System.out.println(separator);
        }
    }
}
