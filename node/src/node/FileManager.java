package node;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManager {
	public final static FileManager INSTANCE = new FileManager();
	private List<String> files = new LinkedList<String>();
	PropertyLoader propertyLoader = new PropertyLoader();
	Properties properties = propertyLoader.getProperties();

	public FileManager() {
		String fileList = properties.getProperty("node.filelist");
		fileList = fileList.replace(" ", "_");
		String[] fileArray = fileList.split(",");
		for (String file : fileArray) {
			files.add(file);
		}
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
		LinkedList<String> result = new LinkedList<String>();

		if (files.isEmpty()) {
			return null;
		} else {
			Pattern p = Pattern.compile("(.*_)?"+file.toLowerCase()+"(_(.*))?");
			for(String afile:files){
				Matcher m = p.matcher(afile.toLowerCase());
				if(m.matches()){
					result.add(afile);
				}
			}
			
		}

		return result;
	}

}
