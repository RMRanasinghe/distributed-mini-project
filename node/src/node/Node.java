package node;

import java.io.IOException;
import java.util.logging.Logger;

public class Node {

	private static final Logger log = Logger.getLogger( Node.class.getName() );
	
	public static void main(String[] args) throws IOException {	
		
		log.info("Starting node");
		PropertyLoader propertyLoader = new PropertyLoader();
		propertyLoader.init();
		

		/*Start CLI Listner thread*/
		CLIListner cliListner = CLIListner.INSTANCE;
		cliListner.start();
		
		
		BSCommunicator bsCommunicator = BSCommunicator.INSTANCE;
		String out = bsCommunicator.init();
		
		QueryParser qp = new QueryParser();
		qp.parse(out);

		/*Start node listner thread*/
		NodeListner nodeListner = NodeListner.INSTANCE;
		nodeListner.start();
		
	}
	

}
