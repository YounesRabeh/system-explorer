package systemx.exceptions;

import java.io.File;

public class DoNotExistsException extends PathException {

    /**
     * A constant string that represents the error message to be displayed when a file or directory does not exist.
     */
    private static final String DOES_NOT_EXIST = ERROR_MESSAGE + "Does not exist: ";

    public DoNotExistsException(String path) {
        super(DOES_NOT_EXIST + path + RESET);
    }

    public DoNotExistsException(File path) {
        super(DOES_NOT_EXIST + path.toString() + RESET);
    }
}