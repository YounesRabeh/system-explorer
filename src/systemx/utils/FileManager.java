package systemx.utils;

import java.io.*;
import java.util.List;

import java.util.ArrayList;

import systemx.exceptions.DoNotExistsException;


/**
 * A utility class for file related operations.
 * <p>
 * This class provides methods to work with an individual file.
 *
 * @author Younes Rabeh
 * @version under development
 */
public final class FileManager {
    private FileManager(){}


    /**
     * Reads the content of a file and returns it as a list of strings.
     * <p>
     * This method reads the content of a file and returns it as a list of strings.
     * Each string in the list represents a line in the file.
     *
     * @param file The file to read
     * @return a list of strings representing  content of the file
     * @throws DoNotExistsException if the file does not exist
     */
    public static List<String> getFileLines(File file) throws DoNotExistsException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
        } catch (Exception e){
            throw new DoNotExistsException(file);
        }

        return lines;
    }

    /**
     * Reads the content of a file and returns it as a list of strings.
     * <p>
     * This method reads the content of a file and returns it as a list of strings.
     * Each string in the list represents a line in the file.
     *
     * @param file The file to read
     * @param start The line number to start reading from
     * @param end The line number to stop reading at
     * @return a list of strings representing the content of the file
     * @throws DoNotExistsException if the file does not exist
     */
    public static List<String> getFileLines(File file, Integer start, Integer end) throws DoNotExistsException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            int fileLines = countLines(file);
            String line;
            if (start < 0 || start > fileLines) throw new IndexOutOfBoundsException();
            if (end < 0 || end > fileLines) throw new IndexOutOfBoundsException();
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= start && lineNumber <= end) lines.add(line);
            }
        } catch (IndexOutOfBoundsException e){
            throw e;
        } catch (Exception e){
            throw new DoNotExistsException(file);
        }

        return lines;
    }

    /**
     * Reads the content of a file and returns it as a list of strings.
     * @param file The file to read
     * @param lineNumber The line number to get
     * @return The line fetched from the file
     * @throws DoNotExistsException if the file does not exist
     */
    public static String getFileLine(File file, Integer lineNumber) throws DoNotExistsException {
        List<String> lines = getFileLines(file);
        if (lineNumber < 0 || lineNumber > lines.size()) throw new IndexOutOfBoundsException();
        return lines.get(lineNumber);
    }

    /**
     * Reads the content of a file and returns it as a list of strings.
     * @param file The file to read
     * @param index The index of the row to get below
     * @return The lines below the index fetched from the file
     */
    public static List<String> getLinesBelow(File file, Integer index) throws DoNotExistsException{
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int currentIndex = 0;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (currentIndex == index) found = true;
                if (found) lines.add(line);
                currentIndex++;
            }
        } catch (Exception e) {
            throw new DoNotExistsException(file);
        }
        return lines;
    }

    /**
     * Reads the content of a file and returns it as a list of strings.
     * @param file The file to read
     * @param index The index of the row to get above
     * @return The lines above the index fetched from the file
     */
    public static List<String> getLinesAbove(File file, Integer index) throws DoNotExistsException{
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int currentIndex = 0;
            while ((line = reader.readLine()) != null) {
                if (currentIndex < index) lines.add(line);
                else break;
                currentIndex++;
            }
        } catch (Exception e) {
            throw new DoNotExistsException(file);
        }
        return lines;
    }

    /**
     * Counts the number of lines in a file.
     * <p>
     * This method counts the number of lines in a file and returns the count.
     *
     * @param file The file to count the lines of
     * @return The number of lines in the file
     * @throws DoNotExistsException if the file does not exist
     */
    public static int countLines(File file) throws DoNotExistsException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            while (reader.readLine() != null) count++;
        } catch (Exception e){
            throw new DoNotExistsException(file);
        }

        return count;
    }

    /**
     * Appends the content of a file to another file.
     * <p>
     * This method appends the content of a file to another file.
     *
     * @param file The file to append to
     * @param fileToAppend The file to append
     * @throws DoNotExistsException if the file to append does not exist
     */
    public static void appendToFile(File file, File fileToAppend) throws DoNotExistsException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
             BufferedReader reader = new BufferedReader(new FileReader(fileToAppend))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            throw new DoNotExistsException(file);
        }
    }

    /**
     * Appends an array of strings to a file.
     * <p>
     * This method appends an array of strings to a file.
     *
     * @param file The file to append to
     * @param lines The array of strings to append
     * @throws DoNotExistsException if the file does not exist
     */
    public static void appendToFile(File file, String[] lines) throws DoNotExistsException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (!lineCheck(file)) writer.newLine();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            throw new DoNotExistsException(file);
        }
    }

    /**
     * Appends a string to a file.
     * <p>
     * This method appends a string to a file.
     *
     * @param file The file to append to
     * @param line The string to append
     * @throws DoNotExistsException if the file does not exist
     */
    public static void appendToFile(File file, String line) throws DoNotExistsException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (!lineCheck(file)) writer.newLine();
            writer.write(line);
            writer.newLine();
        } catch (Exception e) {
            throw new DoNotExistsException(file);
        }
    }

    /**
     * Overrides The content of a file with the content of another file.
     * @param file The file to override
     * @param newFile The file to override with
     * @throws DoNotExistsException if the file does not exist
     */
    public static void overrideFile(File file, File newFile) throws DoNotExistsException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));
             BufferedReader reader = new BufferedReader(new FileReader(newFile))) {
            String line;
            if (!lineCheck(file)) writer.newLine();
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            throw new DoNotExistsException(file);
        }
    }

    /**
     * Overrides the content of a file with an array of strings.
     * @param file The file to override
     * @param lines The array of strings to override with
     * @throws DoNotExistsException if the file does not exist
     */
    public static void overrideFile(File file, String[] lines) throws DoNotExistsException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            throw new DoNotExistsException(file);
        }
    }

    /**
     * Overrides the content of a file with a string.
     * @param file The file to override
     * @param lineNumber The line number to override
     * @param newLine The string to override with
     * @throws DoNotExistsException if the file does not exist
     * @throws IndexOutOfBoundsException if the line number is out of bounds
     */
    public static void overrideLine(File file, Integer lineNumber, String newLine) throws DoNotExistsException {
        List<String> lines = getFileLines(file);
        if (lineNumber < 1 || lineNumber > lines.size()) throw new IndexOutOfBoundsException();
        lines.set(lineNumber - 1, newLine);
        overrideFile(file, lines.toArray(new String[0]));
    }


    /**
     * Overrides a section of a file with an array of strings.
     * @param file The file to override
     * @param start The line number to start overriding from
     * @param end The line number to stop overriding at
     * @param newLines The array of strings to override with
     * @throws DoNotExistsException if the file does not exist
     * @throws IndexOutOfBoundsException if the line numbers are out of bounds
     */
    public static void overrideSection(File file, Integer start, Integer end, String[] newLines) throws DoNotExistsException {
        List<String> lines = getFileLines(file);
        if (start < 1 || start > lines.size()) throw new IndexOutOfBoundsException();
        if (end < 1 || end > lines.size()) throw new IndexOutOfBoundsException();
        for (int i = start - 1; i < end; i++) {
            lines.set(i, newLines[i - start + 1]);
        }
        overrideFile(file, lines.toArray(new String[0]));
    }

    //FIXME: NO override file just insert
    /**
     * Overrides a section of a file with a file.
     * @param file The file to override
     * @param fileToInsert The file to override with
     * @param lineNumber The line number to override
     * @throws DoNotExistsException if the file does not exist
     */
    public static void insertFile(File file, File fileToInsert, Integer lineNumber) throws DoNotExistsException {
        List<String> lines = getFileLines(file);
        List<String> linesToInsert = getFileLines(fileToInsert);
        if (lineNumber < 1 || lineNumber > lines.size()) throw new IndexOutOfBoundsException();
        lines.addAll(lineNumber - 1, linesToInsert);
        overrideFile(file, lines.toArray(new String[0]));
    }

    /**
     * Inserts a string to a file.
     * @param file The file to insert
     * @param lines The array of strings to insert
     * @param lineNumber The line number to insert at
     * @throws DoNotExistsException if the file does not exist
     * @throws IndexOutOfBoundsException if the line number is out of bounds
     */
    public static void insertLines(File file, String[] lines, Integer lineNumber) throws DoNotExistsException {
        List<String> fileLines = getFileLines(file);
        if (lineNumber < 1 || lineNumber > fileLines.size()) throw new IndexOutOfBoundsException();
        for (int i = 0; i < lines.length; i++) {
            fileLines.add(lineNumber - 1 + i, lines[i]);
        }
        overrideFile(file, fileLines.toArray(new String[0]));
    }

    /**
     * Inserts a string to a file.
     * @param file The file to insert
     * @param line The string to insert
     * @param lineNumber The line number to insert at
     * @throws DoNotExistsException if the file does not exist
     * @throws IndexOutOfBoundsException if the line number is out of bounds
     */
    public static void insertLine(File file, String line, Integer lineNumber) throws DoNotExistsException {
        List<String> fileLines = getFileLines(file);
        if (lineNumber < 1 || lineNumber > fileLines.size()) throw new IndexOutOfBoundsException();
        fileLines.add(lineNumber - 1, line);
        overrideFile(file, fileLines.toArray(new String[0]));
    }

    /**
     * Deletes a line from a file.
     * @param file The file to delete from
     * @param start The line number to start deleting from
     * @param end The line number to stop deleting at
     * @throws DoNotExistsException if the file does not exist
     * @throws IndexOutOfBoundsException if the line number is out of bounds
     */
    public static void deleteSection(File file, Integer start, Integer end) throws DoNotExistsException {
        List<String> lines = getFileLines(file);
        if (start < 1 || start > lines.size()) throw new IndexOutOfBoundsException();
        if (end < 1 || end > lines.size()) throw new IndexOutOfBoundsException();
        lines.subList(start - 1, end).clear();
        overrideFile(file, lines.toArray(new String[0]));
    }

    /**
     * Deletes a line from a file.
     * @param file The file to delete from
     * @param lineNumber The line number to delete
     * @throws DoNotExistsException if the file does not exist
     * @throws IndexOutOfBoundsException if the line number is out of bounds
     */
    public static void deleteLine(File file, Integer lineNumber) throws DoNotExistsException {
        List<String> lines = getFileLines(file);
        if (lineNumber < 1 || lineNumber > lines.size()) throw new IndexOutOfBoundsException();
        lines.remove(lineNumber - 1);
        overrideFile(file, lines.toArray(new String[0]));
    }

    static boolean lineCheck(File file) {
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            long length = raf.length();
            if (length > 0) {
                raf.seek(length - 1); // Move to the end of the file
                byte lastByte = raf.readByte();
                if (lastByte != '\n' && lastByte != '\r') {
                    raf.writeByte('\n'); // Add newline character if not present
                }
            }
            raf.close();
            return true; // Return true if write operation was successful
        } catch (IOException e) {
            return false; // Return false if an IOException occurred
        }
    }
}
