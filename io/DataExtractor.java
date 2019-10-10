/*
 * This class reads data from a csv file and parses the data into 2 DataTables (valid & invalid).
 * This class also handles most other inputs and extracts useful information from them.
 */

package io;

import dataStructures.DataTable;
import dataStructures.Record;

import java.io.BufferedReader;
import java.io.FileReader;

public class DataExtractor{
	private final String targetPath;
	private final String inputFileName;
	private DataTable validTable;
	private DataTable invalidTable;

	public DataExtractor(String targetPath){
		this.targetPath = targetPath;
		this.inputFileName = getInputFileFromPath();
		this.validTable = new DataTable();
		this.invalidTable = new DataTable();
	}

	public String getInputFileName(){
		return this.inputFileName;
	}

	public DataTable getValidTable(){
		return this.validTable;
	}

	public DataTable getInvalidTable(){
		return this.invalidTable;
	}

	//read data line by line and put them in the 2 tables
	public void extract(){
		System.out.println("Extracting data from the input file\n");
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(targetPath));

			//skip first row (assuming file is formatted like the example file)
			csvReader.readLine();
			String row = csvReader.readLine();
			while(row != null){
				//skip a row if it is empty
				//this is to handle the last row
				//doesn't stop the program if there's an empty row in the middle of the file
				if(row.isEmpty()){
					row = csvReader.readLine();
					continue;
				}

				//split each row into columns of data
				//the regex matches all commas that are not inside quotations marks
				//this regex is used specifically for column E
				String[] data = row.split(",(?=(?:[^\"]|\"[^\"]*\")*$)");

				Record record = new Record();
				//populate the record
				for(int i = 0; i < data.length; i++){
					record.setData(i, data[i]);
				}

				//if the record is valid, add it to the valid table
				if(record.isValid()){
					validTable.addRecord(record);
				}
				//otherwise, add it to the invalid table
				else{
					invalidTable.addRecord(record);
				}

				//advance to the next row
				row = csvReader.readLine();
			}
			csvReader.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	//get the input file name from the target path
	private String getInputFileFromPath(){
		//separate directories
		String[] dirs = this.targetPath.split("\\\\");
		//get the input file (last item in the directories)
		String fileName = dirs[dirs.length - 1];
		//remove the .csv file extension
		fileName = fileName.substring(0, fileName.length() - 4);

		return fileName;
	}
}