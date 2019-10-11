# MS3CodingChallenge

About the Applicaton
--------------------

This application takes an input CSV file that has 10 columns with column headers "A", "B", "C", "D", "E", "F", "G", "H", "I", and "J". It then parses the data and puts the valid records (records that have data in all 10 columns that correspond with the column headers in the CSV file) in an SQLite database with name <input-file-name>.db. The invalid records are stored in a CSV file in the same manner with name <input-file-name>-bad.csv. A log file is created to keep track of the number of records received, the number of valid records that gets stored in the database, and the number of records that are invalid.


Build and Run
---------------

To build and run the application using Maven, navigate to the project folder (the folder containing the src folder) and use the following commands:

Compile:

    mvn compile

Build project:

    mvn install
    
Copy dependency (the application makes use of org.xerial.sqlite-jdbc-3.28.0.jar):

    mvn dependency:copy-dependencies

Run:

    java -cp /target/MS3CodingChallenge-1.0-SNAPSHOT.jar; /target/dependency/sqlite-jdbc-3.28.0.jar MainApplication
    
A sample CSV file is included in the repository for testing.


Approach
--------

1. Overview:

The application reads the data from the CSV file line-by-line and value-by-value, storing each line in an array of Strings. Every time a value is added to the array, it is checked to see if it is empty. If it is, the whole record becomes invalid. This check during parsing helps reduce extra iterations if the records are checked later. After a record is checked, it is immediately put into a valid data table or an invalid data table based on its validity. An SQLite database is then created using the valid data table, and a CSV file is created using the invalid table. A log file is then created using the number of elements in each table.


2. Data Structures:

The data structure of a record is a String array of size 10. This works on the assumption that the input file is a table that has its data contained in 10 column, which means that if a row has more than 10 values, only the first 10 will be stored in the array. The decision to use an array instead of an ArrayList (even though the default size of an ArrayList is 10) is for slightly better performance and consistency between the Record objects. 

The data structure of a table holding multiple records is similar to a linked list. Since the main operations of the application are reading and writing data, data should only be accessed sequentially. Hence, a linked list provides superior performance in add operations when populating the data tables, especially for large datasets (since ArrayList has to expand its size every time it overflows). There is no random access, so the sequential performance is similar to that of ArrayList.


3. I/O:

Since the application has to read and write a lot of files, there is potential for IO errors. Hence, a file dialog is used to make sure that the files and the directories are valid, eliminating the need for error handling. The application also makes use of a config.properties file that stores the default paths for the output files, in case the user did not specify them. This is done to reduce redundancy in prompting the user for default paths.

Apart from the data structures speeding up the interation through the data, the SQLite queries are also optimized. The application uses a PreparedStatement to queue up the insert statements, only commiting once all the insert statements have been created. This significantly reduces the overhead when auto-committing after every insert statement.

Other Thoughts
--------------

The application could be programmed so that it is entirely contained in a GUI, but that would make the code more complex for something that only does a single task. Instead, the application uses a combination of both the console/command line and a GUI, with the console/command line acting as a debugger that tells the user what the application is doing.
