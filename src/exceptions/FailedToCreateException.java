package exceptions;

import lib.Colors;

import java.io.File;


public class FailedToCreateException extends PathException {
    public FailedToCreateException(String path) {
        super(FAILED_TO_CREATE_DIRECTORY + path + Colors.RESET);
    }

    public FailedToCreateException(File path) {
        super(FAILED_TO_CREATE_DIRECTORY + path.toString() + Colors.RESET);
    }

}
