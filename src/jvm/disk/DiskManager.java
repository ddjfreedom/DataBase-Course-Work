package disk;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
	private static int creatFileEntry =0;
	private int [] pageAccessTable = new int [tableLength];
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
			flag = pageAccessTable[i];
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
		pageAccessTable[pageNum] = mark;
	}
	
	public void createDisk(String diskName){
		try {
			disk = new RandomAccessFile(diskName,"rw");
			byte []diskInit = new byte [diskSize];
			
			Arrays.fill(diskInit, zero); 
			disk.seek(0);
			disk.write(diskInit); //fill the disk with zero
			disk.seek(0);
			for (int i = 0; i< tableSize/pageSize; i++){
				disk.write(dp, 0, 4);  //pageAccessTable occupies 200 pages
			}			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openDB(){
		try {
			disk.seek(0);
			int flag;
			byte[] pageFlag = new byte[4];
			for (int i = 0; i< tableLength; i++){
				disk.read(pageFlag, 0, 4);   //read Table from disk to memory
				flag = byteToInt(pageFlag);
				pageAccessTable[i]=flag;  
			}	
			if(creatFileEntry ==0){
				createFile("fileEntry.sys"); //creat fileEntry as a systemfile 
				creatFileEntry =1;
			}
			StringBuffer sb = new StringBuffer();
			sb=readFile("fileEntry.sys");	//read fileEntry from disk to memory
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
				fileEntry.put(fileName, pageNum);  //use hashmap to accelerate search
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeDB(){
		try {
			disk.seek(0);
			int flag;
			byte[] pageFlag = new byte[4];
			for (int i = 0; i< tableLength; i++){
				flag = pageAccessTable[i];
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
			writeFile("fileEntry.sys",sb); //write FileEntry back to fileEntry.sys on disk
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean createFile(String fileName){
		int pageEntry = -1;
		pageEntry = getFreePageNum();  //find the fileEntryPage
		fileEntry.put(fileName, pageEntry); //put to hashmap
		markPage(pageEntry,finalPage);
		if (pageEntry != -1)
			return true;
		else
			return false;
	}
	
	public void openFile(String fileName){
		if(fileEntry.containsKey(fileName)){
			openFile.add(fileName);
		}
		else
			System.out.println("FILE NOT EXIST!");
	}
	
	
	
	public void writeFile(String fileName, StringBuffer content){
		if(openFile.contains(fileName)){
			int pageEntry = fileEntry.get(fileName);
			int nextEntry = pageEntry;
			int prevEntry = pageEntry;
			for(int i = 0; i < content.length(); i = i +pageSize){ //write to disk in page size
				byte [] page = new byte[pageSize];
				page = splitStringToByte(content,i); // split content into page size
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
			System.out.println("FILE NOT OPEN!");
	}
	
	public StringBuffer readFile(String fileName){
		StringBuffer rs= new StringBuffer();
		if(fileEntry.containsKey(fileName)){
			int pageEntry = fileEntry.get(fileName);
			int nextEntry = pageEntry;
			byte [] page = new byte[pageSize];
			String s;
			for(int i = pageEntry; nextEntry != finalPage;){ //read file in page size
				readPage(nextEntry, page);
				s = new String(page);
				rs.append(s);
				nextEntry = pageAccessTable[i];
				i = nextEntry;
			}
			return rs;
		}
		else{
			System.out.println("FILE NOT EXIST!");
			rs = null;
			return rs; //means error
		}
	}
	
	public void deleteFile(String fileName){
		ArrayList <Integer> tmp = new ArrayList<Integer>();
		if(fileEntry.containsKey(fileName)){
			int pageEntry = fileEntry.get(fileName);
			int nextEntry = pageEntry;
			for(int i = pageEntry; nextEntry != finalPage;){
				tmp.add(nextEntry);
				cleanPage(nextEntry);
				nextEntry = pageAccessTable[i];
				i = nextEntry;
			}
			for(int i = 0; i< tmp.size(); i ++){
				markPage(tmp.get(i), freePage); // mark all the delete pages free
			}
		}
		else
			System.out.println("FILE NOT EXIST!");
	}
	

	
/*	public static void main(String args[]) throws IOException{
		DiskManager dm = new DiskManager();
		
		dm.createDisk("abc");
		dm.openDB();
		dm.createFile("a");
//		dm.closeDB();
//		
//		System.out.println("r "+dm.readFile("fileEntry"));
//		dm.openDB();
		
//		dm.readFile("a");
		dm.openFile("a");
		StringBuffer test =new StringBuffer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccdddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeezzzzzzzzzzzzzzzzzzzzzzqqq");
//		StringBuffer test = new StringBuffer("abc");
		System.out.println(test.length());
		dm.writeFile("a",test );
		StringBuffer s = dm.readFile("a");
		System.out.println(s);

		dm.createFile("b");
		dm.openFile("b");
		StringBuffer t = new StringBuffer("abc");
		dm.writeFile("b", t);
		StringBuffer ss = dm.readFile("b");
		System.out.println("@@@@"+ss);
	
//		dm.openDB();
//		System.out.println(dm.getFreePageNum());
//		System.out.println(s.equals(test));
//		byte []b = new byte[4];
//		b =  intToByte (-2);
//		byte []page = new byte [1024];
//		for (int i=0;i*4<1024;i++){
//			
//		}
		System.out.println("da "+dm.pageAccessTable[200]);
		System.out.println("da "+dm.pageAccessTable[201]);
		System.out.println("da "+dm.pageAccessTable[202]);
		System.out.println("da "+dm.pageAccessTable[203]);
		dm.deleteFile("a");

		System.out.println(dm.getFreePageNum());
	}
*/

}