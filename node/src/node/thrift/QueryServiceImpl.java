package node.thrift;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import node.BoundedMessageIDBuffer;
import node.FileManager;
import node.PropertyLoader;
import node.RoutingTable;
import node.RoutingTableEntry;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class QueryServiceImpl implements QueryService.Iface {
	private RoutingTable routingTable;
	private String nodeIP;
	private int nodePort;
	private PropertyLoader propertyLoader;
	private Properties properties;
	private FileManager fileManager;
	private BoundedMessageIDBuffer sentIds;
	public QueryServiceImpl(){
		routingTable = RoutingTable.INSTANCE;
		propertyLoader = new PropertyLoader();
		fileManager = new FileManager();
		properties = propertyLoader.getProperties();
		nodeIP = properties.getProperty("node.internet.address");
		nodePort = Integer.parseInt(properties
				.getProperty("node.internet.port"));
		sentIds = BoundedMessageIDBuffer.INSTANCE;
	}
	@Override
	public void regOKSuccess1(String ip, int port) throws TException {
		RoutingTableEntry entry = new RoutingTableEntry(ip, port);
		routingTable.add(entry);
		
		TTransport transport;
        transport = new TSocket(ip, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        QueryService.Client client = new QueryService.Client(protocol);
        client.join(nodeIP, nodePort);
		
	}

	@Override
	public void regOKSuccess2(String ip1, int port1, String ip2, int port2)
			throws TException {
		RoutingTableEntry entry1 = new RoutingTableEntry(ip1, port1);
		RoutingTableEntry entry2 = new RoutingTableEntry(ip2, port2);
		routingTable.add(entry1);
		routingTable.add(entry2);
		
		TTransport transport1,transport2;
        transport1 = new TSocket(ip1, port1);
        transport2 = new TSocket(ip2, port2);
        transport1.open();
        transport2.open();
        TProtocol protocol1 = new TBinaryProtocol(transport1);
        TProtocol protocol2 = new TBinaryProtocol(transport2);
        QueryService.Client client1 = new QueryService.Client(protocol1);
        QueryService.Client client2 = new QueryService.Client(protocol2);
        client1.join(nodeIP, nodePort);;
        client2.join(nodeIP, nodePort);
		
	}

	@Override
	public void join(String ip, int port) throws TException {
		RoutingTableEntry entry = new RoutingTableEntry(ip, port);
		routingTable.add(entry);
		
		
	}

	@Override
	public void leave(String ip, int port) throws TException {
		routingTable.remove(ip, port);
		
	}


	@Override
	public void fileSearch(String fileName, String ip, int port, int id,int hops)
			throws TException {
			if (sentIds.contains(id)) {
				return;
			}sentIds.add(id);
			LinkedList<String> fileNames = fileManager.find(fileName);
			if (!fileNames.isEmpty()) {
				System.out.println("f1");
				fileFound(fileNames,ip, port,nodeIP,nodePort,id, hops-1);
				System.out.println("f2");
			} else {
				if (hops != 0) {
					//String searchQuery = queryGenerator.getSearch(ip, port,
							//fileName, hops - 1, id);
					Set<RoutingTableEntry> connectedNodes = routingTable.get();
					TTransport transport;
					for (RoutingTableEntry connectedNode : connectedNodes) {
						//nodeCommunicator.send(connectedNode.IP, connectedNode.port,
								//searchQuery);
						if(nodeIP!=connectedNode.IP && nodePort!=connectedNode.port){
						transport = new TSocket(connectedNode.IP, connectedNode.port);
				        transport.open();
				        TProtocol protocol = new TBinaryProtocol(transport);
				        QueryService.Client client = new QueryService.Client(protocol);
				        //System.out.println("IP "+connectedNode.IP+"  "+"PORT "+connectedNode.port);
				        client.fileSearch(fileName,ip, port,id,hops-1);
				        transport.close();
						}
					}
				}
			}
			
		}
	@Override
	public void fileFound(List<String> fileList, String searchIp,
			int searchPort, String foundIp, int foundPort, int id, int hops)
			throws TException {
		System.out.println("File(s) found:");
		System.out.print("File names: ");
		for (String file : fileList) {
			System.out.print(file + " ");
		}
		System.out.println();
		System.out.println("At ip: " + foundIp + "port: " + foundPort + " within " + hops
				+ " number of hops");
		if(nodeIP!=searchIp && nodePort!=searchPort){
		TTransport transport = new TSocket(searchIp, searchPort);
        try {
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
	        QueryService.Client client = new QueryService.Client(protocol); 
	        client.fileFound(fileList, searchIp, searchPort,foundIp,foundPort, id, hops);
        } catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			transport.close();
		}
		}
		System.out.print("node>>>");
		
	}

}
