/*
 * This class is used to read and write to the config.properties file.
 * This class can access and change the default paths for the output files.
 * Paths are relative to the src folder (src folder is ./).
 */

package io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class DefaultPaths {
    private final String PROPERTIES_FILE;
    private Properties properties;
    private String validTablePath;
    private String invalidTablePath;
    private String logPath;

    public DefaultPaths(){
        PROPERTIES_FILE = "config.properties";
        properties = new Properties();
        //initialize default paths
        getDefaultPaths();
        //create default directories if they are not created
        createDefaultDirectories(OutputFileType.OUTPUT_SQLITE_DATABASE);
        createDefaultDirectories(OutputFileType.OUTPUT_CSV_FILE);
        createDefaultDirectories(OutputFileType.OUTPUT_LOG_FILE);
    }

    //get the path of a type of output file
    public String getPath(OutputFileType outputFileType){
        switch(outputFileType){
            case OUTPUT_SQLITE_DATABASE: return this.validTablePath;
            case OUTPUT_CSV_FILE: return this.invalidTablePath;
            case OUTPUT_LOG_FILE: return this.logPath;
            //should not reach here
            default:
                System.out.println("Invalid file type");
                System.out.println("Exiting");
                System.exit(1);
                return null;
        }
    }

    //set the property of a specific key
    public void setDefaultPath(OutputFileType outputFileType, String path){
        try{
            FileOutputStream outputStream = new FileOutputStream(PROPERTIES_FILE);

            //set the default path
            this.properties.setProperty(outputFileType.toString(), path);
            this.properties.store(outputStream, null);
            outputStream.close();

            //change the member variables
            switch(outputFileType){
                case OUTPUT_SQLITE_DATABASE: this.validTablePath = path; break;
                case OUTPUT_CSV_FILE: this.invalidTablePath = path; break;
                case OUTPUT_LOG_FILE: this.logPath = path; break;
                default:
                    System.out.println("Invalid file type");
                    System.out.println("Exiting");
                    System.exit(1);
            }
        }
        catch (Exception e){
            System.out.println("Something went wrong...");
            e.printStackTrace();
            System.out.println("Exiting");
            System.exit(1);
        }
    }

    //read the config.properties file to initialize member variables with default values
    private void getDefaultPaths(){
        try{
            //read properties file
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(PROPERTIES_FILE);
            this.properties.load(inputStream);

            //initialize member variables with default values
            this.validTablePath = this.properties.getProperty(OutputFileType.OUTPUT_SQLITE_DATABASE.toString());
            this.invalidTablePath = this.properties.getProperty(OutputFileType.OUTPUT_CSV_FILE.toString());
            this.logPath = this.properties.getProperty(OutputFileType.OUTPUT_LOG_FILE.toString());

            inputStream.close();
        }
        //should never reach here
        catch(Exception e){
            System.out.println("Something went wrong...");
            e.printStackTrace();
            System.out.println("Exiting");
            System.exit(1);
        }
    }

    private void createDefaultDirectories(OutputFileType outputFileType){
        File dir = new File(this.getPath(outputFileType));

        //if directory does not exist, create necessary directories
        if(!dir.exists()){
            System.out.println("Creating default directory for " + outputFileType.toString());
            if(dir.mkdirs()){
                System.out.println("Default directory created\n");
            }
            //should not reach here
            else{
                System.out.println("Fail to create default directory for " + outputFileType.toString());
                System.out.println("Exiting");
                System.exit(1);
            }
        }
    }
}