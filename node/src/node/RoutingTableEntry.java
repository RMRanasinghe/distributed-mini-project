package node;

public class RoutingTableEntry {
	public String IP,username;
	public int port;
	public RoutingTableEntry(String ip, int port, String username){
		this.IP = ip;
		this.port = port;
		this.username = username;
	}
}
