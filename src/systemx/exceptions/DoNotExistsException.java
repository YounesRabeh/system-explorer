package systemx.exceptions;

import java.io.File;

public class DoNotExistsException extends PathException {

    /**
     * A constant string that represents the error message to be displayed when a file or directory does not exist.
     */
    private static final String DOES_NOT_EXIST_IN = ERROR_MESSAGE + " doesn't exist in ";

    /**
     * A constant string that represents the error message to be displayed when a file or directory does not exist.
     */ 
    private static final String DOES_NOT_EXIST = ERROR_MESSAGE + " doesn't exist" + RESET;

    /**
     * Thrown to indicate that a file or directory does not exist.
     * @param path the path of the file or directory that does not exist.
     */
    public DoNotExistsException(String path) {
        super(path + DOES_NOT_EXIST);
    }

    /**
     * Thrown to indicate that a file or directory does not exist.
     * @param path the path of the file or directory that does not exist.
     * @param parentPath the path of the parent directory.
     */
    public DoNotExistsException(String path, String parentPath) {
        super(path + DOES_NOT_EXIST_IN + parentPath + RESET);
    }

    /**
     * Thrown to indicate that a file or directory does not exist.
     * @param path the path of the file or directory that does not exist.
     */
    public DoNotExistsException(File path) {
        super(path + DOES_NOT_EXIST);
    }

    /**
     * Thrown to indicate that a file or directory does not exist.
     * @param path the path of the file or directory that does not exist.
     * @param parentPath the path of the parent directory.
     */
    public DoNotExistsException(File path, File parentPath) {
        super(parentPath.toString() + DOES_NOT_EXIST_IN + path.toString() + RESET);
    }
}