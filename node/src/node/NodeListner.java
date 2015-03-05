package node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Logger;

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
		try {
			serverSocket = new DatagramSocket(nodePort);
			byte[] receiveData = new byte[1024];
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				serverSocket.receive(receivePacket);
				String query = new String(receivePacket.getData());
				log.info("Query recieved: "+query);
				System.out.println("node>>>");
				//TODO:send sentence to query parser
			}
		} catch (SocketException e) {
			log.info("message recieving failed");
			System.out.println("node>>>");
		} catch (IOException e) {
			log.info("message recieving failed");
			System.out.println("node>>>");
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
