package node;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

public class Node {

	private static final Logger log = Logger.getLogger( Node.class.getName() );
	
	public static void main(String[] args) throws IOException {	
		BSCommunicator bsCommunicator = BSCommunicator.INSTANCE;
		String out = bsCommunicator.init();
		System.out.println(out);
	}
	

}
