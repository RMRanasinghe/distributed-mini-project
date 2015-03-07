package node;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

public class QueryParser {
	private Set<String> prevMessageIDSet;
	private int maxBuffer;
	private static final Logger log = Logger.getLogger(QueryParser.class
			.getName());

	public QueryParser(int maxBuffer) {
		prevMessageIDSet = new LinkedHashSet<String>();
		this.maxBuffer = maxBuffer; //get from property file
	}
	public QueryParser() {
		prevMessageIDSet = new LinkedHashSet<String>();
		this.maxBuffer = 100; //default buffer size
	}
	private void forgetOldestMessageId(Set<String> set){
		Iterator<String> iter = set.iterator();
	    iter.next();
	    iter.remove();
	}
	public void parse(String query) {
		String[] tokens = query.split("\\s+");
		String command = tokens[1];
		QueryExecutor qe = new QueryExecutor();
		try {
			if (command.equals("REGOK")) {
				Integer respondCode = Integer.parseInt(tokens[2]);
				switch (respondCode) {
				case 1:
					String ip = tokens[3];
					int port = Integer.parseInt(tokens[4]);
					qe.regOKSuccess(ip, port);
					log.info("Node successfully registered");
					break;
				case 2:
					String ip1 = tokens[3];
					int port1 = Integer.parseInt(tokens[4]);
					String ip2 = tokens[6];
					int port2 = Integer.parseInt(tokens[7]);
					qe.regOKSuccess(ip1, port1, ip2, port2);
					log.info("Node successfully registered");
					break;
				case 0:
					log.info("Node successfully registered");
					break;
				case 9999:
					log.severe("Node registration failed! there is some error in the command");
					System.exit(1);
					break;
				case 9998:
					log.severe("Node registration failed! already registered to you, unregister first");
					System.exit(1);
					break;
				case 9997:
					log.severe("Node registration failed! registered to another user, try a different IP and port");
					System.exit(1);
					break;
				case 9996:
					log.severe("Node registration failed! failed, can’t register. BS full.");
					System.exit(1);
					break;
				default:
					log.severe("undefined error code: " + command);
					break;

				}
			}

			if (command.equals("JOIN")) {
				String ip = tokens[2];
				int port = Integer.parseInt(tokens[3]);
				qe.join(ip, port);
			}

			if (command.equals("JOINOK")) {
				// TODO:if JOIN failed do something!!!
			}

			if (command.equals("LEAVE")) {
				String ip = tokens[2];
				int port = Integer.parseInt(tokens[3]);
				qe.leave(ip, port);
			}

			if (command.equals("LEAVEOK")) {
				// TODO:if LEAVE failed do something!!!
			}
			if (command.equals("SER")) {
				String ip = tokens[2];
				int port = Integer.parseInt(tokens[3]);
				String fileName = tokens[4];
				int hops = Integer.parseInt(tokens[5]);
				String messageId = tokens[6];
				//unique search test
				if (!prevMessageIDSet.add(messageId)) {
					qe.search(ip, port, hops, fileName);
					//maintain max buffer size
					if(prevMessageIDSet.size()>maxBuffer)
					forgetOldestMessageId(prevMessageIDSet);
				} else {
					// drop search packet without doing anything
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
}
