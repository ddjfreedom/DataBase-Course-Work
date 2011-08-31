package disk;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DiskManager {
	
	private RandomAccessFile disk;
	private static byte zero = 00000000;
	private static int tableSize = 50*1024*4;
	private static int tableLength = 50*1024;
	private static int pageSize = 1024;
	private static int diskSize = 50*1024*1024;
	private static int freePage = 0;
	private static int dirtyPage = -1;
	private static byte [] dp = intToByte(dirtyPage);
	private static int finalPage = -2;
	private static int fileEntryPageNum = 200;
	private static int creatFileFlag =0;
	private int [] pageAllocateTable = new int [tableLength];
	private HashMap <String,Integer> fileEntry = new HashMap <String, Integer>();
	private ArrayList <String> openFile = new ArrayList <String>();

	public static byte [] intToByte (int i){
		byte[] b = new byte[4];
		b[0] = (byte) i;
		b[1] = (byte) (i >> 8);
		b[2] = (byte) (i >> 16);
		b[3] = (byte) (i >> 24);
		return b;
	}
	
	public static int byteToInt (byte []b){
		int i = 0xff & b[0];
		i |= (b[1] << 8) & 0xff00;
		i |= (b[2] << 16) & 0xff0000;
		i |= (b[3] << 24) & 0xff000000;
		return i;
	}
	
	public byte[] splitStringToByte (StringBuffer s, int from){
		byte [] b = new byte[pageSize];
		if((s.length()-from)<=pageSize){
			String tmp = s.substring(from, s.length());
			b = tmp.getBytes();
		}
		else{
			String tmp = s.substring(from, from+pageSize);
			b = tmp.getBytes();
		}
		return b;
	}

	public int getFreePageNum(){
		int flag;
		int freePageNum = -1;
		for (int i = 0; i< tableLength; i++){
			flag = pageAllocateTable[i];
			if(flag == 0){
				freePageNum = i;
				break;
			}
		}
		return freePageNum; //if freePageNum == -1 mean no more free page
	}
	
	public void cleanPage(int pageNum){
		byte [] fp = new byte [pageSize];
		Arrays.fill(fp, zero); 
		try {
			disk.seek(pageNum*pageSize);
			disk.write(fp);//fill page with zero
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void writePage(int pageNum, byte page[]){
		try {
			disk.seek(pageNum*pageSize);
			disk.write(page, 0, page.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void readPage(int pageNum, byte page[]){
		try {
			disk.seek(pageNum*pageSize);
			disk.read(page,0,pageSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void markPage(int pageNum,int mark){
		pageAllocateTable[pageNum] = mark;
	}
	
	public void createDisk(String diskName){ //initialize the disk
		try {
			disk = new RandomAccessFile(diskName,"rw");
			byte []diskInit = new byte [diskSize];
			Arrays.fill(diskInit, zero); 
			disk.seek(0);
			disk.write(diskInit); //fill the disk with zero
			disk.seek(0);
			
			for (int i = 0; i< tableSize/pageSize+1; i++){
				disk.write(dp, 0, 4);  //table occupies 200 pages and fileEntry.sys occupies 1 pages at first
			}			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openDB(){ //read pageAllocateTable and FileEntry from disk
		try {
			disk.seek(0);
			int flag;
			byte[] pageFlag = new byte[4];
			for (int i = 0; i< tableLength; i++){
				disk.read(pageFlag, 0, 4);   //read Table from disk to memory
				flag = byteToInt(pageFlag);
				pageAllocateTable[i]=flag;  
			}	
			if(creatFileFlag ==0){
				fileEntry.put("fileEntry.sys", fileEntryPageNum);
				markPage(fileEntryPageNum,finalPage);   // store fileEntry map as a file
				creatFileFlag =1;
			}
			StringBuffer sb = new StringBuffer();
			sb=readFile("fileEntry.sys");
			String fileName = new String();
			int pageNum = 0;
			String[]ss=sb.toString().split("/");
			for(int i =0 ; i<ss.length-1;i++){
				if(i == 0|i%2==0){
					fileName=ss[i];
				}
				else{
					pageNum=Integer.parseInt(ss[i]);
				}
				fileEntry.put(fileName, pageNum);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeDB(){ //write pageAllocateTable and FileEntry to disk
		try {
			disk.seek(0);
			int flag;
			byte[] pageFlag = new byte[4];
			for (int i = 0; i< tableLength; i++){
				flag = pageAllocateTable[i];
				pageFlag= intToByte(flag);
				disk.write(pageFlag, 0, 4);   //write Table from memory to disk
			}
			StringBuffer sb = new StringBuffer();
			String fileName =new String();
			String pageNum =new String();
			
			Iterator it = fileEntry.entrySet().iterator();   
		    while (it.hasNext()) {   
		    	Map.Entry entry = (Map.Entry) it.next();   
		        fileName = (String) entry.getKey(); 
		        pageNum = entry.getValue().toString();
		        sb.append(fileName).append("/").append(pageNum).append("/");
		    }
		    openFile("fileEntry.sys");
			writeFile("fileEntry.sys",sb);
			fileEntry.clear();
			fileEntry.put("fileEntry.sys", 200); // fileEntry.sys always begin at 200 page
			openFile.clear(); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean createFile(String fileName){
		if(fileEntry.containsKey(fileName)){
			System.out.println("CAN'T CREAT, RENAME THE FILE!");
			return false;
		}
		else{
			int pageEntry = -1;
			pageEntry = getFreePageNum();   //find the file page entry
			fileEntry.put(fileName, pageEntry);
			markPage(pageEntry,finalPage);
			if (pageEntry != -1)
				return true;
			else
				return false;
		}
		
	}
	
	public void openFile(String fileName){
		if(fileEntry.containsKey(fileName)){
			openFile.add(fileName);  // add file to openfile list. Only open file cant be written
		}
		else
			System.out.println("CAN'T OPEN "+fileName+" , FILE NOT EXIST!");
	
	}
	
	public void writeFile(String fileName, StringBuffer content){
		if(openFile.contains(fileName)&&fileEntry.containsKey(fileName)){
			int pageEntry = fileEntry.get(fileName);
			int nextEntry = pageEntry;
			int prevEntry = pageEntry;
			for(int i = 0; i < content.length(); i = i +pageSize){
				byte [] page = new byte[pageSize];
				page = splitStringToByte(content,i);
				if(i == 0){
					writePage(pageEntry,page);
					markPage(pageEntry,dirtyPage);
				}
				else{
					prevEntry = nextEntry;
					nextEntry = getFreePageNum();
					writePage(nextEntry,page);
					markPage(prevEntry,nextEntry);
					markPage(nextEntry,dirtyPage);
				}
			}
			markPage(nextEntry,finalPage);
		}
		else
			System.out.println("CAN'T WRITE "+fileName);
	}
	
	public StringBuffer readFile(String fileName){
		StringBuffer rs= new StringBuffer();
		if(fileEntry.containsKey(fileName)){
			int pageEntry = fileEntry.get(fileName);
			int nextEntry = pageEntry;
			byte [] page = new byte[pageSize];
			String s;
			for(int i = pageEntry; nextEntry != finalPage;){  // read file in page size
				readPage(nextEntry, page);
				s = new String(page);
				rs.append(s);
				nextEntry = pageAllocateTable[i];
				i = nextEntry;
			}
			return rs;
		}
		else{
			System.out.println("FILE "+fileName+" NOT EXIST, CAN'T READ!");
			rs = null;
			return rs; //rs equals null means error
		}
	}
	
	public void deleteFile(String fileName){
		ArrayList <Integer> tmp = new ArrayList<Integer>();
		if(fileEntry.containsKey(fileName)){
			int pageEntry = fileEntry.get(fileName);
			int nextEntry = pageEntry;
			for(int i = pageEntry; nextEntry != finalPage;){
				tmp.add(nextEntry);
//				cleanPage(nextEntry);
				nextEntry = pageAllocateTable[i];
				i = nextEntry;
			}
			for(int i = 0; i< tmp.size(); i ++){
				markPage(tmp.get(i), freePage);  //mark all the delete pages free
			}
			fileEntry.remove(fileName);//remove from hashmap fileEntry
		}
		else
			System.out.println("CAN'T DELETE "+fileName+" , FILE NOT EXIST!");
	}
	
}

	