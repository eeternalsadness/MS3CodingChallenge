/*
 * This data structure holds up to 10 values in an array of size 10 to optimize performance.
 * Assume no record holds more than 10 values.
 * Data structure is similar to a node in a LinkedList for better sequential performance.
 * The default value for isValid is set to true for simplicity in implementation.
 * The validity of the record is checked upon setting the data to minimize number of steps.
 */

package dataStructures;

public class Record{
	private final int size;
	private String[] record;
    private Record nextRecord;
	private boolean isValid;

	public Record(){
	    this.size = 10;
	    this.record = new String[size];
		this.nextRecord = null;
		this.isValid = true;
	}

	//get the data from record at a specific index
	public String getData(int column) throws ArrayIndexOutOfBoundsException{
		//if the stored data is null, return an empty string
		if(this.record[column] == null){
			return "";
		}
		return this.record[column];
	}

	//set the value for the data at a specific index
	public boolean setData(int column, String data){
		try{
		    this.record[column] = data;
            //if the data is empty, the record is invalid
			//assume no data contains just whitespaces
            if (data.isEmpty()){
                this.isValid = false;
            }

            return true;
        }
		catch(ArrayIndexOutOfBoundsException e){
		    return false;
        }
	}

	//get the next record in the table
	public Record getNext(){
		return nextRecord;
	}

	//set the next record in the table
	public boolean setNext(Record record){
		this.nextRecord = record;
		return true;
	}

	public int getSize(){
	    return this.size;
    }

    //return true if the record is valid
	public boolean isValid(){
		return this.isValid;
	}
}