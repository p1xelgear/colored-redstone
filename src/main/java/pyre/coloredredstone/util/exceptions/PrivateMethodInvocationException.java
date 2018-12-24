package pyre.coloredredstone.util.exceptions;

public class PrivateMethodInvocationException extends RuntimeException {

    public PrivateMethodInvocationException(String message, Exception e) {
        super(message, e);
    }
}
