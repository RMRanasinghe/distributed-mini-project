package node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Logger;

import node.thrift.QueryServiceImpl;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class NodeListner implements Runnable {
	private Thread thread;
	private static final Logger log = Logger.getLogger(NodeListner.class
			.getName());
	public final static NodeListner INSTANCE = new NodeListner();

	PropertyLoader propertyLoader = new PropertyLoader();
	Properties properties = propertyLoader.getProperties();
	String nodeIP = properties.getProperty("node.internet.address");
	int nodePort = Integer.parseInt(properties
			.getProperty("node.internet.port"));

	private NodeListner() {
	}

	public void run() {
		
		DatagramSocket serverSocket;
		QueryParser queryParser = new QueryParser();
		try {
			serverSocket = new DatagramSocket(nodePort);
			byte[] receiveData = new byte[1024];
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				serverSocket.receive(receivePacket);
				String query = new String(receivePacket.getData());
				log.info("Query recieved: "+query);
				queryParser.parse(query);
			}
		} catch (SocketException e) {
			log.info("message recieving failed");
		} catch (IOException e) {
			log.info("message recieving failed");
		}
		
		
	}

	public void start() {
		log.info("Starting other node listner");
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
}
