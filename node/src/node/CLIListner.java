package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import node.thrift.QueryServiceImpl;

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
			try {
				command = br.readLine();
				if (command.equalsIgnoreCase("List")) {
					printFileList();
				} else if (command.equalsIgnoreCase("Search")) {
					System.out.println("Enter file name to search:");
					String searchName = br.readLine().replace(" ", "_");
					qe.fileSearch(searchName);
				} else if (command.equalsIgnoreCase("RPCSearch")) {
					System.out.println("Enter file name to search:");
					String searchName = br.readLine().replace(" ", "_");
					qe.RPCFileSearch(searchName);

				} else if (command.equalsIgnoreCase("Neighbours")) {
					printNeighbourList();
				}else if(command.equalsIgnoreCase("Leave")){
					sendLeave();
				}else if(command.equalsIgnoreCase("RPCLeave")){
					RPCsendLeave();
				}else if(command.equalsIgnoreCase("Help")){
					printHelp();
				}else {
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
	private void printHelp(){
		System.out.println("--------------------Command List--------------------");
		System.out.println("1. Neighbours	- show peer table");
		System.out.println("2. List		- show file list within node");
		System.out.println("3. Search		- Enter file search mode");
		System.out.println("4. RpcSearch	- Enter RPC file search mode");
		System.out.println("5. Leave		- leave from network");
		System.out.println("6. RpcLeave		- leave from network using RPC");
		System.out.println("----------------------------------------------------");
	}
	private void printFileList() {
		System.out.println("-----------File List-------------");
		List<String> list = qe.getFileList();
		for (String file : list) {
			System.out.println(file);
		}
	}

	private void printNeighbourList() {
		System.out.println("-----------Routing Table-------------");
		Set<RoutingTableEntry> table = RoutingTable.INSTANCE.get();
		for (RoutingTableEntry rt : table) {
			System.out.println("IP : " + rt.IP + " Port : " + rt.port);
		}
	}
	private void sendLeave(){
		Set<RoutingTableEntry> table = RoutingTable.INSTANCE.get();
		for(RoutingTableEntry rt : table){
			qe.sendLeave(rt.IP, rt.port);
		}
		qe.sendLeaveToBS();
		try {
			thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
	}
	private void RPCsendLeave(){
		Set<RoutingTableEntry> table = RoutingTable.INSTANCE.get();
		for(RoutingTableEntry rt : table){
			qe.RPCsendLeave(rt.IP, rt.port);
		}
		qe.sendLeaveToBS();
		try {
			thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
	}

}
