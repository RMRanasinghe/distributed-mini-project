package node;

import java.util.LinkedList;
import java.util.List;

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
		
		if (files.isEmpty()) {
			return null;
		} else {
			LinkedList<String> result =null;
			for (String afile : files) {
				int searchMeLength = afile.length();
				int findMeLength = file.length();
				for (int i = 0; i <= (searchMeLength - findMeLength); i++) {
					if (afile.regionMatches(true, i, file, 0, findMeLength)) {
						if(result ==null)
							result = new LinkedList<String>();
						result.add(afile);
						break;
					}
				}
			}
			return result;
		}

	}

}
