package systemx.exceptions;


/**
 * InterfaceErrors is an interface that defines constants for different types of messages.
 * These constants are used throughout the application to maintain a consistent style for messages.
 * @author Younes Rabeh
 * @version 1.0
 */
interface InterfaceErrors {

    /**
     * A constant string that represents the RESET color in console.
     */
    String RESET = "\033[0m";

    /**
     * A constant string that represents the RED color in console.
     */
    String RED = "\033[0;31m";

    /**
     * A constant string that represents the YELLOW color in console.
     */
    String YELLOW = "\033[0;33m";

    /**
     * A constant string that represents the CYAN color in console.
     */
    String CYAN = "\033[0;36m";

    /**
     * A constant string that represents the GREEN color in console.
     */
    String GREEN = "\033[0;32m";

    /**
     * A constant string that represents the color to be used for error messages.
     */
    String ERROR_COLOR = RED;

    /**
     * A constant string that represents the color to be used for warning messages.
     */
    String WARNING_COLOR = YELLOW;

    /**
     * A constant string that represents the color to be used for informational messages.
     */
    String INFO_COLOR = CYAN;

    /**
     * A constant string that represents the color to be used for success messages.
     */
    String SUCCESS_COLOR = GREEN;

    /**
     * A constant string that represents the format of error messages.
     */
    String ERROR_MESSAGE = ERROR_COLOR + "⚠ \"";

    /**
     * A constant string that represents the format of warning messages.
     */
    String WARNING_MESSAGE = WARNING_COLOR + "⚠ \"";

    /**
     * A constant string that represents the format of informational messages.
     */
    String INFO_MESSAGE = INFO_COLOR + "\uD83D\uDEC8 ";

    /**
     * A constant string that represents the format of success messages.
     */
    String SUCCESS_MESSAGE = SUCCESS_COLOR + "✔ ";

}