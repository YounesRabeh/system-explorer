package systemx.exceptions;


import java.io.File;

/**
 * Thrown to indicate that a directory could not be created.
 * @author Younes Rabeh
 * @version 1.0

 */
public class FailedToCreateException extends PathException {

    /**
     * A constant string that represents the error message to be displayed when a directory fails to be created.
     */
    private static final String FAILED_TO_CREATE_DIRECTORY = ERROR_MESSAGE + "\"Failed to create : ";

    /**
     * Thrown to indicate that a directory could not be created.
     * @param path the path of the directory that could not be created.
     */
    public FailedToCreateException(String path) {
        super(FAILED_TO_CREATE_DIRECTORY + path + "\"" +  RESET);
    }

    /**
     * Thrown to indicate that a directory could not be created.
     * @param path the path of the directory that could not be created.
     */
    public FailedToCreateException(File path) {
        super(FAILED_TO_CREATE_DIRECTORY + path + "\"" + RESET);
    }
}
