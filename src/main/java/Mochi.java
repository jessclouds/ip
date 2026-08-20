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

        String[] tasks = new String[100];
        int n = 0;

        while (true) {
            String command = scanner.nextLine(); // creates string so use .equals
            System.out.println(separator);
            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            }

            if (command.equals("list")) {
                for (int i = 0; i < n; i++) {
                    String listing = (i + 1) + ". " + tasks[i];
                    System.out.println(listing);
                }
                System.out.println(separator);
                continue; // bc only exits when use says bye
            }

            tasks[n] = command;
            n++;

            System.out.println("added: " + command);
            System.out.println(separator);
        }
    }
}
