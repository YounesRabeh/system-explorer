package exceptions;

public abstract class PathException extends Exception implements InterfaceErrors {

    public PathException(String message) {
        super(message);
    }
}