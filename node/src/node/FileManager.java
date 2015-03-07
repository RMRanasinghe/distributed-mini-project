package node;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManager {
	public final static FileManager INSTANCE = new FileManager();
	private List<String> files = new LinkedList<String>();

	public FileManager() {
	}

	public synchronized List<String> getFiles() {
		return files;
	}

	public synchronized void add(String file) {
		if (!files.contains(file)) {
			files.add(file);
		}
	}

	public synchronized void remove(String file) {
		if (file.contains(file)) {
			files.remove(file);
		}
	}

	public synchronized LinkedList<String> find(String file) {
		//TODO: modify this to work with underscore 
		if (files.isEmpty()) {
			return null;
		} else {
			LinkedList<String> result =null;
			LinkedList<String> searchFile =new LinkedList();
			Pattern pattern = Pattern.compile("\\w+");
			Matcher matcher = pattern.matcher(file);
			while (matcher.find()) {
			    searchFile.add(matcher.group());
			}
			for (String afile : files) {
				int count =0;
				LinkedList<String> afileList =new LinkedList();
				matcher = pattern.matcher(afile);
				while (matcher.find()) {
				    afileList.add(matcher.group());
				}
				for(String s: searchFile){
					for(String as: afileList){
						if(s.equalsIgnoreCase(as))
							count++;
					}
				}
				if (count==searchFile.size()){
					if(result ==null)
						result = new LinkedList<String>();
					result.add(afile);
					
				}
			}
			return result;
		}

	}

}
