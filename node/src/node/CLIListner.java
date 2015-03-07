package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

public class CLIListner implements Runnable {
	private Thread thread;
	private static final Logger log = Logger.getLogger(CLIListner.class
			.getName());
	public final static CLIListner INSTANCE = new CLIListner();
	private QueryExecutor qe;

	private CLIListner() {
		qe = new QueryExecutor();

	}

	public void run() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String command = null;
		while (true) {
			System.out.print("node>>> ");
			try {
				command = br.readLine();
				if (command.equalsIgnoreCase("List")) {
					printFileList();
				} else if (command.equalsIgnoreCase("Search")) {
					System.out.println("Enter file name to search:");
					String searchName = br.readLine().replace(" ", "_");
					qe.fileSearch(searchName);
				} else {
					System.out
							.println("Incorrect command. Please enter a valid command");
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

	private void printFileList(){
		System.out.println("-----------File List-------------");
		List<String> list = qe.getFileList();
		for(String file:list){
			System.out.println(file);
		}
	}


}
