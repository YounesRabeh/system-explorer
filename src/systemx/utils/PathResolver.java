package systemx.utils;


import java.io.File;

import systemx.exceptions.DoNotExistsException;
import systemx.exceptions.FailedToCreateException;
import java.io.IOException;

/**
 * A utility class for paths related operations.
 * <p>
 * This class provides methods to work with system paths.
 *
 * @author Younes Rabeh
 * @version 1.0
 */
public final class PathResolver {
    private PathResolver() {
        // Private constructor to prevent instantiation
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
    public static boolean doesDirectoryExistsIn(String directoryName, File directory){
        if (checkNull(directoryName, directory)) throw new NullPointerException();
        if (!directory.isDirectory() || !directory.exists()) return false;
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
     * @param childDirectory The directory to search for.
     * @param parentDirectory The root directory to start searching from.
     * @return true if the directory exists within the directory tree, false otherwise.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws DoNotExistsException If the directory does not exist.
     * @throws StackOverflowError     If recursion depth surpasses the call stack size due to excessive recursion.
     */
    public static boolean doesDirectoryExistsIn(
            File childDirectory,
            File parentDirectory
    ) throws DoNotExistsException {
        return getDirectoryDepthIn(childDirectory, parentDirectory) >= 0;
    }

    /**
     * Checks if a directory exists within a given directory tree up to a certain depth.
     * @param directoryName The name of the directory to search for.
     * @param directory The root directory to start searching from.
     * @param depth     The maximum depth of directory traversal.
     * @return true if the directory exists within the directory tree, false otherwise.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws StackOverflowError If {@code depth} is deeper than the call stack due to excessive recursion.
     */
    public static boolean doesDirectoryExistsIn(String directoryName, File directory, Integer depth){
        if (checkNull(directoryName, directory)) throw new NullPointerException();
        if (!directory.exists()) return false;
        if (directory.isDirectory() && (depth != null && depth <= 0)) return false;
        if (directory.getName().equals(directoryName)) return true;

        final File[] subDirectories = directory.listFiles(File::isDirectory);
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
     * @throws DoNotExistsException If the child or parent directory does not exist.
     */
    public static int getDirectoryDepthIn(
            File childDirectory,
            File parentDirectory
    ) throws DoNotExistsException {
        if (childDirectory == null || parentDirectory == null) throw new NullPointerException();
        if (!childDirectory.exists() || !parentDirectory.exists()){
            throw new DoNotExistsException(childDirectory, parentDirectory);
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
        } catch (IOException dirAreNotNested) {
            return -1;
        }
    }

    /**
     * Creates a directory.
     *
     * @param directoryPath The path of the directory to create.
     * @return The created directory.
     * @throws NullPointerException If the provided {@code directoryPath} is null.
     */
    public static File createDirectory(String directoryPath) {
        if (checkNull(directoryPath)) throw new NullPointerException();
        File directory = new File(directoryPath);
        directory.mkdir();
        return directory;
    }

    /**
     * Creates a File in the directory.
     *
     * @param directory The directory to create.
     * @return The created directory.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws FailedToCreateException If the directory could not be created.
     */
    public static File createFileInDirectory(
            String fileName,
            File directory
    ) throws DoNotExistsException, FailedToCreateException {
        if (checkNull(fileName, directory)) throw new NullPointerException();
        if (!directory.exists() || !directory.isDirectory()) {
            throw new DoNotExistsException(directory, directory);
        }
        File file = new File(directory, fileName);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) throw new FailedToCreateException(file);
            } catch (IOException e) {
                throw new FailedToCreateException(file);
            }
        }
        return file;
    }

    /**
     * Deletes a directory.
     *
     * @param directory The directory to delete.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws DoNotExistsException If the directory does not exist.
     * @throws StackOverflowError If recursion depth surpasses the call stack size due to excessive recursion.
     */
    public static void deleteDirectory(File directory) throws DoNotExistsException {
        if (checkNull(directory)) throw new NullPointerException();
        if (!directory.exists()) throw new DoNotExistsException(directory);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else file.delete();
            }
        }
        directory.delete();
    }


    /**
     * Gets the files in a directory.
     *
     * @param directory The directory to get the files from.
     * @return An array of files in the directory.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws DoNotExistsException If the directory does not exist.
     */
    public static File[] getFilesInDirectory(File directory) throws DoNotExistsException{
        if (checkNull(directory)) throw new NullPointerException();
        if (!directory.exists() || !directory.isDirectory()) throw new DoNotExistsException(directory);
        return directory.listFiles();
    }

    /**
     * Gets the directories in a directory.
     *
     * @param directory The directory to get the directories from.
     * @return An array of directories in the directory.
     * @throws NullPointerException If the provided {@code directory} is null.
     * @throws DoNotExistsException If the directory does not exist.
     */
    public static File[] getDirectoriesInDirectory(File directory) throws DoNotExistsException{
        if (checkNull(directory)) throw new NullPointerException();
        if (!directory.exists() || !directory.isDirectory()) throw new DoNotExistsException(directory);
        return directory.listFiles(File::isDirectory);
    }

    /**
     * Gets the file in a directory.
     *
     * @param fileName The name of the file to get.
     * @param directory The directory to get the file from.
     * @return The file in the directory, or null if the file does not exist.
     * @throws NullPointerException If the provided {@code fileName} or {@code directory} are null.
     */
    public static File getFileInDirectory(String fileName, File directory) {
        if (checkNull(fileName, directory)) throw new NullPointerException();
        return doesFileExists(fileName, directory) ? new File(directory, fileName) : null;
    }

    /**
     * Gets the directory in a directory.
     *
     * @param directoryName The name of the directory to get.
     * @param directory The directory to get the directory from.
     * @return The directory in the directory, or null if the directory does not exist.
     * @throws NullPointerException If the provided {@code directoryName} or {@code directory} are null.
     */
    public static File getDirectoryInDirectory(String directoryName, File directory) {
        if (checkNull(directoryName, directory)) throw new NullPointerException();
        return doesDirectoryExistsIn(directoryName, directory) ? new File(directory, directoryName) : null;
    }

    /**
     * Checks if any of the provided objects are null.
     * @param objects The objects to check for null.
     * @return true if any of the objects are null, false otherwise.
     */
    public static boolean checkNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) return true;
        }
        return false;
    }
}

