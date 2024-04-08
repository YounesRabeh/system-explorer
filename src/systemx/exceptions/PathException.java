package systemx.exceptions;

abstract class PathException extends Exception implements InterfaceErrors {

    public PathException(String message) {
        super(message);
    }

}