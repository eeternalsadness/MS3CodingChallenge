/*
 * This data structure is a LinkedList of records that holds the first and last record.
 * Optimized for sequential performance by reducing overhead (eg. ArrayList).
 */

package dataStructures;

public class DataTable{
	private Record firstRecord;
	private Record lastRecord;
	private int size;

	public DataTable(){
		this.firstRecord = null;
		this.lastRecord = null;
		this.size = 0;
	}

	//get the first record in the table
	public Record getFirst(){
		return this.firstRecord;
	}

	//add a record to the end of the table
	public void addRecord(Record record){
		//if the table is empty, add the record as the first record
		if(isEmpty()){
			this.firstRecord = record;
			this.lastRecord = record;
		}
		//else, set the added record as the last record
		else{
			this.lastRecord.setNext(record);
			this.lastRecord = record;
		}
		++size;
	}

	public int getSize(){
		return size;
	}

	public boolean isEmpty(){
		return size == 0;
	}
}