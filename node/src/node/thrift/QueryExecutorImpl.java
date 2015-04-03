package node.thrift;

import java.util.Properties;
import java.util.Random;

import node.FileManager;
import node.PropertyLoader;
import node.RoutingTable;
import node.RoutingTableEntry;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;

public class QueryExecutorImpl implements QueryExecutor.Iface {
	private RoutingTable routingTable;
	private String nodeIP;
	private int nodePort;
	private PropertyLoader propertyLoader;
	private Properties properties;
	private FileManager fileManager;
	
	public QueryExecutorImpl(){
		routingTable = RoutingTable.INSTANCE;
		propertyLoader = new PropertyLoader();
		fileManager = new FileManager();
		properties = propertyLoader.getProperties();
		nodeIP = properties.getProperty("node.internet.address");
		nodePort = Integer.parseInt(properties
				.getProperty("node.internet.port"));
	}
	@Override
	public void regOKSuccess1(String ip, int port) throws TException {
		RoutingTableEntry entry = new RoutingTableEntry(ip, port);
		routingTable.add(entry);
		
		TTransport transport;
        transport = new TSocket(ip, port);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        QueryExecutor.Client client = new QueryExecutor.Client(protocol);
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
        QueryExecutor.Client client1 = new QueryExecutor.Client(protocol1);
        QueryExecutor.Client client2 = new QueryExecutor.Client(protocol2);
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
		// TODO Auto-generated method stub
		
	}

}
