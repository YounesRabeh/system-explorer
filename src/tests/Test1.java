package tests;

import systemx.exceptions.DoNotExistsException;

import systemx.utils.CsvTools;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        test1();
    }



    static void test1(){
        // Test the CsvTools class
        try {
            File file = new File("test.csv");
            String[] rowData = {"1", "2", "3", "5"};

            CsvTools.appendToCsvFile(file, rowData);
            List<String[]> csvData = CsvTools.getCsvFile(file);
            for (String[] row : csvData) {
                System.out.println(Arrays.toString(row));
            }
            String[] row = CsvTools.getRow(file, 0);
            System.out.println(Arrays.toString(row));
            System.out.println();
            throw new DoNotExistsException(file);
        } catch (DoNotExistsException e) {
            System.out.println(e.getMessage());

        }
    }

    static void test2(){
        // Test the CsvTools class
        File file = new File("test.csv");
        String[] rowData = {"1", "2", "3"};
        String text = " kh da au";
        try {
            List<String[]> csvData = CsvTools.getCsvFile(file);
            String[] row = CsvTools.getRow(file, 0);
            String data = row[0];
            System.out.println(data.toCharArray());
        } catch (DoNotExistsException e) {
            e.printStackTrace();

        }
    }
}

