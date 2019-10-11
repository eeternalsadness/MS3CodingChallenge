import dataStructures.DataTable;
import fileCreators.CSVFileCreator;
import fileCreators.LogCreator;
import fileCreators.SQLiteCreator;
import io.DataExtractor;
import io.GUI;
import io.OutputFileType;

class MainApplication{
	public static void main(String[] args){
		//use a file dialog to ensure input file's validity
		GUI gui = new GUI();

		//get paths
		String targetPath = gui.getInputFilePath();
		String validTablePath = gui.getDirectory(OutputFileType.OUTPUT_SQLITE_DATABASE);
		String invalidTablePath = gui.getDirectory(OutputFileType.OUTPUT_CSV_FILE);
		String logPath = gui.getDirectory(OutputFileType.OUTPUT_LOG_FILE);

		DataExtractor dataExtractor = new DataExtractor(targetPath);
		String inputFileName = dataExtractor.getInputFileName();
		//read data from csv file and store in 2 tables (valid & invalid)
		dataExtractor.extract();

		//get the data tables
		DataTable validTable = dataExtractor.getValidTable();
		DataTable invalidTable = dataExtractor.getInvalidTable();

		//insert valid records into SQLite DB
		SQLiteCreator sqliteCreator = new SQLiteCreator(inputFileName, validTable, validTablePath);
		sqliteCreator.createDBFile();

		//insert invalid records into csv file
		CSVFileCreator csvFileCreator = new CSVFileCreator(inputFileName, invalidTable, invalidTablePath);
		csvFileCreator.createInvalidRecords();

		//create log file
		LogCreator logCreator = new LogCreator(inputFileName, logPath, 	validTable.getSize(), invalidTable.getSize());
		logCreator.createLogFile();
	}
}