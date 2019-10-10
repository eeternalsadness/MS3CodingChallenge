package io;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class GUI{
    private final DefaultPaths defaultPaths;

    public GUI(){
        defaultPaths = new DefaultPaths();
    }

    //get the input file path using a file dialog
    public String getInputFilePath(){
        System.out.println("Getting the input file path");

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Choose an Input File");

        //return the input file path if the user selected a file
        int returnVal = fileChooser.showDialog(new JPanel(), "Select File");
        if(returnVal == JFileChooser.APPROVE_OPTION){
            String inputFilePath = fileChooser.getSelectedFile().getPath();
            System.out.println("Input file: " + inputFilePath + "\n");
            return inputFilePath;
        }

        //exit if the user did not select a file
        System.out.println("You did not select a file");
        System.out.println("Exiting");
        System.exit(1);

        return null;
    }

    //custom dialog for getting the default directory
    public String getDirectory(OutputFileType outputFileType){
        System.out.println("Getting default directory for " + outputFileType.toString());

        //create a file dialog that prompts the user input
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(".\\"));
        fileChooser.setDialogTitle("Choose the Default Directory for " + outputFileType.toString());

        //interpret user input
        String dir;
        int returnVal = fileChooser.showDialog(new JPanel(), "Select Directory");
        if(returnVal == JFileChooser.APPROVE_OPTION){
            //change the default directory
            dir = fileChooser.getSelectedFile().getPath();
            System.out.println("Selected directory: " + dir + "\n");

            //change the default path in the config.properties file
            defaultPaths.setDefaultPath(outputFileType, dir);
        }
        else {
            //return the current directory if the user did not select one
            System.out.println("You did not select a directory");
            System.out.println("The directory is set to be the default one");
            dir = defaultPaths.getPath(outputFileType);
            System.out.println("Default directory: " + dir + "\n");
        }

        return dir;
    }
}
