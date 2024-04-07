import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for resolving paths.
 * <p>
 * This class provides methods to work with system paths.
 *
 * @author Younes Rabeh
 * @version under Dev
 */
public final class PathResolver {
    private PathResolver() {
        // Private constructor to prevent instantiation
    }


    /**
     * Gets the directory of the currently executing JAR file.
     *
     * @return The directory of the currently executing JAR file.
     * If the JAR file cannot be determined, returns the current working directory.
     * @throws URISyntaxException If the URI syntax is invalid
     */
    public static String getExecutionDirectory() throws URISyntaxException {
        String jarFilePath = PathResolver.class.getProtectionDomain()
                .getCodeSource().getLocation().toURI().getPath();
        Path jarPath = Paths.get(jarFilePath);

        if (!Files.exists(jarPath) || !Files.isRegularFile(jarPath)) {
            return Paths.get("").toAbsolutePath().toString();
        }
        return jarPath.getParent().toString();
    }

    /**
     * Checks if a file exists in the given directory.
     * @param fileName      The name of the file to check for.
     *                     Must include <b>.file extension</b> in the file name
     * @param directory The directory to check.
     * @return true if the file exists in the directory, false otherwise.
     * @throws NullPointerException If the provided {@code fileName} or {@code directory} are null.
     */
    public static boolean doesFileExists(String fileName, File directory) {
        if (!directory.exists() || !directory.isDirectory()) return false;
        File file = new File(directory, fileName);
        return file.exists() && file.isFile();
    }

    /**
     * Checks if a file exists in the given directory.
     * @param fileName      The name of the file to check for.
     *                     Must include <b>.file extension</b> in the file name
     * @param directoryPath The directory path to check.
     * @return true if the file exists in the directory, false otherwise.
     * @throws NullPointerException If the provided {@code fileName} or {@code directoryPath} are null.
     */
    public static boolean doesFileExists(String fileName, String directoryPath) {
        Path filePath = Paths.get(directoryPath, fileName);
        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }

    /**
     * Checks if a file exists within a directory tree up to a certain depth.
     *
     * @param fileName      The name of the file to search for.
     * @param directory The root directory to start searching from.
     * @param depth         The maximum depth of directory traversal. {@code depth <= 0 or equals null} <b>means same directory</b>.
     * @return true if the file exists within the directory tree, false otherwise.
     * @throws NullPointerException If the provided {@code fileName} or {@code directory} are null.
     * @throws StackOverflowError If {@code depth} is deeper than the call stack due to excessive recursion.
     */
    public static boolean doesFileExists(String fileName, File directory, Integer depth) {
        if (!directory.exists() || !directory.isDirectory()) return false;
        File file = new File(directory, fileName);
        if (file.exists() && file.isFile()) return true;
        if (depth-- <= 0) return false;

        File[] subDirectories = directory.listFiles(File::isDirectory);
        if (subDirectories != null) {
            for (File subDirectory : subDirectories) {
                if (doesFileExists(fileName, subDirectory, depth)) return true;
            }
        }
        return false;
    }

    /**
     * Checks if a file exists within a directory tree up to a certain depth.
     *
     * @param fileName      The name of the file to search for.
     * @param directoryPath The path of the root directory to start searching from.
     * @param depth         The maximum depth of directory traversal. {@code depth <= 0 or equals null} <b>means same directory</b>.
     * @return true if the file exists within the directory tree, false otherwise.
     * @throws NullPointerException If the provided {@code fileName} or {@code directory} are null.
     * @throws StackOverflowError If {@code depth} is deeper than the call stack due to excessive recursion.
     */
    public static boolean doesFileExists(String fileName, String directoryPath, Integer depth) {
        return doesFileExists(fileName, new File(directoryPath), depth);
    }



}

