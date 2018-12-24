package pyre.coloredredstone.util.exceptions;

public class GetPrivateFieldValueException extends RuntimeException {

    public GetPrivateFieldValueException(String message, Exception e) {
        super(message, e);
    }
}
