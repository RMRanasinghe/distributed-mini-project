package node;

public class RoutingTableEntry {
	public String IP;
	public int port;
	public RoutingTableEntry(String ip, int port){
		this.IP = ip;
		this.port = port;
	}
}
