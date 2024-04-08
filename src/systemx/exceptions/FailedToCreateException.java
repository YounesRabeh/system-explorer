package systemx.exceptions;


import java.io.File;


public class FailedToCreateException extends PathException {
    public FailedToCreateException(String path) {
        super(FAILED_TO_CREATE_DIRECTORY + path + RESET);
    }

    public FailedToCreateException(File path) {
        super(FAILED_TO_CREATE_DIRECTORY + path.toString() + RESET);
    }

}
