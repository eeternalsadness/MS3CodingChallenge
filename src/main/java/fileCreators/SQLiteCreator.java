/*
 * This class creates a SQLite db file that stores the valid records.
 * The output file is stored in the pre-determined path.
 */

package fileCreators;

import dataStructures.DataTable;
import dataStructures.Record;
import java.io.File;
import java.sql.*;

public class SQLiteCreator {
    private final String inputFileName;
    private final DataTable validTable;
    private final String validTablePath;
    private final String createTableSQL;
    private final String insertSQL;

    public SQLiteCreator(String inputFileName, DataTable validTable, String validTablePath){
        this.inputFileName = inputFileName;
        this.validTable = validTable;
        this.validTablePath = validTablePath;
        String tableName = "records";
        //SQL statement for creating the table
        this.createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n"
            + "A text NOT NULL,\n"
            + "B text NOT NULL,\n"
            + "C text NOT NULL,\n"
            + "D text NOT NULL,\n"
            + "E text NOT NULL,\n"
            + "F text NOT NULL,\n"
            + "G text NOT NULL,\n"
            + "H text NOT NULL,\n"
            + "I text NOT NULL,\n"
            + "J text NOT NULL\n"
            + ");";
        //SQL statement for inserting a new row
        this.insertSQL = "INSERT INTO " + tableName
            + "(A,B,C,D,E,F,G,H,I,J) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";
    }

    //create SQLite database file containing valid records
    public void createDBFile(){
        System.out.println("Creating SQLite database");
        String filePath = this.validTablePath + this.inputFileName + ".db";

        //if database exists, create a new one with an index
        File file = new File(filePath);
        if(file.exists()){
            System.out.println("Database already exists");
            System.out.println("Creating a new one");
            int index = 1;
            do{
                filePath = this.validTablePath + this.inputFileName + "(" + index + ").db";
                file = new File(filePath);
                ++index;
            }
            while(file.exists());
        }

        //url for database
        final String url = "jdbc:sqlite:" + filePath;
        final String tableName = "records";

        //create database
        try{
            Connection conn = DriverManager.getConnection(url);
            if(conn != null){
                //create table
                createSQLTable(conn);

                //disable auto-commit
                conn.setAutoCommit(false);

                //insert all the records into the table
                PreparedStatement insertStatement = conn.prepareStatement(insertSQL);
                //iterate through the table sequentially
                Record record = this.validTable.getFirst();
                while(record != null){
                    insertRecord(conn, insertStatement, record);
                    record = record.getNext();
                }
                //then commit
                conn.commit();
            }
            System.out.println("Success!");
            System.out.println("Output File: " + filePath + "\n");
        }
        catch(Exception e){
            System.out.println("Failed!");
            System.out.println(e.getMessage());
            System.out.println("Exiting");
            System.exit(1);
        }
    }

    //create a table using an established connection
    private void createSQLTable(Connection conn){
        //the table should have 10 attributes (A-J)
        //create table
        try{
            Statement statement = conn.createStatement();
            statement.execute(this.createTableSQL);
        }
        catch (SQLException e){
            System.out.println("Fail to create SQLite table");
            System.out.println(e.getMessage());
        }
    }

    //insert a record into the table using an established connection
    private void insertRecord(Connection conn, PreparedStatement insertStatement, Record record){
        try{
            //populate the row with values from record
            for(int i = 0; i < record.getSize(); i++){
                //setString starts the index at 1
                insertStatement.setString(i + 1, record.getData(i));
            }
            insertStatement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Fail to insert into SQLite row");
            System.out.println(e.getMessage());
        }
    }
}
