import java.io.File;
import java.io.IOException;
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
     * Checks if a directory exists.
     *
     * @param directoryPath The path of the directory to check.
     * @return true if the directory exists, false otherwise.
     * @throws NullPointerException If the provided {@code directoryPath} is null.
     */
    public static boolean doesDirectoryExists(File directoryPath) {
        return directoryPath.exists() && directoryPath.isDirectory();
    }

    /**
     * Checks if a directory exists within a given directory tree.
     * @param directoryName The name of the directory to search for.
     * @param directory The root directory to start searching from.
     * @return true if the directory exists within the directory tree, false otherwise.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws StackOverflowError     If recursion depth surpasses the call stack size due to excessive recursion.
     */
    public static boolean doesDirectoryExistsIn(String directoryName, File directory) {
        if (directory == null
                || !directory.exists()
                || !directory.isDirectory())
        {return false;}
        if (directory.getName().equals(directoryName)) return true;
        File[] subDirectories = directory.listFiles(File::isDirectory);
        if (subDirectories != null) {
            for (File subDirectory : subDirectories) {
                if (doesDirectoryExistsIn(directoryName, subDirectory)) return true;
            }
        }
        return false;
    }

    /**
     * Checks if the childDirectory exists within a given parentDirectory.
     * @param childDirectory The name of the directory to search for.
     * @param parentDirectory The root directory to start searching from.
     * @return true if the directory exists within the directory tree, false otherwise.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws StackOverflowError     If recursion depth surpasses the call stack size due to excessive recursion.
     */
    public static boolean doesDirectoryExistsIn(File childDirectory, File parentDirectory){
        return getDirectoryDepthIn(childDirectory, parentDirectory) >= 0;
    }

    /**
     * Checks if a directory exists within a given directory tree up to a certain depth.
     * @param directoryName The name of the directory to search for.
     * @param directory The root directory to start searching from.
     * @param depth     The maximum depth of directory traversal.
     * @return true if the directory exists within the directory tree, false otherwise.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws StackOverflowError     If {@code depth} is deeper than the call stack due to excessive recursion.
     */
    public static boolean doesDirectoryExistsIn(String directoryName, File directory, Integer depth) {
        if (directory == null
                || !directory.exists()
                || !directory.isDirectory()
                || (depth != null && depth <= 0))
        {return false;}
        if (directory.getName().equals(directoryName)) return true;
        File[] subDirectories = directory.listFiles(File::isDirectory);
        if (subDirectories != null) {
            for (File subDirectory : subDirectories) {
                if (doesDirectoryExistsIn(directoryName, subDirectory, depth)) return true;
            }
        }
        return false;
    }

    /**
     * Calculates the depth of a child directory relative to a parent directory.
     *
     * @param childDirectory  The child directory whose depth is to be calculated.
     * @param parentDirectory The parent directory against which the child directory's depth is measured.
     * @return The depth of the child directory relative to the parent directory. {@code depth <= 0} <b>means same directory</b>.
     * <p>Returns -1 if the child directory is not nested within the parent directory or if a generic I/O error occurs.
     * @throws NullPointerException If the provided {@code childDirectory} or {@code parentDirectory} are null.
     */
    public static int getDirectoryDepthIn(File childDirectory, File parentDirectory) {
        if (!childDirectory.exists() || !parentDirectory.exists()) {
            throw new IllegalArgumentException("Both directories must exist");
        }
        try {

            String childCanonicalPath = childDirectory.getCanonicalFile().getPath();
            String parentCanonicalPath = parentDirectory.getCanonicalFile().getPath() + File.separator;

            if (childCanonicalPath.startsWith(parentCanonicalPath)) {
                int depth = 0;
                for (char c : childCanonicalPath.substring(parentCanonicalPath.length()).toCharArray()) {
                    if (c == File.separatorChar) depth++;
                }
                return depth;
            } else return -1;
        } catch (IOException dirAreNotNested) {return -1;}
    }




}

