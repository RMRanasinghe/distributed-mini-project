package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class CLIListner implements Runnable {
	private Thread thread;
	private static final Logger log = Logger.getLogger(CLIListner.class
			.getName());
	public final static CLIListner INSTANCE = new CLIListner();
	private Properties properties = null;
	private String fileList;
	private NodeCommunicator nodeCommunicator;
	
	private CLIListner() {
		PropertyLoader propertyLoader = new PropertyLoader();
		properties = propertyLoader.getProperties();
		fileList = properties.getProperty("node.filelist");
		nodeCommunicator = NodeCommunicator.INSTANCE;
		
	}

	public void run() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String command = null;
		while (true) {
			System.out.print("node>>> ");
			try {
				command = br.readLine();
				if(command.equalsIgnoreCase("List")){
					getFileList();
				}else if(command.equalsIgnoreCase("Search")){
					System.out.println("Enter file name to search:");
					String searchName=br.readLine();
					searchFile(searchName);
				}else{
					System.out.println("Incorrect command. Please enter a valid command");	
				}
			} catch (IOException ioe) {
				System.out.println("Error getting input command. Retry");
				break;
			}
		}
	}

	public void start() {
		log.info("Starting command line interface listner");
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	private void getFileList(){
		System.out.println("-----------File List-------------");
		List<String> list = new ArrayList<String>(Arrays.asList(fileList.split(",")));
		for(String file:list){
			System.out.println(file);
		}
	}
	private void searchFile(String fileName){
		//TODO:send message to other connected nodes
	}

}
