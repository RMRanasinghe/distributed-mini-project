package node.thrift;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import node.NodeListner;
import node.PropertyLoader;
import node.QueryParser;

public class ThriftNodeListner implements Runnable {
	private Thread thread;
	private static final Logger log = Logger.getLogger(ThriftNodeListner.class
			.getName());
	public final static ThriftNodeListner INSTANCE = new ThriftNodeListner();

	PropertyLoader propertyLoader = new PropertyLoader();
	Properties properties = propertyLoader.getProperties();
	String nodeIP = properties.getProperty("node.internet.address");
	int nodePort = Integer.parseInt(properties
			.getProperty("node.internet.port"));

	private ThriftNodeListner() {
	}

	public void run() {
		QueryServiceImpl handler;
		node.thrift.QueryService.Processor processor;

		try {

			handler = new QueryServiceImpl();
			processor = new node.thrift.QueryService.Processor(handler);

			TServerTransport serverTransport = new TServerSocket(nodePort);
			TServer server = new TSimpleServer(
					new TServer.Args(serverTransport).processor(processor));

			System.out.println("Starting the simple server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void start() {
		log.info("Starting other thrift node listner");
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
}
