package node;

import java.util.logging.Logger;

public class QueryParser {
	private static final Logger log = Logger.getLogger(QueryParser.class
			.getName());

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
					String username = tokens[5];
					qe.regOKSuccess(ip, port, username);
					log.info("Node successfully registered");
					break;
				case 2:
					String ip1 = tokens[3];
					int port1 = Integer.parseInt(tokens[4]);
					String username1 = tokens[5];
					String ip2 = tokens[6];
					int port2 = Integer.parseInt(tokens[7]);
					String username2 = tokens[8];
					qe.regOKSuccess(ip1, port1, username1, ip2, port2,
							username2);
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
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
}
