package node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class NodeCommunicator {
	public final static NodeCommunicator INSTANCE = new NodeCommunicator();
	private static final Logger log = Logger.getLogger(NodeCommunicator.class
			.getName());

	private NodeCommunicator() {
	}

	public void send(String ip, int port, String message) {

		byte[] buffer = message.getBytes();
		DatagramPacket packet = null;
		DatagramSocket socket = null;

		try {
			try {
				packet = new DatagramPacket(buffer, buffer.length,
						new InetSocketAddress(ip, port));
				socket = new DatagramSocket();
				socket.send(packet);
				log.info("packet sent to: "+ip+" successfully");
			} catch (SocketException e) {
				log.warning("message '" + message + "' did not get delivered!");
			}
			catch (IOException e) {
				log.warning("message '" + message + "' did not get delivered!");
			}
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}
}
