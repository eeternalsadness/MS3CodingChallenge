/*
 * This enum hold the 3 types of output file to be used as parameters.
 */

package io;

public enum OutputFileType {
    OUTPUT_SQLITE_DATABASE {
        @Override
        public String toString() {
            return "Output SQLite Database";
        }
    },
    OUTPUT_CSV_FILE{
        @Override
        public String toString() {
            return "Output CSV File";
        }
    },
    OUTPUT_LOG_FILE{
        @Override
        public String toString() {
            return "Output Log File";
        }
    },
    ;
}