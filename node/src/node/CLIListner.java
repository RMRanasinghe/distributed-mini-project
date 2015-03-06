package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

public class CLIListner implements Runnable {
	private Thread thread;
	private static final Logger log = Logger.getLogger(CLIListner.class
			.getName());
	public final static CLIListner INSTANCE = new CLIListner();
	private Properties properties = null;
	private String fileList;
	private QueryGenerator qg;
	private QueryExecutor qe;
	
	
	private CLIListner() {
		PropertyLoader propertyLoader = new PropertyLoader();
		properties = propertyLoader.getProperties();
		fileList = properties.getProperty("node.filelist");
		qg= new QueryGenerator();
		qe = new QueryExecutor();
		
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
					searchFile(searchName,properties.getProperty("node.internet.address"),Integer.parseInt(properties.getProperty("node.internet.port")),10);
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
	private void searchFile(String fileName,String IP,int port,int hops){
		Random rand = new Random();
		String messageId = IP+rand.nextInt(100);
		String searchQuery = qg.getSearch(IP, port, fileName, hops, messageId);
		qe.search(searchQuery,fileName);
		//TO DO: handle incoming search results message
		
	}

}
