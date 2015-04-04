package node;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

public class QueryExecutor {

	private RoutingTable routingTable;
	private NodeCommunicator nodeCommunicator;
	private QueryGenerator queryGenerator;
	private FileManager fileManager;
	private PropertyLoader propertyLoader;
	private Properties properties;
	private String nodeIP;
	private int nodePort;
	private BoundedMessageIDBuffer sentIds;

	public QueryExecutor() {
		routingTable = RoutingTable.INSTANCE;
		nodeCommunicator = NodeCommunicator.INSTANCE;
		queryGenerator = new QueryGenerator();
		fileManager = new FileManager();
		propertyLoader = new PropertyLoader();
		properties = propertyLoader.getProperties();
		nodeIP = properties.getProperty("node.internet.address");
		nodePort = Integer.parseInt(properties
				.getProperty("node.internet.port"));
		sentIds = BoundedMessageIDBuffer.INSTANCE;
	}

	public void regOKSuccess(String ip, int port) {
		RoutingTableEntry entry = new RoutingTableEntry(ip, port);
		routingTable.add(entry);

		nodeCommunicator.send(ip, port,
				queryGenerator.getJoin(nodeIP, nodePort));
	}

	public void regOKSuccess(String ip1, int port1, String ip2, int port2) {

		RoutingTableEntry entry1 = new RoutingTableEntry(ip1, port1);
		RoutingTableEntry entry2 = new RoutingTableEntry(ip2, port2);
		routingTable.add(entry1);
		routingTable.add(entry2);

		nodeCommunicator.send(ip1, port1,
				queryGenerator.getJoin(nodeIP, nodePort));
		nodeCommunicator.send(ip2, port2,
				queryGenerator.getJoin(nodeIP, nodePort));
	}

	public void join(String ip, int port) {
		RoutingTableEntry entry = new RoutingTableEntry(ip, port);
		routingTable.add(entry);

		nodeCommunicator.send(ip, port, queryGenerator.getJoinOK(0));
	}

	public void leave(String ip, int port) {
		routingTable.remove(ip, port);

		nodeCommunicator.send(ip, port, queryGenerator.getLeaveOK(0));
	}

	public void fileSearch(String fileName) {
		int maxHops = Integer.parseInt(properties.getProperty("max.ttl"));
		Random rand = new Random();
		int id = rand.nextInt();// The probability of getting same number again
								// is low
		fileSearch(fileName, nodeIP, nodePort, id, maxHops);
	}

	public void fileSearch(String fileName, String ip, int port, int id,
			int hops) {
		if (sentIds.contains(id)) {
			return;
		}
		sentIds.add(id);
		LinkedList<String> fileNames = fileManager.find(fileName);
		if (!fileNames.isEmpty()) {
			String responseQuery = queryGenerator.getSearchOK(nodeIP, nodePort,
					fileNames, hops - 1);
			nodeCommunicator.send(ip, port, responseQuery);
		} else {
			if (hops != 0) {
				String searchQuery = queryGenerator.getSearch(ip, port,
						fileName, hops - 1, id);
				Set<RoutingTableEntry> connectedNodes = routingTable.get();
				for (RoutingTableEntry connectedNode : connectedNodes) {
					nodeCommunicator.send(connectedNode.IP, connectedNode.port,
							searchQuery);
				}
			}
		}
	}

	public List<String> getFileList() {
		return fileManager.getFiles();
	}

	public void fileFound(String ip, int port, List<String> files, int hops) {
		System.out.println("File(s) found:");
		System.out.print("File names: ");
		for (String file : files) {
			System.out.print(file + " ");
		}
		System.out.println();
		System.out.println("At ip: " + ip + "port: " + port + " within " + hops
				+ " number of hops");
		System.out.print("node>>>");
	}
	
	//Thrift methods
	
	
}
