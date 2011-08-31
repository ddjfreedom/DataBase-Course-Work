package disk;

import java.util.ArrayList;
import java.util.List;

public class RecordTable {
	private String tableName;
	private String metaData;
	private List<String> records;
	private DiskManager disk;
	
	public RecordTable(String tableName, String metaData, List<String> records){
		this.setTableName(tableName);
		this.setMetaData(metaData);
		this.setRecords(records);
		initializeDisk();
	}
	
	public RecordTable(String tableName){
		this.setTableName(tableName);
		this.setMetaData(null);
		this.setRecords(null);
		initializeDisk();
	}
	
	public RecordTable(String tableName, String metaData){
		this.setTableName(tableName);
		this.setMetaData(metaData);
		this.setRecords(null);
		initializeDisk();
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setRecords(List<String> records) {
		this.records = records;
	}

	public List<String> getRecords() {
		return records;
	}
	
	public String getRecord(int index){
		return records.get(index);
	}

	public void setRecord(int index, String record){
		records.set(index, record);
	}
	
	protected void initializeDisk(){
		disk = DiskManager.getInstance();
	}
	
	/**
	 * write current record table to disk
	 */
	public void writeToDisk(){
		StringBuffer file = new StringBuffer(metaData);
		disk.createFile(tableName);
		disk.openFile(tableName);
		for(String record : records){
			file.append("%s%");
			file.append(record);
		}
		file.append("%end%");
		disk.writeFile(tableName, file);
	}
	
	/**
	 * read from disk
	 * @param tableName
	 */
	public void readFromDisk(){
		disk.openFile(tableName);
		StringBuffer file = disk.readFile(tableName);
		String content  = file.toString().split("%end%")[0];
		String [] filePart = content.split("%s%");
		int size = filePart.length;
		setMetaData(filePart[0]);
		List<String> records = new ArrayList<String>();
		for(int i = 2; i < size; ++i){
			records.add(filePart[i]);
		}
		setRecords(records);
	}
	
	public void deleteTable(){
		disk.deleteFile(tableName);
	}
	
	public String toString(){
		String temp = new String();
		temp += tableName;
		temp += "\n" + metaData;
		for(int i = 0; i < records.size(); ++i){
			temp += "\n" + "record:" + records.get(i);
		}
		return temp;
	}
	
	public static void main(String [] args){
		DiskManager disk = DiskManager.getInstance();
		disk.openDB();
		ArrayList<String> records = new ArrayList<String>();
		for(int i = 0; i < 5000; ++i){
			records.add("record " + i);	
		}
		
		RecordTable table = new RecordTable("aTable", "metadata", records);
		System.out.println(table);
		table.writeToDisk();
		RecordTable table2 = new RecordTable("aTable");
		table2.readFromDisk();
		System.out.println(table2);
		table2.setRecord(9, "changed record");
		table2.writeToDisk();
		RecordTable table3 = new RecordTable("aTable");
		table3.readFromDisk();
		System.out.println(table3);
		disk.closeDB();
	}
}
