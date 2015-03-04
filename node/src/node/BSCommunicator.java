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
	private String BSAddress,nodeIP,nodeUsername;
	private int BSPort,nodePort;

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
		nodePort = Integer.parseInt(properties.getProperty("node.internet.port"));
		nodeUsername = properties.getProperty("node.username");
		
		log.info("Boostrap server address and port loaded");
	}

	public String init() {
		Socket clientSocket = null;
		QueryGenerator queryGenerator = new QueryGenerator();
		String sentence = queryGenerator.getBSRegister(nodeIP, nodePort, nodeUsername);
		String modifiedSentence;
		boolean attemptFailed = false;

		try {
			clientSocket = new Socket(BSAddress, BSPort);
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			outToServer.writeBytes(sentence + '\n');

		} catch (UnknownHostException e) {
			log.severe("Connection to boostrap server failed");
			System.exit(0);
		} catch (IOException e) {
			log.severe("Failed to connect boostrap server");
			try {
				clientSocket.close();
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
				log.severe("JOIN query error");
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
