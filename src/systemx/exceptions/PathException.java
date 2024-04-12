package systemx.exceptions;


/**
 * PathException is the superclass of those exceptions that can be thrown during the normal operation of the system.
 * It is the superclass of all exceptions that can be thrown by the systemx package.
 * @author Younes Rabeh
 * @version 1.0
 * @see InterfaceErrors
 */
abstract class PathException extends Exception implements InterfaceErrors {

    /**
     * Constructs a new PathException with the specified detail message.
     * @param message the detail message.
     */
    public PathException(String message) {
        super(message);
    }

}