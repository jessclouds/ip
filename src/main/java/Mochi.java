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
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(separator);
    }
}
