/**
 * Represents an error caused by an invalid Mochi command.
 */

public class MochiException extends Exception {
    private static final long serialVersionUID = 1L;

    public MochiException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "OOPS!!! " + getMessage();
    }

}
