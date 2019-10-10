/*
 * This class creates a csv file that holds the invalid records.
 * The output file is created in the pre-determined path.
 */

package fileCreators;

import dataStructures.DataTable;
import dataStructures.Record;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileCreator{
    private final String inputFileName;
    private final DataTable invalidTable;
    private final String invalidTablePath;

    public CSVFileCreator(String inputFileName, DataTable invalidTable, String invalidTablePath){
        this.inputFileName = inputFileName;
        this.invalidTable = invalidTable;
        this.invalidTablePath = invalidTablePath;
    }

    //create csv file containing invalid records
    public void createInvalidRecords(){
        System.out.println("Creating CSV file for the invalid records");
        try {
            String filePath = this.invalidTablePath	+ this.inputFileName + "-bad.csv";

            //if csv file exists, create a new one with an index
            File file = new File(filePath);
            if(file.exists()){
                System.out.println("CSV file already exists");
                System.out.println("Creating a new one");
                int index = 1;
                do{
                    filePath = this.invalidTablePath + this.inputFileName + "-bad(" + index + ").csv";
                    file = new File(filePath);
                    ++index;
                }
                while(file.exists());
            }

            FileWriter csvWriter = new FileWriter(filePath);
            //create table headers
            createTableHeaders(csvWriter);

            //populate csv file with invalid records
            Record record = this.invalidTable.getFirst();
            //iterate through the table sequentially
            while(record != null){
                //append values until the next-to-last value
                for(int dataIndex = 0; dataIndex < record.getSize()-1; dataIndex++){
                    csvWriter.append(record.getData(dataIndex));
                    csvWriter.append(",");
                }
                //append the last value
                csvWriter.append(record.getData(record.getSize() - 1));
                csvWriter.append("\n");

                record = record.getNext();
            }
            csvWriter.close();
            System.out.println("Success!");
            System.out.println("Output file: " + filePath + "\n");
        }
        //should never reach here
        catch(IOException e){
            System.out.println("Failed!");
            e.printStackTrace();
        }
    }

    //create the table headers (A-J) for csv file
    private void createTableHeaders(FileWriter csvWriter){
        try {
            csvWriter.append("A");
            csvWriter.append(",");
            csvWriter.append("B");
            csvWriter.append(",");
            csvWriter.append("C");
            csvWriter.append(",");
            csvWriter.append("D");
            csvWriter.append(",");
            csvWriter.append("E");
            csvWriter.append(",");
            csvWriter.append("F");
            csvWriter.append(",");
            csvWriter.append("G");
            csvWriter.append(",");
            csvWriter.append("H");
            csvWriter.append(",");
            csvWriter.append("I");
            csvWriter.append(",");
            csvWriter.append("J");
            csvWriter.append("\n");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}