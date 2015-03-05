package node;

import java.util.LinkedList;
import java.util.List;

public class FileManager {
	public final static FileManager INSTANCE = new FileManager();
	private List<String> files = new LinkedList<String>();
	
	private FileManager(){
	}
	
	public synchronized List<String> getFiles(){
		return files;
	}
	
	public synchronized void add(String file){
		if(!files.contains(file)){
			files.add(file);
		}
	}
	public synchronized void remove(String file){
		if(file.contains(file)){
			files.remove(file);
		}
	}
	
	public synchronized LinkedList<String> find(String file){
		//TODO:search and return all the file names
		return null;
	}
}
