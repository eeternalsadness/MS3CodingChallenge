/*
 * This class creates a log file that showls how many records were received/successful/failed.
 * The log file is stored in the pre-determined path.
 */

package fileCreators;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogCreator{
    private String inputFileName;
    private final String logPath;
    private final int validTableSize;
    private final int invalidTableSize;

    public LogCreator(String inputFileName,
                      String logPath,
                      int validTableSize,
                      int invalidTableSize){
        this.inputFileName = inputFileName;
        this.logPath = logPath;
        this.validTableSize = validTableSize;
        this.invalidTableSize = invalidTableSize;
    }

    //create log file to log the number of records received/successful/failed
    public void createLogFile(){
        System.out.println("Creating log file");
        String filePath = this.logPath + this.inputFileName + ".log";

        //if log file exists, create a new one with an index
        File file = new File(filePath);
        if(file.exists()){
            System.out.println("Log file already exists");
            System.out.println("Creating a new one");
            int index = 1;
            do{
                filePath = this.logPath + this.inputFileName + "(" + index + ").log";
                file = new File(filePath);
                ++index;
            }
            while(file.exists());
            this.inputFileName = this.inputFileName + "(" + index + ")";
        }

        Logger logger = Logger.getLogger(this.inputFileName);
        final int recordsReceived = validTableSize + invalidTableSize;

        try {
            //configure logger
            FileHandler handler = new FileHandler(filePath);
            logger.addHandler(handler);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);

            //log number of records received/successful/failed
            System.out.println("Logging");
            logger.info("#Records received: " + recordsReceived);
            logger.info("#Records successful: " + validTableSize);
            logger.info("#Records failed: " + invalidTableSize);

            System.out.println("Output file: " + filePath + "\n");
        }
        //should never reach here
        catch(IOException e){
            System.out.println("Failed!");
            e.printStackTrace();
            System.out.println("Exiting");
            System.exit(1);
        }
    }
}