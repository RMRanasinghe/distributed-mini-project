package node;

import java.util.Properties;
import java.util.Set;


public class QueryExecutor {

	RoutingTable routingTable = RoutingTable.INSTANCE;
	NodeCommunicator nodeCommunicator = NodeCommunicator.INSTANCE;
	QueryGenerator queryGenerator = new QueryGenerator();
	
	PropertyLoader propertyLoader = new PropertyLoader();
	Properties properties = propertyLoader.getProperties();	
	String nodeIP = properties.getProperty("node.internet.address");
	int nodePort = Integer.parseInt(properties
			.getProperty("node.internet.port"));
	
	public void regOKSuccess(String ip, int port) {		
		RoutingTableEntry entry = new RoutingTableEntry(ip, port);
		routingTable.add(entry);
		
		nodeCommunicator.send(ip, port,queryGenerator.getJoin(nodeIP, nodePort));
	}

	public void regOKSuccess(String ip1, int port1,
			String ip2, int port2) {
		
		RoutingTableEntry entry1 = new RoutingTableEntry(ip1, port1);
		RoutingTableEntry entry2 = new RoutingTableEntry(ip2, port2);
		routingTable.add(entry1);
		routingTable.add(entry2);

		nodeCommunicator.send(ip1, port1,queryGenerator.getJoin(nodeIP, nodePort));
		nodeCommunicator.send(ip2, port2,queryGenerator.getJoin(nodeIP, nodePort));
	}
	
	public void join(String ip, int port){
		RoutingTableEntry entry = new RoutingTableEntry(ip, port);
		routingTable.add(entry);
		
		nodeCommunicator.send(ip, port, queryGenerator.getJoinOK(0));
	}
	
	public void leave(String ip, int port){
		routingTable.remove(ip,port);
		
		nodeCommunicator.send(ip, port, queryGenerator.getLeaveOK(0));
	}
	public void search(String message,String fileName){
		//TO DO: handle the filename result and forward or respond
		
		Set<RoutingTableEntry> connectedNodes=routingTable.get();
		for(RoutingTableEntry connectedNode:connectedNodes){
		nodeCommunicator.send(connectedNode.IP, connectedNode.port, message);
		}
	}
}
