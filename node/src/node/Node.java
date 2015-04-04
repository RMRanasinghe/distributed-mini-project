package node;

import java.io.IOException;
import java.util.logging.Logger;

import node.thrift.ThriftNodeListner;

public class Node {

	private static final Logger log = Logger.getLogger( Node.class.getName() );
	
	public static void main(String[] args) throws IOException {	
		
		log.info("Starting node");
		PropertyLoader propertyLoader = new PropertyLoader();
		propertyLoader.init();

		/*Start node listner thread*/
		NodeListner nodeListner = NodeListner.INSTANCE;
		nodeListner.start();
		
		ThriftNodeListner tnodeListner =ThriftNodeListner.INSTANCE;
		tnodeListner.start();
		
		BSCommunicator bsCommunicator = BSCommunicator.INSTANCE;
		String out = bsCommunicator.init();
		
		QueryParser qp = new QueryParser();
		qp.parse(out);
		
		/*Start CLI Listner thread*/
		CLIListner cliListner = CLIListner.INSTANCE;
		cliListner.start();
		
	}
	

}
