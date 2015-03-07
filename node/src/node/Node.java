package node;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Node {

	private static final Logger log = Logger.getLogger( Node.class.getName() );
	
	public static void main(String[] args) throws IOException {	
		String saman = "saman_kumara";
		String kamal = "saman_kumara";
		Pattern p = Pattern.compile("(.*_)?"+saman+"(_(.*))?");
		Matcher m = p.matcher(kamal);
		System.out.println(m.matches());
		log.info("Starting node");
		PropertyLoader propertyLoader = new PropertyLoader();
		propertyLoader.init();
		
		BSCommunicator bsCommunicator = BSCommunicator.INSTANCE;
		String out = bsCommunicator.init();
		
		QueryParser qp = new QueryParser();
		qp.parse(out);

		/*Start node listner thread*/
		NodeListner nodeListner = NodeListner.INSTANCE;
		nodeListner.start();
		
		/*Start CLI Listner thread*/
		CLIListner cliListner = CLIListner.INSTANCE;
		cliListner.start();
		
	}
	

}
