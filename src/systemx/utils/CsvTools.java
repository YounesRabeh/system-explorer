package systemx.utils;

import java.util.List;

import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;

import systemx.exceptions.DoNotExistsException;

//TODO: add the exception throw to the methods that can throw an exception (safe)
/**
 * A utility class for working with CSV files
 *
 * @author Younes Rabeh
 * @version 1.0
 */
public final class CsvTools {
    // The file must be in the format of a CSV file, the first line is the header, and the rest are the data
    // Prevent instantiation
    private CsvTools() {}


    //TODO: add the access modifier to specific methods

    /**
     * Appends a row of data to a CSV file
     * @param file The CSV file
     * @param rowData The data to append
     * @throws DoNotExistsException If the file does not exist
     */
    private static void appendToCsvFile(File file, String[] rowData) throws DoNotExistsException {
        FileManager.appendToFile(file, String.join(",", rowData));
    }

    /**
     * Reads a CSV file and returns the data as a list of arrays
     * @param file The CSV file
     * @return The data in the CSV file
     * @throws DoNotExistsException If the file does not exist
     */
    private static List<String[]> getCsvFile(File file) throws DoNotExistsException {
        List<String[]> csvData = new ArrayList<>();
        List<String> lines = FileManager.getFileLines(file);
        for (String line : lines) {
            csvData.add(line.split(","));
        }
        return csvData;
    }

    /**
     * Reads a CSV file and returns a row as a list of arrays
     * @param file The path to the CSV file
     * @param index The index of the row to get
     * @return The row in the CSV file
     * @throws DoNotExistsException If the file does not exist
     */
    public static String[] getRow(File file, Integer index) throws DoNotExistsException {
        String fileContent = FileManager.getFileLine(file, index);
        if (fileContent != null) {
            return fileContent.split(",");
        }
        return null; // Index out of bounds
    }

    /**
     * Gets all the rows below a certain index in a CSV file
     * @param file The CSV file
     * @param index The index of the row to get all the rows below
     * @return The rows above the index in the CSV file
     * @throws DoNotExistsException If the file does not exist
     */
    public static List<String[]> getRowsAbove(File file, Integer index) throws DoNotExistsException {
        List<String[]> csvData = new ArrayList<>();
        List<String> lines = FileManager.getLinesAbove(file, index);
        for (String line : lines) {
            csvData.add(line.split(","));
        }
        return csvData;
    }

    /**
     * Gets all the rows below a certain index in a CSV file
     * @param file The CSV file
     * @param index The index of the row to get all the rows below
     * @return The rows below the index in the CSV file
     * @throws DoNotExistsException If the file does not exist
     */
    public static List<String[]> getRowsBelow(File file, Integer index) throws DoNotExistsException {
        List<String[]> csvData = new ArrayList<>();
        List<String> lines = FileManager.getLinesBelow(file, index);
        for (String line : lines) {
            csvData.add(line.split(","));
        }
        return csvData;
    }

    /**
     * Gets the Titles of the columns in a CSV file
     * @param file The CSV file
     * @return The titles of the columns
     * @throws DoNotExistsException If the file does not exist
     */
    public static String[] getColumnsTitles(File file) throws DoNotExistsException {
        return getRow(file, 0);
    }

    /**
     * Gets a column from a CSV file
     * @param file The CSV file
     * @param columnIndex The index of the column to get
     * @return The column data
     * @throws DoNotExistsException If the file does not exist
     */
    public static String[] getColumn(File file, Integer columnIndex) throws DoNotExistsException {
        List<String[]> csvData = getCsvFile(file);
        List<String> columnData = new ArrayList<>();

        for (String[] row : csvData) {
            if (row.length > columnIndex) {
                columnData.add(row[columnIndex]);
            }
        }
        return columnData.toArray(new String[0]);
    }

    /**
     * Gets a column from a CSV file
     * @param file The CSV file
     * @param columnIndices The indices of the columns to get
     * @return The columns data
     * @throws DoNotExistsException If the file does not exist
     */
    public static List<String[]> getColumns(File file, Integer... columnIndices) throws DoNotExistsException {
        List<String[]> columnsData = new ArrayList<>();
        List<String[]> csvData = getCsvFile(file);
        for (int columnIndex : columnIndices) {
            List<String> columnData = new ArrayList<>();
            for (String[] row : csvData) {
                if (row.length > columnIndex) {
                    columnData.add(row[columnIndex]);
                }
            }
            columnsData.add(columnData.toArray(new String[0]));
        }
        return columnsData;
    }

    /**
     * Adds a row to a CSV file
     * @param file The CSV file
     * @param rowData The data to add
     * @throws DoNotExistsException If the file does not exist
     */
    public static void appendRow(File file, String[] rowData) throws DoNotExistsException {
        appendToCsvFile(file, rowData);
    }

    /**
     * Adds multiple rows to a CSV file
     * @param file The CSV file
     * @param rowsData The data to add
     * @throws DoNotExistsException If the file does not exist
     */
    public static void appendRows(File file, List<String[]> rowsData) throws DoNotExistsException {
        for (String[] row : rowsData) {
            appendToCsvFile(file, row);
        }
    }

    /**
     * Appends the content of a CSV file to another CSV file
     * @param file The file to append to
     * @param fileToAppend The file to append
     * @throws DoNotExistsException If the file does not exist
     */
    public static void appendFile(File file, File fileToAppend) throws DoNotExistsException {
        for (String[] row : getCsvFile(fileToAppend)) {
            appendToCsvFile(file, row);
        }
    }

    /**
     * Appends a column to a CSV file. Best effort. If the column data is less than the number of rows,
     * the column will be appended to the rows that have data.
     * @param file The CSV file
     * @param columnData The data to append
     * @throws DoNotExistsException If the file does not exist
     */
    public static void appendColumn(File file, String[] columnData) throws DoNotExistsException {
        List<String> lines = new ArrayList<>(FileManager.getFileLines(file));
        final int size = columnData.length;
        for (int i = 0; i < lines.size(); i++) {
            if (i < size){
                lines.set(i, lines.get(i) + "," + columnData[i]);
            }
        }
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }

    //TODO: pick-up the rows using the init primary key, [get throughout the record]
    /**
     * Inserts a row into a CSV file
     * @param file The CSV file
     * @param index The index to insert the row at
     * @param rowData The data to insert
     * @throws DoNotExistsException If the file does not exist
     */
    public static void insertRow(File file, int index, String[] rowData) throws DoNotExistsException {
        List<String> lines = new ArrayList<>(FileManager.getFileLines(file));
        if (index < 0 || index > lines.size()) throw new IndexOutOfBoundsException();
        StringBuilder rowString = new StringBuilder();
        for (String data : rowData) {
            rowString.append(data).append(",");
        }
        rowString.deleteCharAt(rowString.length() - 1);
        lines.add(index, rowString.toString());
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }

    /**
     * Inserts multiple rows into a CSV file
     * @param file The CSV file
     * @param index The index to insert the rows at
     * @param rowsData The data to insert
     * @throws DoNotExistsException If the file does not exist
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public static void insertRows(File file, int index, List<String[]> rowsData) throws DoNotExistsException {
        List<String> lines = new ArrayList<>(FileManager.getFileLines(file));
        final int size = FileManager.countLines(file);
        if (index < 0 || index > lines.size()) throw new IndexOutOfBoundsException();
        StringBuilder rowString = new StringBuilder();
        for (String[] data : rowsData) {
            rowString.append(Arrays.toString(data)).append("\n");
        }
        rowString.deleteCharAt(rowString.length() - 1);
        lines.add(index, rowString.toString());
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }

    /**
     * Deletes a row from a CSV file
     * @param file The CSV file
     * @param index The index of the row to delete
     * @throws DoNotExistsException If the file does not exist
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public static void deleteRow(File file, int index) throws DoNotExistsException {
        List<String> lines = new ArrayList<>(FileManager.getFileLines(file));
        if (index < 0 || index >= lines.size()) throw new IndexOutOfBoundsException();
        lines.remove(index);
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }

    /**
     * Deletes multiple rows from a CSV file
     * @param file The CSV file
     * @param integers The indices of the rows to delete
     * @throws DoNotExistsException If the file does not exist
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public static void deleteRows(File file, Integer... integers) throws DoNotExistsException {
        List<String> lines = new ArrayList<>(FileManager.getFileLines(file));
        for (int index : integers) {
            if (index < 0 || index >= lines.size()) throw new IndexOutOfBoundsException();
            lines.remove(index);
        }
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }

    /**
     * Deletes a column from a CSV file
     * @param file The CSV file
     * @param index The index of the column to delete
     * @throws DoNotExistsException If the file does not exist
     */
    public static void deleteColumn(File file, int index) throws DoNotExistsException {
        List<String> lines = new ArrayList<>(FileManager.getFileLines(file));
        for (int i = 0; i < lines.size(); i++) {
            String[] row = lines.get(i).split(",");
            if (index < 0 || index >= row.length) throw new IndexOutOfBoundsException();
            StringBuilder rowString = new StringBuilder();
            for (int j = 0; j < row.length; j++) {
                if (j != index) {
                    rowString.append(row[j]).append(",");
                }
            }
            rowString.deleteCharAt(rowString.length() - 1);
            lines.set(i, rowString.toString());
        }
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }


    /**
     * Overrides a row in a CSV file
     * @param file The CSV file
     * @param index The index of the row to override
     * @param rowData The new data to override the row with
     * @throws DoNotExistsException If the file does not exist
     */
    public static void overrideRow(File file, int index, String[] rowData) throws DoNotExistsException {
        List<String> lines = new ArrayList<>(FileManager.getFileLines(file));
        if (index < 0 || index >= lines.size()) throw new IndexOutOfBoundsException();
        StringBuilder rowString = new StringBuilder();
        for (String data : rowData) {
            rowString.append(data).append(",");
        }
        rowString.deleteCharAt(rowString.length() - 1);
        lines.set(index, rowString.toString());
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }

    /**
     * Overrides a CSV file with new data
     * @param file The CSV file
     * @param data The new data to override the file with
     * @throws DoNotExistsException If the file does not exist
     */
    public static void overrideFile(File file, List<String[]> data) throws DoNotExistsException {
        List<String> lines = new ArrayList<>();
        for (String[] row : data) {
            lines.add(String.join(",", row));
        }
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }

    /**
     * Overrides a CSV file with new file data
     * @param file The CSV file
     * @param newFile The new file to override the file with
     * @throws DoNotExistsException If the file does not exist
     */
    public static void overrideFile(File file, File newFile) throws DoNotExistsException {
        List<String> lines = new ArrayList<>(FileManager.getFileLines(newFile));
        FileManager.overrideFile(file, lines.toArray(new String[0]));
    }
}
