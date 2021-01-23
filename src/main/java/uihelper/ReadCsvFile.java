package uihelper;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCsvFile {

    //This function return a list of array list
    public static ArrayList<ArrayList<String>> readCsv(String fileName){
        String file = System.getProperty("user.dir") + String.format("\\src\\main\\recources\\datatest\\%s.csv",fileName);
        ArrayList<String> col1 = new ArrayList<>();
        ArrayList<String> col2 = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {

            String [] nextLine;
            int rowNumber = 0;
            while ((nextLine = reader.readNext()) != null) {
                rowNumber++;
                if(rowNumber == 1) continue;
                col1.add(nextLine[0]);
                col2.add(nextLine[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        ArrayList<ArrayList<String>> objects = new ArrayList<ArrayList<String>>();
        objects.add(col1);
        objects.add(col2);
        return objects;
    }
}
