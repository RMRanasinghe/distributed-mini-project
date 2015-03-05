package node;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Logger;

public class BSCommunicator {

	private static final Logger log = Logger.getLogger(BSCommunicator.class
			.getName());
	public final static BSCommunicator INSTANCE = new BSCommunicator();
	private Properties properties = null;
	private String BSAddress, nodeIP, nodeUsername;
	private int BSPort, nodePort;

	private BSCommunicator() {
		properties = new Properties();

		try {
			properties.load(new FileInputStream("resources" + File.separator
					+ "constants.properties"));
		} catch (FileNotFoundException e) {
			log.severe("Boostrap server configuration file not found");
		} catch (IOException e) {
			log.severe("Configuration file did not get loaded");
		}

		BSAddress = properties.getProperty("bs.internet.address");
		BSPort = Integer.parseInt(properties.getProperty("bs.internet.port"));
		nodeIP = properties.getProperty("node.internet.address");
		nodePort = Integer.parseInt(properties
				.getProperty("node.internet.port"));
		nodeUsername = properties.getProperty("node.username");
	}

	public String init() {
		log.info("Initializing communication with boostrap server");
		QueryGenerator queryGenerator = new QueryGenerator();
		String query = queryGenerator.getBSRegister(nodeIP, nodePort,
				nodeUsername);
		log.info("sending: " + query);
		String respond = sendandRecieve(query);
		log.info("recieved: " + respond);
		return respond;
	}

	public String leave() {
		QueryGenerator queryGenerator = new QueryGenerator();
		String query = queryGenerator.getBSUnRegister(nodeIP, nodePort,
				nodeUsername);
		log.info("sending: " + query);
		String respond = sendandRecieve(query);
		log.info("recieved: " + respond);
		return respond;
	}

	private String sendandRecieve(String query) {
		Socket clientSocket = null;
		String sentence = query + "\n";
		String modifiedSentence;
		boolean attemptFailed = false;

		try {
			clientSocket = new Socket(BSAddress, BSPort);
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			outToServer.writeBytes(sentence);

		} catch (UnknownHostException e) {
			log.severe("Connection to boostrap server failed");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(sentence);
			System.out.println(sentence.length());
			log.severe("Failed to connect boostrap server");
			try {
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (IOException e1) {
				log.warning("Closing the connection to boostrap server failed");
			}
			System.exit(0);
		}

		BufferedReader inFromServer;
		if (!attemptFailed) {
			try {
				inFromServer = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				while (!inFromServer.ready()) {
				}// Busy waiting

				modifiedSentence = inFromServer.readLine();
				return modifiedSentence;
			} catch (IOException e) {
				log.severe("Query error");
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e1) {
					log.warning("Closing the connection to boostrap server failed");
				}
			}
		}
		return null;
	}

}
