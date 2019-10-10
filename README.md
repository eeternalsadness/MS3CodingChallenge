# MS3CodingChallenge

About the Applicaton
--------------------

This application takes an input CSV file that has 10 columns with column headers "A", "B", "C", "D", "E", "F", "G", "H", "I", and "J". It then parses the data and puts the valid records (records that have data in all 10 columns that correspond with the column headers in the CSV file) in an SQLite database with name <input-file-name>.db. The invalid records are stored in a CSV file in the same manner with name <input-file-name>-bad.csv. A log file is created to keep track of the number of records received, the number of valid records that gets stored in the database, and the number of records that are invalid.


Compile and Run
---------------

To compile and run the application in the command line, navigate to the folder containing MainApplication.java and use the following commands:

    javac -classpath ./ MainApplication.java
    java -classpath ./;./resources/sqlite-jdbc-3.27.2.1.jar MainApplication
    
    
Approach
--------

