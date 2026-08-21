/**
 * Represents an error caused by an invalid Mochi command.
 */

public class MochiException extends Exception {
    public MochiException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "OOPS!!! " + getMessage();
    }

}
